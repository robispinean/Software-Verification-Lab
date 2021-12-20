import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class WebServerTests {
    WebServer webServer = null;

    @Test
    public void WebServer() throws IOException {
        ServerSocket sSocket = new ServerSocket(8001);
        Socket sClient = new Socket("127.0.0.1", 8001);
        sSocket.accept();
        webServer = new WebServer(sClient);
        sClient.close();
        sSocket.close();
    }

    @Test
    public void runTest() throws IOException {
        webServer.STATUS = "RUNNING";
        ServerSocket sSocket = new ServerSocket(8888);
        webServer = new WebServer(sSocket.accept());
        webServer.run();
        sSocket.close();
    }

    @Test
    public void Maintenance() throws IOException {
        webServer.STATUS = "MAINTENANCE";
        ServerSocket sSocket = new ServerSocket(8888);
        Socket sClient = new Socket("127.0.0.1", 8888);
        webServer = new WebServer(sClient);
        sClient.close();
        sSocket.close();
    }

    @Test
    public void StopServer() throws IOException {
        webServer.STATUS = "STOPPED";
        ServerSocket sSocket = new ServerSocket(8004);
        Socket sClient = new Socket("127.0.0.1", 8004);
        webServer = new WebServer(sClient);
        sClient.close();
        sSocket.close();

    }

    @Test
    public void ExitPTest() throws IOException {
        webServer.STATUS = "EXIT";
        ServerSocket sSocket = new ServerSocket(8005);
        Socket sClient = new Socket("127.0.0.1", 8005);
        sSocket.accept();
        webServer = new WebServer(sClient);
        sClient.close();
        sSocket.close();
    }

    @Test
    public void CliConfigTest() throws IOException {
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        webServer.CLIConfig();
        System.setIn(new ByteArrayInputStream("2\n".getBytes()));
        webServer.CLIConfig();
        System.setIn(new ByteArrayInputStream("3\n".getBytes()));
        webServer.CLIConfig();

    }

}
