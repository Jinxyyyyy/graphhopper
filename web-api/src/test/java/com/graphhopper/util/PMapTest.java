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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PMapTest {

    @Test
    public void singleStringPropertyCanBeRetrieved() {
        PMap subject = new PMap("foo=bar");

        assertEquals("bar", subject.getString("foo", ""));
    }

    @Test
    public void propertyFromStringWithMultiplePropertiesCanBeRetrieved() {
        PMap subject = new PMap("foo=valueA|bar=valueB");

        assertEquals("valueA", subject.getString("foo", ""));
        assertEquals("valueB", subject.getString("bar", ""));
    }

    @Test
    public void keyCannotHaveAnyCasing() {
        PMap subject = new PMap("foo=valueA|bar=valueB");

        assertEquals("valueA", subject.getString("foo", ""));
        assertEquals("", subject.getString("Foo", ""));
    }

    @Test
    public void numericPropertyCanBeRetrievedAsLong() {
        PMap subject = new PMap("foo=1234|bar=5678");

        assertEquals(1234L, subject.getLong("foo", 0));
    }

    @Test
    public void numericPropertyCanBeRetrievedAsDouble() {
        PMap subject = new PMap("foo=123.45|bar=56.78");

        assertEquals(123.45, subject.getDouble("foo", 0), 1e-4);
    }

    @Test
    public void hasReturnsCorrectResult() {
        PMap subject = new PMap("foo=123.45|bar=56.78");

        assertTrue(subject.has("foo"));
        assertTrue(subject.has("bar"));
        assertFalse(subject.has("baz"));
    }

    @Test
    public void nameAndProperties() {
        PMap pMap = new PMap("name|x=3|y=2");
        assertEquals(2, pMap.toMap().size());
        assertEquals(3, pMap.getInt("x", -1));
        assertEquals(2, pMap.getInt("y", -1));
    }

    @Test
    public void empty() {
        assertTrue(new PMap("").toMap().isEmpty());
        assertTrue(new PMap("name").toMap().isEmpty());
    }
    /**
     * Nom du test : testReadValidArgs
     *
     * Intention du test :
     * Vérifier que read() interprète correctement un tableau de chaînes "clé=valeur"
     * et stocke les paires dans la map avec le type adéquat.
     *
     * Motivation des données de test choisies :
     * Deux clés ("foo" et "bar") sont utilisées avec des entiers en valeur pour représenter
     * un cas d’utilisation classique.
     *
     * Explication de l'oracle :
     * Après parsing, la clé "foo" doit renvoyer 1 et la clé "bar" doit renvoyer 2 via getInt().
     * L’oracle consiste à comparer ces résultats avec les valeurs attendues.
     */
    @Test
    public void testReadValidArgs() {
        PMap map = PMap.read(new String[]{"foo=1", "bar=2"});
        assertEquals(1, map.getInt("foo", 0));
        assertEquals(2, map.getInt("bar", 0));
    }
    /**
     * Nom du test : testReadDuplicateKeyThrows
     *
     * Intention du test :
     * Vérifier que read() rejette correctement un tableau d’arguments contenant une clé dupliquée.
     *
     * Motivation des données de test choisies :
     * On fournit deux entrées "foo=1" et "foo=2". Cela simule un scénario où un utilisateur fournit
     * une même clé deux fois avec des valeurs différentes, ce qui doit être interdit.
     *
     * Explication de l'oracle :
     * Le code de read() lève une IllegalArgumentException lorsqu’une clé est ajoutée en double.
     * L’oracle est donc la vérification que cette exception est bien levée dans ce cas.
     */
    @Test
    public void testReadDuplicateKeyThrows() {
        assertThrows(IllegalArgumentException.class, () -> PMap.read(new String[]{"foo=1", "foo=2"}));
    }

    /**
     * Nom du test : testGetBool
     *
     * Intention du test :
     * Vérifier que getBool() retourne la valeur stockée lorsqu’elle existe,
     * et qu’il renvoie la valeur par défaut lorsque la clé est absente.
     *
     * Motivation des données de test choisies :
     * La clé "flag" est insérée avec la valeur true pour tester la récupération d’un booléen.
     * On utilise ensuite une clé ("missing") pour vérifier que la valeur par défaut est utilisée,
     * une fois avec false, une fois avec true.
     *
     * Explication de l'oracle :
     * getBool("flag", false) -> retourne true, car la valeur existe.
     * getBool("missing", false) -> retourne false, car la clé n’existe pas et la valeur par défaut est false.
     * getBool("missing", true) ->  retourne true, car la clé n’existe pas et la valeur par défaut est true.
     */
    @Test
    public void testGetBool() {
        PMap map = new PMap();
        map.putObject("flag",true);

        assertTrue(map.getBool("flag", false));
        assertFalse(map.getBool("missing", false));
        assertTrue(map.getBool("missing", true));

    }
}
