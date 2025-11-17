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

class DistanceConfigMockTest {

    private TranslationMap mockTranslationMap;

    @BeforeEach
    void setUp() {
        // Crée un mock TranslationMap et un mock Translation pour éviter NullPointerException
        mockTranslationMap = mock(TranslationMap.class);
        when(mockTranslationMap.getWithFallBack(any(Locale.class)))
                .thenReturn(mock(com.graphhopper.util.Translation.class));
    }

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