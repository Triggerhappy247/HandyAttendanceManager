import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SingleStudentAttendanceController implements Initializable {

    @FXML
    private TableView studentAttendanceTable;
    @FXML
    private TableColumn dates,attendance;
    @FXML
    private Button close;
    @FXML
    private Label attendancePercentage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentAttendanceTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public SingleStudentAttendanceController() {
    }

    public void populateStudentTable(String idStudent,String slots,ArrayList<LocalDate> allDates,DatabaseConnection db){
        ArrayList<LocalDate> absentDates = Attendance.getAbsentDates(idStudent,slots,db);
        ObservableList<SingleStudentTable> studentAttendance = FXCollections.observableArrayList();

        double percentage = (1 - (double)absentDates.size()/allDates.size()) * 100;
        attendancePercentage.setText(String.format("%.2f%%",percentage));
        for (LocalDate localDate : allDates)
        {
            if(absentDates.contains(localDate)) {
                SingleStudentTable temp = new SingleStudentTable(localDate.toString(),"Absent");
                studentAttendance.add(temp);
            } else {
                SingleStudentTable temp = new SingleStudentTable(localDate.toString(),"Present");
                studentAttendance.add(temp);
            }
        }

        studentAttendanceTable.setItems(studentAttendance);
        dates.setCellValueFactory(
                new PropertyValueFactory<SingleStudentTable,String>("date")
        );
        attendance.setCellValueFactory(
                new PropertyValueFactory<SingleStudentTable,String>("attendanceValue")
        );
    }

    public void closeWindow(){
        Stage stage = (Stage)close.getScene().getWindow();
        stage.close();
    }
}
