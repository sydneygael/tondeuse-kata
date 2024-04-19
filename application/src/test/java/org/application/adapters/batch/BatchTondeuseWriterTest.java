package org.application.adapters.batch;

import org.application.adapters.batch.output.FileWriterAdapter;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.Chunk;

import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class BatchTondeuseWriterTest {

    @Mock
    private FileWriterAdapter fileWriterAdapter;

    @Captor
    private ArgumentCaptor<String> resultCaptor;

    @Test
    void testWrite() throws Exception {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // fichier temporaire
        var tempFile = Files.createTempFile("test_output", ".txt");

        // Given
        var batchTondeuseWriter = new BatchTondeuseWriter(fileWriterAdapter, tempFile.toString());
        var items = Arrays.asList("Item 1", "Item 2", "Item 3");

        // When
        batchTondeuseWriter.write(new Chunk<>(items));

        // Then
        verify(fileWriterAdapter).writeResult(resultCaptor.capture(),
                ArgumentCaptor.forClass(OutputStreamWriter.class).capture());
        var capturedResult = resultCaptor.getValue();
        var expectedContent = String.join("", items);
        assertEquals(expectedContent, capturedResult);

        // detruire le fichier temporaire
        Files.delete(tempFile);
    }
}
