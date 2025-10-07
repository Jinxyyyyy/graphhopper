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
