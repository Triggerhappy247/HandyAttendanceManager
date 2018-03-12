import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;


public class LoginController {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public LoginController() {
    }

    @FXML
    private void initialize(){
    }

    @FXML
    private void loginFaculty(){
        DatabaseConnection db = new DatabaseConnection();
        db.connect("jdbc:mysql://localhost:3310/attendance_manager?useSSL=false");
        Faculty faculty = new Faculty(username.getText(),password.getText(),db);

        if(faculty != null)
        {
            FXMLLoader loader = new FXMLLoader();
            try {
                 GridPane Timetable = (GridPane) loader.load(new FileInputStream("C:\\Users\\qasim\\IdeaProjects\\HandyAttendanceManager\\src\\TimeTableView.fxml"));
                Scene scene = new Scene(Timetable);
                Stage currentStage = (Stage)username.getScene().getWindow();
                currentStage.setScene(scene);
                currentStage.setResizable(true);
                currentStage.setTitle("Timetable");
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                currentStage.setX((primScreenBounds.getWidth() - currentStage.getWidth()) / 2);
                currentStage.setY((primScreenBounds.getHeight() - currentStage.getHeight()) / 2);
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        db.close();
    }

}
