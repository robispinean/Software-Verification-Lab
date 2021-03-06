import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {

    @Override
    public void start(Stage stage) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("Web Server");
            stage.setScene(scene);
            stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}