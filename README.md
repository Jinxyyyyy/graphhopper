# Rapport tâche 3 - IFT3913A25 - Poldo Silva et Costarella-Serra


## **Documentation GA**

## **1.Modifications apportées à l'action**
L’objectif du projet est de mettre en place, au sein de GitHub Actions, un mécanisme automatique capable de faire échouer un build dès qu’une régression du score de mutation est détectée. Pour y parvenir, plusieurs éléments sont nécessaires : un score de mutation de référence (issu du build précédent), le score de mutation obtenu lors du build courant, et une méthode fiable permettant de comparer ces deux valeurs.

Nous avons choisi de récupérer, pour chaque module testable du projet, le score de mutation global. Une approche plus fine, par exemple récupérer un score de mutation pour chaque classe, aurait été possible, mais elle alourdirait considérablement la suite du pipeline et complexifierait l’analyse. À l’inverse, une approche moins granulaire consistant à calculer un score global unique pour l’ensemble de GraphHopper fournirait trop peu d’informations en cas d’échec du build, rendant le diagnostic difficile pour l’utilisateur.
Ainsi, la granularité par module représente un bon compromis : elle reste suffisamment simple à implémenter tout en offrant une information pertinente et exploitable lorsque le build échoue.

Le flux d'exécution du workflows est le suivant: 
### 1 Récupération des scores de mutation du build précédent
[lien vers l'action]
Avant d'exécuter les tests de mutations sur le build courant, le workflow tente de récupérer les scores de mutations des builds précédents. 
Le script `download-pit-scores.sh` [lien vers le script] effectue les actions suivantes:
1- Le script interroge GitHub pour récupérer les runs précédents

\```bash
gh run list \
  --workflow="$WORKFLOW" \
  --branch="$BRANCH" \
  --status=success \
  --limit="$LIMIT"
\```

2- Pour chaque run trouvé, on télécharge l'artificat **pit-scores-baselines**. Lorsqu'un artifact est trouvé, il est téléchargé dans le répertoire courant. Le script s'arrêt dès qu'il trouve un run contenant les scores. 
3- Deux cas sont possibles 
    1- Des scores ont été trouvés: On a une baseline pour comparaison.
    2- Aucun score trouvé: C'est le premier run, on set la baseline pour le prochain build. 

### 2 Exécution des tests de mutation sur le build courant
lien vers l'action
Après la compilation et l'exécution des tests unitaires, le workflow lance les tests de mutation avec **PITEST**. Le script `run-pit-test.sh` contient la logique d'execution. 
Il y a 2 points importants dans la logique d'exécution. 
1- Les modules CORE et READER-GTFS sont exclus. Les tests de mutations n'achèvent jamais, possiblement à cause de boucles infinis. 
2- Les paramètres `-DreportsDirectory=target/pit-reports -DoutputFormats=XML,HTML` permettent de générer un rapport dans les répertoires `target/pit-reports/mutations.xml`et `target/pit-reports/index.html`. `mutations.xml`sera nécessaire pour évaluer la régression des scores de mutations. 

### 3 Verification de la régression de mutation
lien vers l'action
Le script `check-mutation-regression.sh` effectue les actions suivantes: 
1- Récupère pour chaque module testé, un fichier `mutations.xml`
2- Calcule le score de mutation par module **(coverage = (mutations tués / mutants totaux) x 100)**
3- Pour chaque module, le score courant est comparé au score précédent sauvegardé dans `pit-score-<module>.txt`.
4- Un statut est attribué selon 3 cas, **Improved**, **Unchanged** ou **Regression**
5- En cas de **Regression**, `failed=true`.
6- Les scores sont ajoutés dans un rapport `mutation-report.md`
7- Les scores de références sont mises-à-jour et enregistrés dans `pit-score-<module>.txt`
8- Si `failed=true`, l'action échoue automatiquement si au moins un module a vu son score baisser.  

### 4 Enregistrement et archivage des scores et rapport
Que le build soit un succès ou un échec, les données sont sauvegardés sous forme d'artifacts. 
1. lien vers Upload PIT scores baseline: On sauvegarde **toujours** tous les fichiers `pit-score-*.txt` et on le conserve 90 jours. 
2. lien vers Upload PIT reports: On sauvegarde **toujours** tous les fichiers dans `*/target/pit-reports/` et conserve 30 jours. Cela inclut `mutations.xml` et `ìndex.html`
3. lien vers archire test reports on failure: En cas **d'échec**, On sauvegarde tous les rapports Surefire et conserve 7 jours, pour faciliter le débogage. 


### **2.Validation des modifications** 


## **Documentation mocks**

### **1.Choix des classes**
Les deux classes choisies sont : **DistanceConfig** et **ConditionalDistanceVoiceInstructionConfig**. On a choisi ces classes car elles ne sont pas très complexes et elles dépendent de peu d’éléments externes. Elles dépendent notamment de **TranslationMap** et de **Translation**, qui eux-mêmes n’ont pas de dépendances supplémentaires, ce qui facilite leur simulation avec des mocks.

Initialement, nous avions essayé de créer des tests avec des mocks pour la classe **DijkstraBiDirectionCH**, mais cela s’est avéré extrêmement difficile : cette classe possède de nombreuses dépendances, elles-mêmes dépendantes d’autres classes, et l’utilisation de mocks dans ce contexte devient très complexe et peu fiable. Cette expérience nous a montré les limites des mocks pour des classes fortement imbriquées, et nous a amenés à nous concentrer sur des classes moins complexes, comme **DistanceConfig** et **ConditionalDistanceVoiceInstructionConfig**.

Ces classes sont intéressantes pour les tests avec mocks car elles contiennent une logique interne importante (gestion des distances et génération d’instructions vocales), tout en étant suffisamment isolables grâce aux mocks. Cela permet de tester précisément leur comportement sans dépendre de la configuration réelle ou des fichiers de traduction, garantissant des tests unitaires fiables et reproductibles.

### **2.Définition des mocks**
Comme mentionne ci-haut on a decider de mock les classes TranslationMap et Translation dont **DistanceConfig** et **ConditionalDistanceVoiceInstructionConfig** dependant. Pour ce faire, on a defini les mock de facons suivante.

**Creation mock pour ConditionalDistanceVoiceInstructionConfig**:
- mockTranslationMap = mock(TranslationMap.class);
- mockTranslation = mock(com.graphhopper.util.Translation.class);

**Definition mock pour ConditionalDistanceVoiceInstructionConfig**:
- when(mockTranslationMap.getWithFallBack(any(Locale.class))).thenReturn(mockTranslation);
- when(mockTranslation.tr(anyString(), any())).thenReturn("dummy");

**Creation mock pour DistanceConfig**:
-mockTranslationMap = mock(TranslationMap.class);

**Definition mock pour DistanceConfig**:
- when(mockTranslationMap.getWithFallBack(any(Locale.class))).thenReturn(mock(com.graphhopper.util.Translation.class));

### **3.Changements dans les tests**

Pour adapter les tests initiaux en utilisant des mocks, plusieurs modifications ont été apportées aux tests creer par les developpeurs de GraphHopper et on va les expliquer ci-dessous :

**1.Remplacement des dépendances réelles par des mocks**

Dans les tests originaux, TranslationMap et Translation étaient utilisées telles quelles, ce qui rendait les tests dépendants des fichiers de traduction et des configurations réelles.
Dans les tests mockés, ces dépendances ont été remplacées par des objets simulés, permettant de contrôler totalement leur comportement. Par exemple, toute traduction renvoie "dummy".

**2.Adaptation des assertions**

Les tests officiels vérifiaient le texte exact des instructions vocales (par exemple "In 400 meters turn then") mais cela ne nous importe pas car on veut tester la logique avec les mocks en eliminant la dependance au fichier contenant les strings avec les directions. Donc les assertions se concentrent sur la logique interne de la classe : la sélection correcte de la distance (distanceAlongGeometry / distanceVoiceValue) et la présence de la description de direction(Left/Right) dans l’objet retourné.

**3.Isolation de la classe testée**

Ces changements permettent de transformer des tests qui ressemblaient à des tests d’intégration en tests unitaires isolés, reproductibles et stables, sans dépendre de la configuration externe de d'autres documents, fichiers ou initialisation de structure de donnees. Cela garantit que tout échec dans le test provient de la logique interne de la classe et non d’un problème avec les dépendances.

**4.Maintien de la pertinence fonctionnelle**

Même avec les mocks, les tests continuent de vérifier le comportement réel attendu de la classe : que la bonne instruction vocale soit choisie pour une distance donnée et que la valeur renvoyée soit correcte ou null si la distance est trop courte. Ainsi, les tests restent pertinents et fiables, tout en étant plus rapides et plus simples à exécuter.

En résumé, les changements consistent principalement à isoler les dépendances et à adapter les assertions pour tester uniquement la logique de la classe. Cela permet d’obtenir des tests unitaires robustes, faciles à maintenir et reproduisibles dans n’importe quel environnement, tout en restant cohérents avec les scénarios originaux fournis par les tests officiels de GraphHopper.


