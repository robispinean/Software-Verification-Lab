package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {

    public byte[] readFileByte(File file) throws IOException {
        FileInputStream f = null;
        byte[] data = new byte[(int) file.length()];

        try {
            f = new FileInputStream(file);
            f.read(data);
        } finally {
            if (f != null)
                f.close();
        }
        return data;
    }

    public String readFileString(File file) throws IOException {
        String data;
        data = new String(this.readFileByte(file));
        return data;
    }

    public String fileType(String extension) {
        if (extension.contains(".html")) return "text/html";
        if (extension.contains(".css")) return "text/css";
        if (extension.contains(".jpg")) return "image/jpg";
        if (extension.contains(".txt")) return "text/txt";
        return null;
    }
}
