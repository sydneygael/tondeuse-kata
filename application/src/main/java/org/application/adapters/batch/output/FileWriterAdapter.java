package org.application.adapters.batch.output;

import org.application.ports.ouput.WritePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;


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
        writer.flush();
    }

    private void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LOGGER.error("une erreur s'est produite lors de la fermeture du flux : ",e);
            }
        }
    }
}
