import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MarkAttendanceController implements Initializable {

    private TimeTableSlot timeTableSlot;
    private StudentList studentList;
    private Attendance attendance;

    @FXML
    private TableView attendanceTable;

    @FXML
    private TableColumn studentColumn,attendanceColumn;

    @FXML
    private Button saveButton,cancelButton;

    @FXML
    private ComboBox dateList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage close = (Stage)cancelButton.getScene().getWindow();
                close.close();
            }
        });
    }

    public MarkAttendanceController() {
    }

    public TimeTableSlot getTimeTableSlot() {
        return timeTableSlot;
    }

    public void setTimeTableSlot(TimeTableSlot timeTableSlot) {
        this.timeTableSlot = timeTableSlot;
    }

    public StudentList getStudentList() {
        return studentList;
    }

    public void setStudentList(StudentList studentList) {
        this.studentList = studentList;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }
}
