# Rapport tâche 2 - IFT3913A25 - Poldo Silva et Costarella 

### Nouveaux tests 

1. [testSetAndGetDistanceInfluence()](./web-api/src/test/java/com/graphhopper/util/CustomModelTest.java#L97-L122)
    **Intention:** 
    Vérifier que la valeur de distanceInfluence est correctement enregistrée et récupérée
    **Motivation des données de test:**
    - ***null:*** Confirme qu'aucune valeur n'est définie initiialement.
    - ***2.5, 4.0, 6.5:*** Valeurs arbitraires mais distinctes, utilisés pour tester l'enregistrement, la modification et la lecture cohérente des valeurs.
   **Oracle:** 
   - getDistanceInfluence() retourne null par défaut.
   - Après appel à setDistanceInfluence(), getDistanceInfluence() retourne la valeur donnée.
   - Le setter retourne l’instance courante.

2. [testGetBool](./web-api/src/test/java/com/graphhopper/util/PMapTest.java#L142-L49)
    *
    * Intention du test :
    * Vérifier que getBool() retourne la valeur stockée lorsqu’elle existe,
    * et qu’il renvoie la valeur par défaut (_default) lorsque la clé est absente.
    *
    * Motivation des données de test choisies :
    * La clé "flag" est insérée avec la valeur true pour tester la récupération d’un booléen existant.
    * On utilise ensuite une clé absente ("missing") pour vérifier que la valeur par défaut est utilisée,
    * une fois avec false, une fois avec true.
    *
    * Explication de l'oracle :
    * getBool("flag", false) -> retourne true, car la valeur existe.
    * - `getBool("missing", false)` -> retourne false, car la clé n’existe pas et la valeur par défaut est false.
    * - `getBool("missing", true)` ->  retourne true, car la clé n’existe pas et la valeur par défaut est true.
        */
3. [testSetAndGetDistanceInfluence()](./web-api/src/test/java/com/graphhopper/util/CustomModelTest.java#L97-L122)

4. [testSetAndGetDistanceInfluence()](./web-api/src/test/java/com/graphhopper/util/CustomModelTest.java#L97-L122)

5. [testSetAndGetDistanceInfluence()](./web-api/src/test/java/com/graphhopper/util/CustomModelTest.java#L97-L122)

6. [testSetAndGetDistanceInfluence()]()

7. [test6]()
