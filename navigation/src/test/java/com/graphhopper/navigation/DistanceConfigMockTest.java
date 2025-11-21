package com.graphhopper.navigation;

import com.graphhopper.util.TranslationMap;
import com.graphhopper.routing.util.TransportationMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
/**
 * Test unitaire pour la classe {@link DistanceConfig} en utilisant des mocks pour
 * {@link TranslationMap} et {@link com.graphhopper.util.Translation}.
 * Ces tests vérifient que les instructions vocales générées par {@link DistanceConfig#getVoiceInstructionsForDistance}
 * ne sont pas vides pour différentes unités de mesure (métrique et impérial) et pour différents modes de transport
 * (BICYCLE et FOOT) et donc que des instructions sont generes pour l'utilisateur.
 */

class DistanceConfigMockTest {
     /** Mock de TranslationMap utilisé pour injecter dans DistanceConfig. */
    private TranslationMap mockTranslationMap;
    /**
     * Initialise les mocks avant chaque test.
     * Crée un mock TranslationMap et configure un mock Translation pour éviter les NullPointerException.
     */
    @BeforeEach
    void setUp() {
        // Crée un mock TranslationMap et un mock Translation pour éviter NullPointerException
        mockTranslationMap = mock(TranslationMap.class);
        when(mockTranslationMap.getWithFallBack(any(Locale.class)))
                .thenReturn(mock(com.graphhopper.util.Translation.class));
    }
    /**
     * Test que la génération d'instructions vocales pour le mode BICYCLE avec unité métrique
     * renvoie une liste non vide.
     */
  @Test
void testVoiceInstructionsMetricBiking() {
    TranslationMap mockTranslationMap = mock(TranslationMap.class);
    com.graphhopper.util.Translation mockTranslation = mock(com.graphhopper.util.Translation.class);
    when(mockTranslationMap.getWithFallBack(any(Locale.class))).thenReturn(mockTranslation);
    when(mockTranslation.tr(anyString(), any())).thenReturn("dummy");

    DistanceConfig config = new DistanceConfig(
            DistanceUtils.Unit.METRIC,
            mockTranslationMap,
            Locale.ENGLISH,
            TransportationMode.BIKE
    );

    assertFalse(config.getVoiceInstructionsForDistance(150, "turn right", "").isEmpty());
}
    /**
     * Test que la génération d'instructions vocales pour le mode BICYCLE avec unité impériale
     * renvoie une liste non vide.
     */
@Test
void testVoiceInstructionsImperialBiking() {
    TranslationMap mockTranslationMap = mock(TranslationMap.class);
    com.graphhopper.util.Translation mockTranslation = mock(com.graphhopper.util.Translation.class);
    when(mockTranslationMap.getWithFallBack(any(Locale.class))).thenReturn(mockTranslation);
    when(mockTranslation.tr(anyString(), any())).thenReturn("dummy");

    DistanceConfig config = new DistanceConfig(
            DistanceUtils.Unit.IMPERIAL,
            mockTranslationMap,
            Locale.ENGLISH,
            TransportationMode.BIKE
    );

    assertFalse(config.getVoiceInstructionsForDistance(500, "turn right", "").isEmpty());
}
    /**
     * Test que la génération d'instructions vocales pour le mode FOOT (marche) avec unité métrique
     * renvoie une liste non vide.
     */
    @Test
    void testVoiceInstructionsMetricFoot() {
        DistanceConfig config = new DistanceConfig(
                DistanceUtils.Unit.METRIC,
                mockTranslationMap,
                Locale.ENGLISH,
                TransportationMode.FOOT
        );

        assertFalse(config.getVoiceInstructionsForDistance(50, "turn left", "").isEmpty(),
                "La liste des instructions ne doit pas être vide pour le mode FOOT (WALK)");
    }
    /**
     * Test que la génération d'instructions vocales pour le mode FOOT (marche) avec unité impériale
     * renvoie une liste non vide.
     */
    @Test
    void testVoiceInstructionsImperialFoot() {
        DistanceConfig config = new DistanceConfig(
                DistanceUtils.Unit.IMPERIAL,
                mockTranslationMap,
                Locale.ENGLISH,
                TransportationMode.FOOT
        );

        assertFalse(config.getVoiceInstructionsForDistance(50, "turn left", "").isEmpty(),
                "La liste des instructions ne doit pas être vide pour le mode FOOT (WALK) en IMPERIAL");
    }
}
