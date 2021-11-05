import org.junit.Test;
import utils.FileUtils;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

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
}
