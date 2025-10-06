/*
 *  Licensed to GraphHopper GmbH under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for
 *  additional information regarding copyright ownership.
 *
 *  GraphHopper GmbH licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except in
 *  compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.graphhopper.util;

import com.graphhopper.json.Statement;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.util.Iterator;


import static com.graphhopper.json.Statement.*;
import static com.graphhopper.json.Statement.Op.MULTIPLY;
import static org.junit.jupiter.api.Assertions.*;

public class CustomModelTest {

    @Test
    public void testMergeComparisonKeys() {
        CustomModel truck = new CustomModel();
        truck.addToPriority(If("max_width < 3", MULTIPLY, "0"));
        CustomModel car = new CustomModel();
        car.addToPriority(If("max_width<2", MULTIPLY, "0"));
        CustomModel bike = new CustomModel();
        bike.addToPriority(If("max_weight<0.02", MULTIPLY, "0"));

        assertEquals(2, CustomModel.merge(bike, car).getPriority().size());
        assertEquals(1, bike.getPriority().size());
        assertEquals(1, car.getPriority().size());
    }

    @Test
    public void testMergeElse() {
        CustomModel truck = new CustomModel();
        truck.addToPriority(If("max_width < 3", MULTIPLY, "0"));

        CustomModel car = new CustomModel();
        car.addToPriority(If("max_width < 2", MULTIPLY, "0"));

        CustomModel merged = CustomModel.merge(truck, car);
        assertEquals(2, merged.getPriority().size());
        assertEquals(1, car.getPriority().size());
    }

    @Test
    public void testMergeEmptyModel() {
        CustomModel emptyCar = new CustomModel();
        CustomModel car = new CustomModel();
        car.addToPriority(If("road_class==primary", MULTIPLY, "0.5"));
        car.addToPriority(ElseIf("road_class==tertiary", MULTIPLY, "0.8"));

        Iterator<Statement> iter = CustomModel.merge(emptyCar, car).getPriority().iterator();
        assertEquals("0.5", iter.next().value());
        assertEquals("0.8", iter.next().value());

        iter = CustomModel.merge(car, emptyCar).getPriority().iterator();
        assertEquals("0.5", iter.next().value());
        assertEquals("0.8", iter.next().value());
    }
    /**
     * Test testSetAndGetDistanceInfluence
     *
     * Intention :
     * Vérifier que la valeur de distanceInfluence est correctement enregistrée et récupérée.
     *
     * Motivation des données de test :
     * null : confirme qu’aucune valeur n’est définie initialement.
     * 2.5, 4.0, 6.5 : valeurs arbitraires, mais distinctes, utilisées pour tester l’enregistrement,
     * la modification et la lecture cohérente des valeurs.
     *
     * Oracle :
     * getDistanceInfluence() retourne null par défaut.
     * Après appel à setDistanceInfluence(), getDistanceInfluence() retourne la valeur donnée.
     */
    @Test
    public void testSetAndGetDistanceInfluence() {
        CustomModel car = new CustomModel();
        assertNull(car.getDistanceInfluence());

        CustomModel car2 = car.setDistanceInfluence(6.5);
        assertSame(car, car2);
        assertEquals(6.5, car.getDistanceInfluence(), 0.0001);

        car.setDistanceInfluence(2.5);
        assertEquals(2.5, car.getDistanceInfluence(), 0.0001);

        car.setDistanceInfluence(4.0);
        assertEquals(4.0, car.getDistanceInfluence(), 0.0001);
    }
}
