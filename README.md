# Rapport tâche 2 - IFT3913A25 - Poldo Silva et Costarella-Serra 

## Nouveaux tests 

1. [testSetAndGetDistanceInfluence()](./web-api/src/test/java/com/graphhopper/util/CustomModelTest.java#L97-L122)

    **Intention :** 
    Vérifier que la valeur de distanceInfluence est correctement enregistrée et récupérée

    **Motivation des données de test :**
    - ***null :*** Confirme qu'aucune valeur n'est définie initialement.
    - ***2.5, 4.0, 6.5:*** Valeurs arbitraires, mais distinctes, utilisés pour tester l'enregistrement, la modification et la lecture cohérente des valeurs.
   
   **Oracle :** 
   - `getDistanceInfluence()` retourne null par défaut.
   - Après appel à `setDistanceInfluence()`, `getDistanceInfluence()` retourne la valeur donnée.
   
2. [testReadValidArgs()](./web-api/src/test/java/com/graphhopper/util/CustomModelTest.java#L100-L105)

   **Intention du test :**
    - Vérifier que `read()` interprète correctement un tableau de chaînes "clé=valeur" et stocke les paires dans la map avec le type adéquat.

    **Motivation des données de test choisies :**
    - Deux clés ("foo" et "bar") sont utilisées avec des entiers en valeur pour représenter un cas d’utilisation classique.
    
    **Explication de l'oracle :**
    - Après parsing, la clé "foo" doit renvoyer 1 et la clé "bar" doit renvoyer 2 via `getInt()`.
    - L’oracle consiste à comparer ces résultats avec les valeurs attendues.

3. [testReadDuplicateKeyThrows](./web-api/src/test/java/com/graphhopper/util/PMapTest.java#L20-L123)
    **Intention du test :**
    - Vérifier que `read()` rejette correctement un tableau d’arguments contenant une clé dupliquée.
   
    **Motivation des données de test :**
    - On fournit deux entrées "foo=1" et "foo=2". Cela simule un scénario où un utilisateur fournit une même clé deux fois avec des valeurs différentes, ce qui doit être interdit.
   
    **Oracle :**
    - Le code de `read()` lève une `IllegalArgumentException` lorsqu’une clé est ajoutée en double.
    - L’oracle est la vérification que cette exception est bien levée.
      
4. [testGetBool](./web-api/src/test/java/com/graphhopper/util/PMapTest.java#L142-L149)

    **Intention du test :**
    - Vérifier que `getBool()` retourne la valeur stockée lorsqu’elle existe, et qu’il renvoie la valeur par défaut  lorsque la clé est absente.
   
      **Motivation des données de test :**
    - La clé "flag" est insérée avec la valeur ***true*** pour tester la récupération d’un booléen existant.
    - On utilise ensuite une clé ("missing") pour vérifier que la valeur par défaut est utilisée, une fois avec ***false***, une fois avec ***true***.
   
    **Oracle :**
    - `getBool("flag", false)` -> retourne ***true***, car la valeur existe.
    - `getBool("missing", false)` -> retourne ***false***, car la clé n’existe pas et la valeur par défaut est ***false***.
    - `getBool("missing", true)` ->  retourne ***true***, car la clé n’existe pas et la valeur par défaut est ***true***.

5. [testEmptyString](web-api/src/test/java/com/graphhopper/util/HelperTest.java#L147-151)
   
    **Intention du test:**
    -  On vérifie que la méthode retourne un liste video si le input est un string vide.
     
   **Motivation du test:**
    - Gestion de l'exception de string vide pour prevenir un "crash" du programme
     
   **Oracle du test:**
     Input: string vide -> ""
     Output:
     assertTrue(result.isEmpty()) retourne ***[]*** la liste vide

6. [testNormalList](web-api/src/test/java/com/graphhopper/util/HelperTest.java#L170-178)


    **Intention du test:**
    - On veut voir si le code est capable de compter le nombre d'éléments dans la liste d'élément et soit
    - capable de retourner les éléments dans une position spécifique.
      
   **Motivation du test:**
    - On veut savoir si la fonction est capable d'extraire comme il se doit,
    - les données dans la liste pour pouvoir manipuler ces informations par
    - la suite dans une autre fonction quelconque
     
   **Oracle du test:**
       Input: "[benoit, meryem, yogya]"
       Output: 
     - assertEquals(3, result.size())  retourne ***3*** la taille de la liste
     - assertEquals("benoit", result.get(0)) retourne ***"benoit*** le premier élément
     - assertEquals("meryem", result.get(1)) retourne ***"meryem*** le deuxieme élément
     - assertEquals("yogya", result.get(2)) retourne ***yogya*** le troisieme élément
       
7. [testListWithEmptyElements](web-api/src/test/java/com/graphhopper/util/HelperTest.java#L197-205)
   **Intention du test:**
    - On veut voir si la méthode parseList est capable de gérer les trous vide dans la liste
     
   **Motivation du test:**
    - Le but est de s'assuré que les trou vide sont bien gérer, sans quoi le programme pourrait
    - etre surpris par cela et "crash" car il ne sait pas gérer cela.
     
   **Oracle du test:**
     Input -> [benoit, , yogya, , ]
     Output:
     assertEquals(2, result.size()) retourne  ***2*** qui est la taille du tableau
     assertEquals("benoit", result.get(0)) retourne  ***"benoit"*** qui est le premier élément
     assertEquals("yogya", result.get(1)) retourne ***"yogya"*** qui est le deuxieme élément
     
     

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
On observe un forte augmentation de la couverture des mutants passant de 34% à 58%, avec 9 mutants supplémentaires détectés. Cette amélioration s'explique une meilleure couverture de deux méthodes non testées : `getBool()` et `read()`. 
 
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

### com.graphhopper.
**Avant ajout de 3 tests**
- Line coverage 27% (45/167)
- Mutation coverage 25% (43/173)
- Test Strength 88% (43/49)

**Après ajout de 3 tests**
- Line coverage 33% (55/167)
- Mutation coverage 27% (47/173)
- Test Strength 88% (47/54)

#### Analyse

On observe que le score de mutation a augmenté de 2 %, avec 4 mutants détectés de plus qu’auparavant. 
De plus, la couverture de lignes est passée de 27 % à 33 %. Ces trois tests couvrent donc du code qui n’était pas 
testé auparavant par les développeurs de GraphHopper. Cependant, la force des tests est restée stable à 88 %, comme avant.

Je pense que l’une des principales raisons de ces résultats est que nous avons testé la même fonction avec trois cas de tests différents. 
Il est également important de noter que ces trois cas sont du même type, à savoir des tests basés sur des assertions (assert).
Je crois que si nous avions utilisé d’autres types de tests (par exemple des tests de performance, d’exception ou de paramétrisation),
nous aurions pu obtenir de meilleurs résultats globaux.

Pour ce qui est des mutants, on peut expliquer les grandes catégories de mutants qui ont été tués et par quel test.
D’après le rapport, des mutants de type **negated conditional** et **null return** ont été tués. Ce genre de mutants est détecté par le **testEmptyString**, 
qui vérifie si le résultat est vide ou null dans certains cas : si ce n’est pas le cas, le mutant est tué.

Ensuite, le **testNormalList** tue les mutants de type **changed conditional boundary**, **removed trim** et **negated condition**. Ces mutants modifient les 
conditions en les inversant ou empêchent la suppression des espaces superflus (trim) dans les chaînes de caractères. 
Si des caractères en trop apparaissent, ce que provoquent ces mutants, le test échoue et le mutant est donc tué.

Enfin, le **testListWithEmptyElements** tue les mutants du type **negated conditional** et **removed if**. Ce test échoue si des éléments vides sont ajoutés
: par exemple, si la condition est inversée par le mutant, la liste contiendra plus de deux éléments, ce qui ne respecte pas l’assertion, et le mutant est tué.


## Justification Java Faker 

Le test testRandomBearingsWithFaker a pour objectif de vérifier le bon fonctionnement d’une méthode qui traite des angles représentés sous forme de chaînes de caractères. L’idée principale est de s’assurer que la méthode peut correctement extraire et manipuler des valeurs provenant d’entrées variées, même lorsque celles-ci sont générées aléatoirement.

Pour cela, nous utilisons Java Faker, qui permet de générer des nombres aléatoires. Chaque valeur aléatoire est ensuite combinée pour former une chaîne représentant deux angles séparés par une virgule. Cette approche simule des entrées réalistes mais qui varie, ce qui permet de tester la robustesse de la méthode sous pression dans différentes situations.

L’importance de ce test est dans sa capacité à détecter des erreurs de parsing ou de traitement des données. Le test vérifie que, pour chaque chaîne générée, le premier angle est correctement identifié et stocké dans la liste des résultats. Si la méthode échoue à extraire la bonne valeur, le test échoue.

Cette stratégie permet donc de tester de manière dynamique et répétable la méthode sur un large éventail de données possibles, ce qui est beaucoup plus efficace que de coder manuellement chaque scénario. De plus, elle garantit que la méthode reste fiable même lorsque les valeurs d’entrée changent, ce qui est crucial pour tout système qui doit traiter des données variées provenant de différentes sources.

En résumé, le test testRandomBearingsWithFaker sert à valider que la méthode gère correctement des entrées multiples, aléatoires et variées, tout en assurant que le comportement attendu — l’extraction correcte du premier angle — est toujours respecté.
