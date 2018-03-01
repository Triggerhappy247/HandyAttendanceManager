import javafx.application.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.*;
import javafx.stage.*;


public class HandyManager extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage stage) {
        Group root = new Group();
        Stop[] stops = new Stop[] {
                new Stop(0, Color.DARKSLATEBLUE),
                new Stop(1, Color.DARKRED)};
        Scene scene = new Scene(root,new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops));
        stage.setScene(scene);
        stage.setTitle("Hello");
        stage.show();
    }
}
