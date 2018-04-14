import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.BatchUpdateException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private Button saveButton,cancelButton,cancelSlot;

    @FXML
    private ComboBox dateList;

    @FXML
    private Label cancelLabel;

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

        multipleSlots = String.format("'%s'",timeTableSlot.getIdTimeTableSlot());
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
            if(!cancelLabel.isVisible()) {
                saveButton.setDisable(false);
                saveButton.setText("Save");
                cancelButton.setText("Cancel");
                String primarykey = String.format("%s/%s/%s", dateList.getValue().toString(), timeTableSlot.getIdTimeTableSlot(), idStudent);
                db.addBatch(String.format("delete from student_attendance_absent where primarykey = '%s'", primarykey));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveRecord(String idStudent){
        try {
            if(!cancelLabel.isVisible()) {
                saveButton.setDisable(false);
                saveButton.setText("Save");
                cancelButton.setText("Cancel");
                String primary = String.format("%s/%s/%s", dateList.getValue().toString(), timeTableSlot.getIdTimeTableSlot(), idStudent);
                db.addBatch(String.format("insert into student_attendance_absent values('%s','%s','%s','%s');", primary, dateList.getValue(), timeTableSlot.getIdTimeTableSlot(), idStudent));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelSlot(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cancelConfirmation.fxml"));
            AnchorPane confirmView = (AnchorPane) loader.load();
            CancelConfirm CCC = loader.getController();
            CCC.setMAC(this);

            Scene scene = new Scene(confirmView);
            Stage confirm = new Stage();
            confirm.setScene(scene);
            confirm.initModality(Modality.WINDOW_MODAL);
            confirm.initOwner((Stage)saveButton.getScene().getWindow());
            confirm.setResizable(false);
            confirm.setTitle(String.format("Cancel %s/%s/%s on %s",timeTableSlot.getSubject().getIdSubject(),timeTableSlot.getSlotType(),timeTableSlot.getStudentList(),dateList.getValue().toString()));
            confirm.show();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void confirmCancel(){
        try {
            String primary = String.format("%s/%s/%s",dateList.getValue().toString(),timeTableSlot.getIdTimeTableSlot(),"Cancelled");
            db.updateDatabase(String.format("insert into student_attendance_absent values('%s','%s','%s','%s');", primary, dateList.getValue(), timeTableSlot.getIdTimeTableSlot(),"Cancelled"));
            setTable((LocalDate) dateList.getValue());
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
            cancelButton.setText("Close");
        } catch (BatchUpdateException e){
            saveButton.setText("Saved?");
            cancelButton.setText("Close");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTable(LocalDate localDate){
        saveButton.setText("Save");
        saveButton.setDisable(true);
        cancelButton.setText("Close");
        cancelSlot.setVisible(true);
        cancelLabel.setVisible(false);
        boolean cancelled = false;
        try {
            for (StudentAttendanceTable aStudentTableData : studentTableData) {
                aStudentTableData.getPresent().setSelected(true);
            }
            ResultSet rs = db.queryDatabase(String.format("select idStudent from student_attendance_absent where date = '%s' and timeTableSlot = %s;",localDate.toString(),getMultipleSlots()));
            StudentAttendanceTable tempStudent;
            if(rs.next()){
                cancelled = rs.getString("idStudent").contentEquals("Cancelled");
                rs.previous();
            }
            while (rs.next() && !cancelled) {
                tempStudent = new StudentAttendanceTable(rs.getString("idStudent"));
                studentTableData.get(studentTableData.indexOf(tempStudent)).getAbsent().setSelected(true);
                cancelSlot.setVisible(false);
            }
            if (cancelled){
                cancelLabel.setVisible(true);
                cancelSlot.setVisible(false);
                for (StudentAttendanceTable aStudentTableData : studentTableData) {
                    aStudentTableData.getAbsent().setSelected(true);
                }
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
