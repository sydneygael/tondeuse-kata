package org.application.adapters.batch;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BatchTondeuseReaderTest {

    @Test
    void testRead() throws Exception {
        // Given
        var expectedContent = "Contenu du fichier test";
        Path tempFile = Files.createTempFile("test", ".txt");
        Files.writeString(tempFile, expectedContent);

        // Créer une instance de BatchTondeuseReader avec le chemin du fichier temporaire
        var batchTondeuseReader = new BatchTondeuseReader(tempFile.toString(),true);

        // When
        var content = batchTondeuseReader.read();

        // Then
        assertEquals(expectedContent, content);

        // Supprimer le fichier temporaire après le test
        Files.delete(tempFile);
    }
}

