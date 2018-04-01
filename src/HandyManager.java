import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.*;
import java.io.IOException;


public class HandyManager extends Application {

    private Stage stage;
    private Faculty faculty;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws IOException{
        setStage(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        AnchorPane content = (AnchorPane) loader.load();
        LoginController loginController = loader.getController();
        loginController.setManager(this);
        Scene scene = new Scene(content);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.show();
    }

    public void showTimeTable(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TimeTableView.fxml"));
            GridPane Timetable = (GridPane) loader.load();
            TimeTableViewController TTC = loader.getController();
            TTC.setManager(this);
            TTC.populateTimeTable(faculty);

            Scene scene = new Scene(Timetable);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setTitle("Timetable of " + faculty.getIdFaculty() );
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSlot(TimeTableSlot slotInfo){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SlotView.fxml"));
            BorderPane SlotView = (BorderPane) loader.load();
            SlotViewController SVC = loader.getController();
            SVC.setSlotInfo(slotInfo);
            SVC.fillInfoTable();

            Scene scene = new Scene(SlotView);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.initOwner(stage);
            secondaryStage.setResizable(false);
            secondaryStage.setTitle(String.format("%ss at %s",slotInfo.getDayOfWeek(),slotInfo.getTime()));
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            secondaryStage.setX((primScreenBounds.getWidth() - secondaryStage.getWidth()) / 2);
            secondaryStage.setY((primScreenBounds.getHeight() - secondaryStage.getHeight()) / 2);
            secondaryStage.show();
        } catch (IOException e) {
            e.getStackTrace();
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Faculty getFaculty() {
        return faculty;
    }
}
