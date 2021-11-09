import org.junit.Test;
import utils.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.mockito.Mockito.*;

public class WebServerTests {
    WebServer webServer = null;
    private FileUtils FileUtil = mock(FileUtils.class);

    @Test
    public void WebServer() throws IOException {
        ServerSocket sSocket = new ServerSocket(8002);
        Socket sClient = new Socket("127.0.0.1", 8002);
        sSocket.accept();
        webServer = new WebServer(sClient);
        sClient.close();
        sSocket.close();
    }

    @Test
    public void StartServerTest() throws IOException {
        ServerSocket sSocket = new ServerSocket(8002);
        Socket sClient = new Socket("127.0.0.1", 8002);
        webServer = new WebServer(sClient);
        webServer.STATUS="RUNNING";
        sSocket.accept();
        sClient.close();
        sSocket.close();
    }

    @Test
    public void Maintenance() throws IOException {
        webServer.STATUS = "MAINTENANCE";
        ServerSocket sSocket = new ServerSocket(8003);
        Socket sClient = new Socket("127.0.0.1", 8003);
        webServer = new WebServer(sClient);
        sClient.close();
        sSocket.close();
    }

    @Test
    public void StopServer() throws IOException {
        webServer.STATUS = "STOPPED";
        ServerSocket sSocket = new ServerSocket(8002);
        Socket sClient = new Socket("127.0.0.1", 8002);
        webServer = new WebServer(sClient);
        sClient.close();
        sSocket.close();

    }

    @Test
    public void ExitPTest() throws IOException {
        webServer.STATUS = "EXIT";
        ServerSocket sSocket = new ServerSocket(8002);
        Socket sClient = new Socket("127.0.0.1", 8002);
        sSocket.accept();
        webServer = new WebServer(sClient);
        sClient.close();
        sSocket.close();
    }

    @Test
    public void CliConfigTest() throws IOException {
        ServerSocket sSocket = new ServerSocket(8002);
        Socket sClient = new Socket("127.0.0.1", 8002);
        webServer = new WebServer(sClient);
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        webServer.CLIConfig();
        System.setIn(new ByteArrayInputStream("2\n".getBytes()));
        webServer.CLIConfig();
        System.setIn(new ByteArrayInputStream("3\n".getBytes()));
        webServer.CLIConfig();

    }

}
