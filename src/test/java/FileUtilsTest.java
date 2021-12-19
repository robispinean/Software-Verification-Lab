import org.junit.Test;
import utils.FileUtils;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class FileUtilsTest {

    @Test
    public void readFileByteTXT() throws IOException{
        FileUtils fu = new FileUtils();
        String mockedString = "Hello TXT works";
        File f = new File("src/main/TestSite/a.txt");
        assertEquals(mockedString, new String(fu.readFileByte(f), "UTF-8"));
    }

    @Test
    public void readFileStringTXT() throws IOException{
        FileUtils fu = new FileUtils();
        String mockedString = "Hello TXT works";
        File f = new File("src/main/TestSite/a.txt");
        assertEquals(mockedString, fu.readFileString(f));
    }

    @Test
    public void fileTypeHtml(){
        FileUtils fu = new FileUtils();
        String mockedFile = "mocked.html";
        assertEquals("text/html", fu.fileType(mockedFile));
    }

    @Test
    public void fileTypeCss(){
        FileUtils fu = new FileUtils();
        String mockedFile = "mocked.css";
        assertEquals("text/css", fu.fileType(mockedFile));
    }

    @Test
    public void fileTypeJpg(){
        FileUtils fu = new FileUtils();
        String mockedFile = "mocked.jpg";
        assertEquals("image/jpg", fu.fileType(mockedFile));
    }

    @Test
    public void fileTypeTxt(){
        FileUtils fu = new FileUtils();
        String mockedFile = "mocked.txt";
        assertEquals("text/txt", fu.fileType(mockedFile));
    }

    @Test
    public void fileTypeNONE(){
        FileUtils fu = new FileUtils();
        String mockedFile = "mocked.abc";
        assertEquals(null, fu.fileType(mockedFile));
    }

    @Test
    public void checkFilePathTest(){
        FileUtils fu = new FileUtils();
        assertEquals( "src/main/TestSite/a.html", fu.checkFilePath("GET / a.html"));
    }

    @Test
    public void checkFilePathNullTest(){
        FileUtils fu = new FileUtils();
        assertNull( fu.checkFilePath("GET / HTTP/1.1"));
    }

    @Test
    public void replyTest(){
        FileUtils fu = new FileUtils();
        PrintWriter os = mock(PrintWriter.class);

        File file = new File("src/main/TestSite/a.html");
        assertEquals("Got the file" + file + " file type: " + "text/html" + ", length:" + 10, fu.reply(os, file, 10));
    }

}
