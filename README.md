# Rapport tâche 2 - IFT3913A25 - Poldo Silva et Costarella-Serra 

## Nouveaux tests 

1. [testSetAndGetDistanceInfluence()](https://github.com/Jinxyyyyy/graphhopper/blob/master/web-api/src/test/java/com/graphhopper/util/CustomModelTest.java#L94-L107)

    **Intention :** 
    Vérifier que la valeur de distanceInfluence est correctement enregistrée et récupérée

    **Motivation des données de test :**
    - ***null :*** Confirme qu'aucune valeur n'est définie initialement.
    - ***2.5, 4.0, 6.5:*** Valeurs arbitraires, mais distinctes, utilisés pour tester l'enregistrement, la modification et la lecture cohérente des valeurs.
   
   **Oracle :** 
   - `getDistanceInfluence()` retourne null par défaut.
   - Après appel à `setDistanceInfluence()`, `getDistanceInfluence()` retourne la valeur donnée.
   
2. [testReadValidArgs()](https://github.com/Jinxyyyyy/graphhopper/blob/master/web-api/src/test/java/com/graphhopper/util/PMapTest.java#L100-L105)

   **Intention du test :**
    - Vérifier que `read()` interprète correctement un tableau de chaînes "clé=valeur" et stocke les paires dans la map avec le type adéquat.

    **Motivation des données de test choisies :**
    - Deux clés ("foo" et "bar") sont utilisées avec des entiers en valeur pour représenter un cas d’utilisation classique.
    
    **Explication de l'oracle :**
    - Après parsing, la clé "foo" doit renvoyer 1 et la clé "bar" doit renvoyer 2 via `getInt()`. L’oracle consiste à comparer ces résultats avec les valeurs attendues.

3. [testReadDuplicateKeyThrows](https://github.com/Jinxyyyyy/graphhopper/blob/master/web-api/src/test/java/com/graphhopper/util/PMapTest.java#L120-L123)

    **Intention du test :**
    - Vérifier que `read()` rejette correctement un tableau d’arguments contenant une clé dupliquée.
   
    **Motivation des données de test :**
    - On fournit deux entrées "foo=1" et "foo=2". Cela simule un scénario où un utilisateur fournit une même clé deux fois avec des valeurs différentes, ce qui doit être interdit.
   
    **Oracle :**
    - Le code de `read()` lève une `IllegalArgumentException` lorsqu’une clé est ajoutée en double. L’oracle est la vérification que cette exception est bien levée.
      
4. [testGetBool](https://github.com/Jinxyyyyy/graphhopper/blob/master/web-api/src/test/java/com/graphhopper/util/PMapTest.java#L142-L149)

    **Intention du test :**
    - Vérifier que `getBool()` retourne la valeur stockée lorsqu’elle existe, et qu’il renvoie la valeur par défaut  lorsque la clé est absente.
   
    **Motivation des données de test :**
    - La clé "flag" est insérée avec la valeur ***true*** pour tester la récupération d’un booléen existant.
    - On utilise ensuite une clé ("missing") pour vérifier que la valeur par défaut est utilisée, une fois avec ***false***, une fois avec ***true***.
   
    **Oracle :**
    - L’oracle valide que la valeur retournée par getBool() correspond à la valeur associée à la clé, ou à la valeur par défaut si la clé n’existe pas.
5. [testEmptyString()](https://github.com/Jinxyyyyy/graphhopper/blob/master/web-api/src/test/java/com/graphhopper/util/HelperTest.java#L147-L151)
   
    **Intention du test :**
    -  Vérifier que la méthode `parseList()` retourne une liste vide lorsqu’elle reçoit en entrée une chaîne de caractères vide.
     
   **Motivation des données :**
   - S’assurer que la méthode `parseList()` est robuste face à un cas limite (entrée vide) et qu’elle ne provoque pas d’erreurs d’exécution. 
   
   - **Oracle du test :** 
   - Pour une chaîne vide en entrée, la méthode doit retourner une liste vide.

6. [testNormalList()](https://github.com/Jinxyyyyy/graphhopper/blob/master/web-api/src/test/java/com/graphhopper/util/HelperTest.java#L170-L178)
    **Intention du test :**
    - Vérifier que la méthode `parseList()` est capable de parser correctement une chaîne représentant une liste et de restituer tous les éléments dans l’ordre attendu.
      
   **Motivation des données :**
    - S’assurer que la méthode `parseList()` identifie correctement chaque élément d’une liste non vide, en conservant à la fois la taille exacte et l’ordre des éléments.
     
   **Oracle :**
   - L’oracle confirme que, pour l’entrée `[benoit, meryem, yogya]`, le résultat attendu est une liste ordonnée de taille 3, où `élément[0] = "benoit"`, `élément[1] = "meryem"` et `élément[2] = "yogya"`.       

7. [testListWithEmptyElements()](https://github.com/Jinxyyyyy/graphhopper/blob/master/web-api/src/test/java/com/graphhopper/util/HelperTest.java#L197-L205)
   **Intention :**
    - Vérifier que la méthode `parseList()` est capable d’ignorer correctement les éléments vides lorsqu’elle parse une chaîne représentant une liste.
     
   **Motivation des données :**
   - S’assurer que la méthode`parseList()` gère de manière robuste la présence d’éléments vides, afin d’éviter des erreurs d’exécution ou un comportement inattendu.
   
   **Oracle du test :**
   - L’oracle confirme que, pour l’entrée `[benoit, , yogya, , ]`, le résultat attendu est une liste ordonnée de taille 2, où élément[0] = "benoit" et élément[1] = "yogya".

## Score de mutation 

### com.graphhopper.util.PMap 
**Avant ajout de 3 tests** 
- Line coverage 36% (21/59) 
- Mutation coverage 34% (13/38)
- Test Strength 87% (12/15)

**Après ajout de 3 tests**
- Line coverage 68% (40/59)
- Mutation coverage 58% (22/38)
- Test Strength 88% (22/25)

#### Analyse
On observe une forte augmentation de la couverture des mutants passant de 34% à 58%, avec 9 mutants supplémentaires détectés. Cette amélioration s'explique une meilleure couverture de deux méthodes non testées : `getBool()` et `read()`. 
 
**Nouveaux mutants détectés :**

    `L71#2 negated conditional -> NO_CONVERAGE` ---> `KILLED`
    `L76#1 negated conditional -> NO_CONVERAGE` ---> `KILLED`
    `L80#1 negated conditional -> NO_CONVERAGE` ---> `KILLED`
    `L84#1 Replaced integer addition with substraction -> NO_CONVERAGE` ---> `KILLED`
    `L86#1 negated conditional -> NO_CONVERAGE` ---> `KILLED`
    `L90#1 replaced return value for null for read() ---> `KILLED`
    `L108#1 replaced boolean return with true for getBool() -> NO_CONVERAGE` ---> `KILLED`
    `L108#2 negated conditional -> NO_CONVERAGE` ---> `KILLED`
    `L108#1 replaced boolean return with false for getBool() -> NO_CONVERAGE` ---> `KILLED`

- `getBool()` : 
    Avant l'ajout des tests, cette méthode n'était pas couverte, ce qui empêchait la détection de 3 mutants. 
    Les nouveaux tests ajoutés augmentent la couverture de code sur cette méthode, permettant aux mutants d'être détectés et tués. 
- `read()`:
    Avant l'ajout des tests, cette méthode n'était pas couverte, ce qui empêchait la détection de 6 mutants.
    Les nouveaux tests ajoutés augmentent la couverture de code sur cette méthode, permettant aux mutants d'être détectés et tués.

En résumé, l'ajout de 3 tests augmente significativement la couverture du code et le score du de mutation.

### com.graphhopper.util.CustomModel
**Avant ajout de 1 test**
- Line coverage 53% (39/74)
- Mutation coverage 24% (8/33)
- Test Strength 50% (8/16)

**Après ajout de 1 test**
- Line coverage 55% (41/74)
- Mutation coverage 30% (10/33)
- Test Strength 88% (10/17)

#### Analyse
On observe une augmentation du score de mutation, passant de 8/33 à 10/33, soit 2 mutants supplémentaires détectés. 
Cela est dû principalement de la couverture deux 2 méthodes (`setDistanceInfluence()` et `getDistanceInfluence()`) non couvertes auparavant. 

**Nouveaux mutants détectés :**

    `L141#1 replaced value with null for setDistanceInfluence() -> NO_CONVERAGE` ---> `KILLED`
    `L145#1 replaced Double return value with 0 for getDistanceInfluence() -> SURVIVED Covering tests` ---> `KILLED`

- `setDistanceInfluence()` :
    Avant l'ajout des tests, cette méthode n'était pas couverte, ce qui empêchait la détection ce mutant.
    Le nouveau test execute la méthode et valide son comportement, ce qui a permis de tuer le mutant. 
- `getDistanceInfluence()` :
    Les tests n'évaluaient pas la valeur de retour, laissant le mutant survivre. 
    Le nouveau test ajoute une assertion sur la valeur retournée, validant le comportement et tuant le mutant. 

En résumé, l'ajout d'un test sur cette classe a permis d'améliorer la couverture de mutation. 

### com.graphhopper.util.Helper

**Avant ajout de 3 tests**
- Line coverage 27% (45/167)
- Mutation coverage 25% (43/173)
- Test Strength 88% (43/49)

**Après ajout de 3 tests**
- Line coverage 33% (55/167)
- Mutation coverage 27% (47/173)
- Test Strength 88% (47/54)

#### Analyse

On observe une augmentation du score de 2% avec 4 nouveaux mutants détectés et une hausse de 6% de la couverture des lignes. Cette amélioration s'explique principalement par la meilleure couverture de la méthode `parseList()`.
Cependant, la force de test reste stable à 88%, ce qui suggère que les cas nouveaux cas de test n'améliorent pas significativement la qualité du code.

Cela pourrait être dû au fait que les 3 nouveaux tests testent la meme fonction (`parseList()`) avec des assertions simples et du même type. Une amélioration possible est de tester plus de méthodes de la classe `Helper.java`, valider les exceptions et ajouter des tests paramétrés. 

**Nouveaux mutants détectés :**

    `L408#2 negated conditional → NO_COVERAGE` ---> `KILLED`
    `L410#1 Replaced integer subtraction with addition -> NO_COVERAGE` ---> `KILLED`
    `L414#1 negated conditional → NO_COVERAGE` ---> `KILLED`
    `L418#1 replaced return value with Collections.emptyList parseList() → NO_COVERAGE` ---> `KILLED`

On observe qu’avant l’introduction des trois nouveaux cas de test, quatre mutants liés à la méthode `parseList()` n’étaient pas tués. Cela s’explique simplement par le fait que les tests initiaux ne couvraient pas correctement cette méthode.

En bref, l'augmentation du score de mutation et la couverture des lignes n'implique pas toujours l'augmentation de la force de test. 

## Justification Java Faker 

[testRandomBearingsWithFaker()](https://github.com/Jinxyyyyy/graphhopper/blob/master/navigation/src/test/java/com/graphhopper/navigation/NavigateResourceTest.java#L52-L75)

Le test `testRandomBearingsWithFaker()` a pour objectif de vérifier le bon fonctionnement d’une méthode qui traite des angles représentés sous forme de chaînes de caractères. L’idée principale est de s’assurer que la méthode peut correctement extraire et manipuler des valeurs provenant d’entrées variées, même lorsque celles-ci sont générées aléatoirement.

Pour cela, nous utilisons **Java Faker**, une bibliothèque qui permet de générer des nombres aléatoires. Chaque valeur aléatoire est combinée pour former une chaîne représentant **deux angles séparés par une virgule**. Cette approche simule des entrées réalistes mais variables, afin de tester la robustesse de la méthode dans différentes situations.

L’importance de ce test est en sa capacité à **détecter des erreurs de parsing ou de traitement des données**. Le test vérifie que, pour chaque chaîne générée, le premier angle est correctement identifié et stocké dans la liste des résultats. Si la méthode échoue à extraire la bonne valeur, le test échoue.

Cette stratégie permet donc de tester de manière **dynamique et répétable** la méthode sur un large éventail de données possibles, ce qui est beaucoup plus efficace que de coder manuellement chaque scénario. Elle garantit que la méthode reste fiable même lorsque les valeurs d’entrée changent, ce qui est crucial pour tout système qui doit traiter des données variées provenant de différentes sources.

En résumé, le test `testRandomBearingsWithFaker()` valide que la méthode gère correctement des entrées multiples, aléatoires et variées, tout en assurant que le comportement attendu, l’extraction correcte du premier angle, est toujours respecté.



# Rapport tâche 3 - IFT3913A25 - Poldo Silva et Costarella-Serra


## **Documentation GA**

### **1.Modifications apportées à l'action**

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





