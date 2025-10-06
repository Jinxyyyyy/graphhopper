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



5. [test5]()
# TODO
6. [test6]()
# TODO
7. [test7]()
# TODO

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

Ici, on observe un forte augmentation de la couverture des mutants pour un total 9 nouveaux mutants. 

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

- `setDistanceInfluence()` :
    Avant l'ajout des tests, cette méthode n'était pas couverte, ce qui empêchait la détection ce mutant.
    Le nouveau test execute la méthode et valide son comportement, ce qui a permis de tuer le mutant. 
- `getDistanceInfluence()` :
    Les tests n'évaluaient pas la valeur de retour, laissant le mutant survivre. 
    Le nouveau test ajoute une assertion sur la valeur retournée, validant le comportement et tuant le mutant. 

En résumé, l'ajout d'un test sur cette classe a permis d'améliorer la couverture de mutation. 

### com.graphhopper.
**Avant ajout de 3 tests**
- Line coverage xx% (xx/xx)
- Mutation coverage xx% (xx/xx)
- Test Strength 88% (xx/xx)

**Après ajout de 3 tests**
- Line coverage xx% (xx/xx)
- Mutation coverage xx% (xx/xx)
- Test Strength 88% (xx/xx)

#### Analyse

# TODO 

## Java Faker 
# TODO
