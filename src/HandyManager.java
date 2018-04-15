import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;


public class HandyManager extends Application {

    private Stage primaryStage,secondaryStage;
    private Faculty faculty;
    private DatabaseConnection db;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws IOException{
        setPrimaryStage(stage);
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

    public void aboutPage() throws IOException{
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AboutPage.fxml"));
            AnchorPane SlotView = (AnchorPane) loader.load();

            Scene scene = new Scene(SlotView);
            secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.initOwner(primaryStage);
            secondaryStage.setResizable(false);
            secondaryStage.setTitle("About Page");
            secondaryStage.show();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void logoutPage() throws IOException{

        db.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        AnchorPane content = (AnchorPane) loader.load();
        LoginController loginController = loader.getController();
        loginController.setManager(this);
        Scene scene = new Scene(content);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Login");
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
        primaryStage.show();
    }

    public void showTimeTable(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TimeTableView.fxml"));
            BorderPane Timetable = (BorderPane) loader.load();
            TimeTableViewController TTC = loader.getController();
            TTC.setManager(this);
            TTC.populateTimeTable(faculty.getTimeTable());

            Scene scene = new Scene(Timetable);
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.setTitle("Timetable of " + faculty.getIdFaculty() );
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
            primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSlot(TimeTableSlot slotInfo,boolean isFuture){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SlotView.fxml"));
            BorderPane SlotView = (BorderPane) loader.load();
            SlotViewController SVC = loader.getController();
            SVC.setSlotInfo(slotInfo);
            SVC.setAttendance(isFuture);
            SVC.fillInfoTable();
            SVC.setManager(this);

            Scene scene = new Scene(SlotView);
            secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.initOwner(primaryStage);
            secondaryStage.setResizable(false);
            secondaryStage.setTitle(String.format("%ss at %s",slotInfo.getDayOfWeek(),slotInfo.getTime()));
            secondaryStage.show();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void showMarkAttendance(TimeTableSlot timeTableSlot){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MarkAttendanceView.fxml"));
            BorderPane SlotView = (BorderPane) loader.load();
            MarkAttendanceController MAC = loader.getController();
            MAC.setTimeTableSlot(timeTableSlot);
            MAC.setDb(db);
            MAC.setTimeTable(faculty.getTimeTable());
            MAC.setFrozenDates(Attendance.getFreezeDates(getDb()));
            MAC.setStudentList(new StudentList(timeTableSlot.getStudentList(),timeTableSlot,faculty.getTimeTable(),db));
            MAC.populateAttendanceTable();


            Scene scene = new Scene(SlotView);
            secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.initOwner(primaryStage);
            secondaryStage.setResizable(false);
            secondaryStage.setTitle(String.format("Attendance View - %s/%s/%s",timeTableSlot.getSubject().getIdSubject(),timeTableSlot.getSlotType(),timeTableSlot.getStudentList()));
            secondaryStage.show();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setDb(DatabaseConnection db) {
        this.db = db;
    }

    public DatabaseConnection getDb() {
        return db;
    }
}
