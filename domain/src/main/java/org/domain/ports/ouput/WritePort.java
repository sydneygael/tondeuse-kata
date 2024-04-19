package org.domain.ports.ouput;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

public interface WritePort {
    void writeResult(String result, OutputStreamWriter stream);
}
