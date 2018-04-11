import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ListIterator;
import java.util.ResourceBundle;

public class MarkAttendanceController implements Initializable {

    private TimeTableSlot timeTableSlot;
    private StudentList studentList;
    private ObservableList<StudentAttendanceTable> studentTableData;
    private DatabaseConnection db;

    @FXML
    private TableView attendanceTable;

    @FXML
    private TableColumn studentColumn,presentColumn,absentColumn;

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

    public void populateAttendanceTable() {
        final ObservableList<StudentAttendanceTable> studentTableData = FXCollections.observableArrayList();
        for (Student student : studentList.getStudent()) {
            studentTableData.add(new StudentAttendanceTable(student.getIdStudent()));
        }

        attendanceTable.setItems(studentTableData);
        this.studentTableData = studentTableData;
        studentColumn.setCellValueFactory(
                new PropertyValueFactory<StudentAttendanceTable,String>("studentID")
        );
        presentColumn.setCellValueFactory(
                new PropertyValueFactory<StudentAttendanceTable,RadioButton>("present")
        );
        absentColumn.setCellValueFactory(
                new PropertyValueFactory<StudentAttendanceTable,RadioButton>("absent")
        );

        dateList.setItems(FXCollections.observableList(studentList.getStudent().get(0).getAttendance().getAllDates()));

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveData();
            }
        });
    }

    public void saveData(){
        for (StudentAttendanceTable student : studentTableData) {
            RadioButton button = (RadioButton) student.getToggleGroup().getSelectedToggle();
            System.out.println(student.getStudentID() + "-" + button.getText());
        }
    }

    public TimeTableSlot getTimeTableSlot() {
        return timeTableSlot;
    }

    public void setTimeTableSlot(TimeTableSlot timeTableSlot) {
        this.timeTableSlot = timeTableSlot;
    }

    public void setStudentList(StudentList studentList) {
        this.studentList = studentList;
    }
}
