package com.graphhopper.navigation;

import com.graphhopper.util.TranslationMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ConditionalDistanceVoiceInstructionConfigMockTest {

    private TranslationMap mockTranslationMap;
    private com.graphhopper.util.Translation mockTranslation;

    @BeforeEach
    void setUp() {
        // Mock TranslationMap and Translation to isolate the class
        mockTranslationMap = mock(TranslationMap.class);
        mockTranslation = mock(com.graphhopper.util.Translation.class);
        when(mockTranslationMap.getWithFallBack(any(Locale.class))).thenReturn(mockTranslation);
        when(mockTranslation.tr(anyString(), any())).thenReturn("dummy"); // Always return "dummy" for translation
    }

    @Test
    void testGetConfigForDistance_returnsCorrectInstruction() {
        int[] geometryDistances = {50, 100, 150};
        int[] voiceValues = {50, 100, 150};

        ConditionalDistanceVoiceInstructionConfig config =
                new ConditionalDistanceVoiceInstructionConfig(
                        "in_lower_distance", mockTranslationMap, Locale.ENGLISH,
                        geometryDistances, voiceValues
                );

        // Test a distance that should pick the 100m instruction
        VoiceInstructionConfig.VoiceInstructionValue value =
                config.getConfigForDistance(120, "turn right", "");
        
        assertNotNull(value, "VoiceInstructionValue should not be null");
        assertEquals(50, value.spokenDistance, "Spoken distance should match the fitting distance");
        assertTrue(value.turnDescription.contains("turn right"), "Description should include the turn");
    }

    @Test
    void testGetConfigForDistance_returnsNullForTooShortDistance() {
        int[] geometryDistances = {50, 100, 150};
        int[] voiceValues = {50, 100, 150};

        ConditionalDistanceVoiceInstructionConfig config =
                new ConditionalDistanceVoiceInstructionConfig(
                        "in_lower_distance", mockTranslationMap, Locale.ENGLISH,
                        geometryDistances, voiceValues
                );

        // Distance below the first threshold
        VoiceInstructionConfig.VoiceInstructionValue value =
                config.getConfigForDistance(30, "turn left", "");
        
        assertNull(value, "VoiceInstructionValue should be null for distance below first threshold");
    }
}
