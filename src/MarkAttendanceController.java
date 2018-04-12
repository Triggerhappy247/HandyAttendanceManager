import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.sql.BatchUpdateException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ListIterator;
import java.util.ResourceBundle;

public class MarkAttendanceController implements Initializable {

    private TimeTableSlot timeTableSlot;
    private StudentList studentList;
    private ObservableList<StudentAttendanceTable> studentTableData;
    private DatabaseConnection db;
    private TimeTable timeTable;
    private String multipleSlots;

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
        dateList.setVisibleRowCount(7);
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
            studentTableData.add(new StudentAttendanceTable(student.getIdStudent(),this));
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
        ObservableList<LocalDate> allDates = FXCollections.observableList(studentList.getStudent().get(0).getAttendance().getAllDates());

        if(timeTableSlot.getSlotType().equalsIgnoreCase("Lecture"))
        {
            TimeTableSlot slots[] = timeTable.getSlotIds();
            for(TimeTableSlot slot : slots){
                if(timeTableSlot.isSameLecture(slot)){
                    multipleSlots = String.format("'%s'",slot.getIdTimeTableSlot());
                    setTimeTableSlot(slot);
                    break;
                }
            }
        }
        System.out.println(multipleSlots);

        dateList.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                setTable((LocalDate) newValue);
            }
        });

        dateList.setItems(allDates);
        dateList.setValue(allDates.get(allDates.size() - 1));

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveData();
            }
        });
    }

    public void deleteRecord(String idStudent){
        try {
            saveButton.setDisable(false);
            saveButton.setText("Save");
            String primarykey = String.format("%s/%s/%s",dateList.getValue().toString(),timeTableSlot.getIdTimeTableSlot(),idStudent);
            db.addBatch(String.format("delete from student_attendance_absent where primarykey = '%s'",primarykey));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveRecord(String idStudent){
        try {
            saveButton.setDisable(false);
            saveButton.setText("Save");
            String primarykey = String.format("%s/%s/%s",dateList.getValue().toString(),timeTableSlot.getIdTimeTableSlot(),idStudent);
            db.addBatch(String.format("insert into student_attendance_absent values('%s','%s','%s','%s');", primarykey, dateList.getValue(), timeTableSlot.getIdTimeTableSlot(), idStudent));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveData(){
        saveButton.setDisable(true);
        saveButton.setText("Saving...");
        try {
            db.batchUpdate();
            saveButton.setText("Saved!");
        } catch (BatchUpdateException e){
            saveButton.setText("Saved?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTable(LocalDate localDate){
        saveButton.setText("Save");
        saveButton.setDisable(true);
        try {
            for (StudentAttendanceTable aStudentTableData : studentTableData) {
                aStudentTableData.getPresent().setSelected(true);
            }
            ResultSet rs = db.queryDatabase(String.format("select idStudent from student_attendance_absent where date = '%s' and timeTableSlot = %s;",localDate.toString(),getMultipleSlots()));
            StudentAttendanceTable tempStudent;
            while (rs.next()) {
                tempStudent = new StudentAttendanceTable(rs.getString("idStudent"));
                studentTableData.get(studentTableData.indexOf(tempStudent)).getAbsent().setSelected(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    public void setDb(DatabaseConnection db) {
        this.db = db;
    }

    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

    public String getMultipleSlots() {
        return multipleSlots;
    }

    public void setMultipleSlots(String multipleSlots) {
        this.multipleSlots = multipleSlots;
    }

    public Button getSaveButton() {
        return saveButton;
    }
}
