package com.graphhopper.navigation;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class NavigateResourceTest {


    @Test
    public void voiceInstructionsTest() {

        List<Double> bearings = NavigateResource.getBearing("");
        assertEquals(0, bearings.size());
        assertEquals(Collections.EMPTY_LIST, bearings);

        bearings = NavigateResource.getBearing("100,1");
        assertEquals(1, bearings.size());
        assertEquals(100, bearings.get(0), .1);

        bearings = NavigateResource.getBearing(";100,1;;");
        assertEquals(4, bearings.size());
        assertEquals(100, bearings.get(1), .1);
    }
    /**
     * Nom du test : testRandomBearingsWithFaker
     *
     * Intention du test :
     * Vérifier que la méthode getBearing() extrait correctement le premier angle d'une série de paires d'angles
     * générées au hasard. Avec Java Faker on produit des donnees au hasard qui nous permettent de tester plusieurs
     * formats.
     *
     * Motivation du test :
     * Les tests précédents utilisent des valeurs fixes. Ce test introduit de la variabilité à l’aide de la bibliothèque
     * Java Faker pour s’assurer que la fonction fonctionne correctement avec tout type de valeurs réalistes.
     * Cela permet de vérifier la robustesse et la stabilité de l’algorithme, indépendamment des valeurs d’entrée.
     *
     * Oracle du test :
     * Pour chaque paire générée (angle1, angle2), la méthode doit extraire uniquement le premier angle (angle1).
     * On compare donc la liste retournée par getBearing() à la liste attendue "expected" contenant uniquement les angle1.
     * Les deux listes doivent être de la même taille et contenir les mêmes valeurs aux mêmes indices.
     */


    @Test
    public void testRandomBearingsWithFaker() {
        Faker faker = new Faker();
        int numberOfBearings = 5;
        List<String> bearingStrings = new ArrayList<>();
        List<Double> expected = new ArrayList<>();

        for (int i = 0; i < numberOfBearings; i++) {
            double angle1 = faker.number().randomDouble(2, 0, 180); // premier angle
            double angle2 = faker.number().randomDouble(2, 0, 180); // deuxième angle
            bearingStrings.add(angle1 + "," + angle2);
            expected.add(angle1); // on ne prend que le premier angle
        }

        String input = String.join(";", bearingStrings);

        List<Double> result = NavigateResource.getBearing(input);

        assertEquals(expected.size(), result.size(),
                "La taille de la liste doit correspondre au nombre de bearings");
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), result.get(i), 1e-6,
                    "Angle aléatoire " + i + " incorrect");
        }
    }
}
