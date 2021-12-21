import org.junit.Test;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class WebServerTests {
    WebServer webServer = null;

    @Test
    public void testServerSocketWithSpecificPortGetsCreated() throws IOException {
        final int testPort = 9001;
        ServerSocket testServerSocket = new ServerSocket(testPort);
        assertEquals(testServerSocket.getLocalPort(), testPort);
    }

    @Test
    public void testClientSocketGetsCreated() throws IOException {
        ServerSocket mockServerSocket = mock(ServerSocket.class);
        when(mockServerSocket.accept()).thenReturn(new Socket());
        Socket clientSocket = mockServerSocket.accept();
        assertNotNull(clientSocket);
    }

    @Test
    public void WebServer() throws IOException {
        ServerSocket sSocket = mock(ServerSocket.class);
        when(sSocket.accept()).thenReturn(new Socket());
        Socket sClient = sSocket.accept();
        webServer = new WebServer(sClient);
        sClient.close();
        sSocket.close();
    }

    @Test
    public void runTest() throws IOException, URISyntaxException {
        webServer.STATUS = "RUNNING";
        ServerSocket sSocket = new ServerSocket(8081);
        Desktop d = Desktop.getDesktop();
        d.browse(new java.net.URI("http://localhost:8081"));
        webServer = new WebServer(sSocket.accept());
        webServer.run();
    }

    @Test
    public void runTestFileNotFound() throws IOException, URISyntaxException {
        webServer.STATUS = "RUNNING";
        ServerSocket sSocket = new ServerSocket(8080);
        Desktop d = Desktop.getDesktop();
        d.browse(new java.net.URI("http://localhost:8080/FileDoesntExit.html"));
        webServer = new WebServer(sSocket.accept());
        webServer.run();
    }

    @Test
    public void StartServerTest() throws IOException, URISyntaxException {
        webServer.STATUS="RUNNING";
        WebServer.timeout=1000;
        ServerSocket sSocket = mock(ServerSocket.class);
        when(sSocket.accept()).thenReturn(new Socket());
        Desktop d = Desktop.getDesktop();
        d.browse(new java.net.URI("http://localhost:8080"));
        Socket sClient = sSocket.accept();
        webServer = new WebServer(sClient);
        webServer.StartServer();
        when(sSocket.accept()).thenReturn(sClient);
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
        System.setIn(new ByteArrayInputStream("0\n".getBytes()));

    }

}
