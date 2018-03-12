import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;

import java.io.FileInputStream;
import java.io.IOException;


public class HandyManager extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage stage) throws IOException{
        AnchorPane content = null;
        FXMLLoader loader = new FXMLLoader();
        content = (AnchorPane) loader.load(new FileInputStream("C:\\Users\\qasim\\IdeaProjects\\HandyAttendanceManager\\src\\LoginPage.fxml"));
            Scene scene = new Scene(content);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
    }

}
