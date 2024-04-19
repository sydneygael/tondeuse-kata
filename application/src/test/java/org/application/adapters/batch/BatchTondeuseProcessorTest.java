package org.application.adapters.batch;

import org.domain.service.TondeuseService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BatchTondeuseProcessorTest {

    private final BatchTondeuseProcessor batchTondeuseProcessor = new BatchTondeuseProcessor(new TondeuseService());

    @Test
    void process_WithValidInput_ShouldReturnExpectedResult() throws Exception {
        // Given
        String input = "5 5\n" +
                "1 2 N\n" +
                "GAGAGAGAA\n" +
                "3 3 E\n" +
                "AADAADADDA";

        // When
        String result = batchTondeuseProcessor.process(input);

        // Then
        assertEquals("1 3 N 5 1 E", result);
    }

    @Test
    void process_WithNoInstructions_ShouldThrowIllegalArgumentException() {
        // Given
        String input = "5 5\n" +
                "1 2 N";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            batchTondeuseProcessor.process(input);
        });
    }

    @Test
    void process_WithNoTondeuse_ShouldThrowIllegalArgumentException() {
        // Given
        String input = "5 5";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            batchTondeuseProcessor.process(input);
        });
    }
}
