package org.application.adapters.batch;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemReader;

import java.nio.file.Files;
import java.nio.file.Path;


@AllArgsConstructor
public class BatchTondeuseReader implements ItemReader<String> {

    private String filePath;
    private boolean read = false;
    @Override
    public String read() throws Exception {
        if(read) {
            Path path = Path.of(filePath);
            read = false;
            return Files.readString(path);
        }
        return null;
    }
}
