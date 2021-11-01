import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WebServerTests {
    WebServer server;
    WebServer webServerMock = mock(WebServer.class);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRun() throws IOException {
        String[] args = null;
        server.main(args);
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            server.main(args);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}
