package org.application.adapters.batch.output;

import org.domain.ports.ouput.WritePort;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

@Component
public class FileWriterAdapter implements WritePort {
    @Override
    public void writeResult(String result, OutputStreamWriter stream) {
        try {
            writeString(result, stream);
        } catch (IOException e) {
            // Gérer l'exception en fonction de vos besoins
            e.printStackTrace();
        } finally {
            closeStream(stream);
        }
    }

    private void writeString(String result, Writer writer) throws IOException {
        writer.write(result);
        writer.flush(); // Assurez-vous de vider le flux
    }

    private void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close(); // Fermez le flux après utilisation
            } catch (IOException e) {
                // Gérer l'exception en fonction de vos besoins
                e.printStackTrace();
            }
        }
    }
}
