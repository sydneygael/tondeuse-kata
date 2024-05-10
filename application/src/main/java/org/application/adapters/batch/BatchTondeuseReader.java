package org.application.adapters.batch;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemReader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;


@AllArgsConstructor
public class BatchTondeuseReader implements ItemReader<String> {
    
    private File filePath;
    private boolean read;
    @Override
    public String read() throws Exception {
        if(read) {
            Path path = filePath.toPath();
            read = false;
            return Files.readString(path);
        }
        return null;
    }
}
