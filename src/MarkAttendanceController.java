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
        ObservableList<LocalDate> allDates = FXCollections.observableList(studentList.getStudent().get(0).getAttendance().getAllDates());
        dateList.setItems(allDates);
        dateList.setValue(allDates.get(allDates.size() - 1));

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveData();
            }
        });

        setMultipleSlots(String.format("'%s'",timeTableSlot.getIdTimeTableSlot()));
        if(timeTableSlot.getSlotType().equalsIgnoreCase("Lecture"))
        {
            TimeTableSlot slots[] = timeTable.getSlotIds();
            for(TimeTableSlot slot : slots){
                if(timeTableSlot.isSameLecture(slot)){
                    multipleSlots = String.format("%s or '%s'",multipleSlots,slot.getIdTimeTableSlot());
                }
            }
        }

        dateList.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    ResultSet rs = db.queryDatabase(String.format("select idStudent from student_attendance_absent where date = '%s' and timeTableSlot = %s;",newValue.toString(),getMultipleSlots()));
                    StudentAttendanceTable tempStudent;
                    if(rs.next()) {
                        rs.previous();
                        while (rs.next()) {
                            tempStudent = new StudentAttendanceTable(rs.getString("idStudent"));
                            studentTableData.get(studentTableData.indexOf(tempStudent)).getAbsent().setSelected(true);
                        }
                    }else
                    {
                        for(ListIterator<StudentAttendanceTable> iterator = studentTableData.listIterator();iterator.hasNext();){
                            iterator.next().getPresent().setSelected(true);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void saveData(){
        RadioButton button;
        String localDate = dateList.getValue().toString();
        System.out.println(localDate);
        for (StudentAttendanceTable student : studentTableData) {
            button = (RadioButton) student.getToggleGroup().getSelectedToggle();
            if(button.getText().equalsIgnoreCase("Absent")) {
                try {
                    db.updateDatabase(String.format("insert into student_attendance_absent (date,timeTableSlot,idStudent) values('%s','%s','%s');", localDate, timeTableSlot.getIdTimeTableSlot(), student.getStudentID()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
}
