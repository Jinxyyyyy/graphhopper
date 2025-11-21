package com.graphhopper.navigation;

import com.graphhopper.util.TranslationMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour {@link ConditionalDistanceVoiceInstructionConfig} utilisant des mocks
 * pour {@link TranslationMap} et {@link com.graphhopper.util.Translation}.
 * Ces tests vérifient que la méthode {@link ConditionalDistanceVoiceInstructionConfig#getConfigForDistance}
 * renvoie correctement un {@link VoiceInstructionConfig.VoiceInstructionValue} pour différentes distances
 * ou renvoie null lorsque la distance est inférieure au seuil minimal.
 */

class ConditionalDistanceVoiceInstructionConfigMockTest {
/** Mock de TranslationMap utilisé pour injecter dans ConditionalDistanceVoiceInstructionConfig. */
    private TranslationMap mockTranslationMap;
        /** Mock de Translation utilisé pour éviter les NullPointerException. */
    private com.graphhopper.util.Translation mockTranslation;
     /**
     * Initialise les mocks avant chaque test.
     * Configure TranslationMap et Translation pour toujours retourner une valeur “dummy”.
     */
    @BeforeEach
    void setUp() {
        // Section pour les mocks en guise de preparation
        mockTranslationMap = mock(TranslationMap.class);
        mockTranslation = mock(com.graphhopper.util.Translation.class);
        when(mockTranslationMap.getWithFallBack(any(Locale.class))).thenReturn(mockTranslation);
        when(mockTranslation.tr(anyString(), any())).thenReturn("dummy"); 
    }
    /**
     * Test que {@link ConditionalDistanceVoiceInstructionConfig#getConfigForDistance}
     * renvoie un {@link VoiceInstructionConfig.VoiceInstructionValue} correct pour une distance
     * située entre deux seuils.
     * Vérifie que :
     *   La valeur renvoyée n’est pas nulle
     *   Le spokenDistance correspond au seuil approprié
     *   La description contient le texte du virage
     */
    @Test
    void testGetConfigForDistance_returnsCorrectInstruction() {
        // Distances “géométriques” pour les seuils de l’instruction vocale
        int[] geometryDistances = {50, 100, 150};
        // Valeurs vocales correspondantes à chaque seuil
        int[] voiceValues = {50, 100, 150};
        // Création de la configuration d’instruction vocale conditionnelle
        ConditionalDistanceVoiceInstructionConfig config =
                new ConditionalDistanceVoiceInstructionConfig(
                        "in_lower_distance", mockTranslationMap, Locale.ENGLISH,
                        geometryDistances, voiceValues
                );
        // Récupère l’instruction vocale correspondant à une distance de 120
        VoiceInstructionConfig.VoiceInstructionValue value =
                config.getConfigForDistance(120, "turn right", "");
        
        assertNotNull(value, "VoiceInstructionValue should not be null");
        assertEquals(50, value.spokenDistance, "Spoken distance should match the fitting distance");
        assertTrue(value.turnDescription.contains("turn right"), "Description should include the turn");
    }
    /**
     * Test que {@link ConditionalDistanceVoiceInstructionConfig#getConfigForDistance}
     * renvoie null lorsqu’une distance inférieure au premier seuil est donnée.
     * Vérifie que la méthode ne génère pas d’instruction vocale pour des distances trop courtes.
     */
    @Test
    void testGetConfigForDistance_returnsNullForTooShortDistance() {
        int[] geometryDistances = {50, 100, 150};
        int[] voiceValues = {50, 100, 150};

        ConditionalDistanceVoiceInstructionConfig config =
                new ConditionalDistanceVoiceInstructionConfig(
                        "in_lower_distance", mockTranslationMap, Locale.ENGLISH,
                        geometryDistances, voiceValues
                );

        VoiceInstructionConfig.VoiceInstructionValue value =
                config.getConfigForDistance(30, "turn left", "");
        
        assertNull(value, "VoiceInstructionValue should be null for distance below first threshold");
    }
}
