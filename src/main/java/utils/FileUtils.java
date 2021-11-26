package utils;

import java.io.*;

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

    public static String reply(PrintStream os, File f, int len){
        String contentType = fileType(f.toString());
        if(contentType == null) return "File type not supported";

        os.print("HTTP:/1.0 200 OK\n");
        os.print("Content-type:" +  contentType + "\n");
        os.print("Content-length: "+ len +"\n");
        os.print("\n");

        return "Got the file" + f + " file type: " + contentType + ", length:" + len;
    }

    public static String fileType(String fileName) {
        if (fileName.contains(".html")) return "text/html";
        if (fileName.contains(".css")) return "text/css";
        if (fileName.contains(".jpg")) return "image/jpg";
        if (fileName.contains(".txt")) return "text/txt";
        return null;
    }

    public static String checkFilePath(String fileName) {
        if (fileName.length() == 0 || !fileName.substring(0, 3).equals("GET")) return null;
        fileName = fileName.replace("GET / ", "");
        if (fileType(fileName) != null){
            fileName = fileName.replace(" ", "%20");
            return "src/main/TestSite/" + fileName;
        }
        return null;
    }
}
