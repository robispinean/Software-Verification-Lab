import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class GUIController {

    public static WebServer webServerGUI = null;

    @FXML
    private TextField PORT;

    @FXML
    private Text STATUS;

    @FXML
    void setPort(ActionEvent event) {
        webServerGUI.PORT = Integer.parseInt(PORT.getText());
    }

    @FXML
    void startServer(ActionEvent event) {
        webServerGUI.STATUS = "RUNNING";
        STATUS.setText("RUNNING");
        serverThread();
    }

    @FXML
    void stopServer(ActionEvent event) {
        webServerGUI.STATUS = "STOPPED";
        STATUS.setText("STOPPED");
    }

    @FXML
    public void initialize() {
        PORT.setText("8080");
        STATUS.setText("STOPPED");
        webServerGUI.STATUS = "STOPPED";

    }

    private static void serverThread() {
        Thread serverThread = new Thread(() -> {
            try {
                WebServer.StartServer();
            } catch (Exception e) {
                System.err.println("Error starting thread");
            }
        });

        serverThread.start();
    }

}
