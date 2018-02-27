import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HandyManager extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) {
        Parent root = new BorderPane();
        primaryStage.setTitle("Handy Attendance Manager");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }
}
