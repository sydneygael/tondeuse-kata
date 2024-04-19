package org.application.adapters.batch;

import org.application.adapters.batch.output.FileWriterAdapter;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

@Component
public class BatchTondeuseWriter implements ItemWriter<String> {

    private final FileWriterAdapter fileWriterAdapter;

    @Value("${output.file}")
    private String outputFile;

    public BatchTondeuseWriter(FileWriterAdapter fileWriterAdapter) {
        this.fileWriterAdapter = fileWriterAdapter;
    }

    @Override
    public void write(Chunk<? extends String> chunk) throws Exception {
        // Créer un flux de sortie pour stocker les résultats
        var outputStream = new FileOutputStream(outputFile);
        var writer = new OutputStreamWriter(outputStream);

        // Concaténer tous les résultats dans une seule chaîne
        StringBuilder resultBuilder = new StringBuilder();
        for (String item : chunk.getItems()) {
            resultBuilder.append(item);
        }
        String result = resultBuilder.toString();

        // Utiliser l'adaptateur pour écrire le résultat dans le flux de sortie
        fileWriterAdapter.writeResult(result, writer);

        // Assurez-vous de fermer le flux de sortie après utilisation
        writer.close();
        outputStream.close();
    }
}

