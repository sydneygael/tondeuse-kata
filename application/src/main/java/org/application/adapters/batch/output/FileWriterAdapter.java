package org.application.adapters.batch.output;

import org.domain.ports.ouput.WritePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

@Component
public class FileWriterAdapter implements WritePort {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileWriterAdapter.class);
    @Override
    public void writeResult(String result, OutputStreamWriter stream) {
        try {
            writeString(result, stream);
        } catch (IOException e) {
            LOGGER.error("une erreur s'est produite lors de l'écriture du flux : ",e);
        } finally {
            closeStream(stream);
        }
    }

    private void writeString(String result, Writer writer) throws IOException {
        writer.write(result);
        writer.flush(); // vider le flux
    }

    private void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close(); // Fermez le flux après utilisation
            } catch (IOException e) {
                LOGGER.error("une erreur s'est produite lors de la fermeture du flux : ",e);
            }
        }
    }
}
