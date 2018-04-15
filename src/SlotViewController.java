import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SlotViewController implements Initializable {

    @FXML
    private Button attendanceButton,backButton;

    @FXML
    private TableColumn property,value;
    @FXML
    private TableView<SlotViewTableData> infoTable;

    private HandyManager manager;
    private TimeTableSlot slotInfo;
    private boolean isAttendance;

    public SlotViewController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               Stage close = (Stage)backButton.getScene().getWindow();
               close.close();
            }
        });
    }

    public void fillInfoTable(){

        if(isAttendance())
            attendanceButton.setText("View Attendance");
        else{
            attendanceButton.setText("Mark Attendance");
        }
        attendanceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage secondary = (Stage)backButton.getScene().getWindow();
                secondary.hide();
                manager.showMarkAttendance(slotInfo);
                }
            });
        final ObservableList<SlotViewTableData> slotTable = FXCollections.observableArrayList(
        new SlotViewTableData("Subject Code",slotInfo.getSubject().getIdSubject()),
        new SlotViewTableData("Subject",slotInfo.getSubject().getSubName()),
        new SlotViewTableData("Slot Type",slotInfo.getSlotType()),
        new SlotViewTableData("Day",slotInfo.getDayOfWeek()),
        new SlotViewTableData("Time",slotInfo.getTime()),
        new SlotViewTableData("Room",slotInfo.getRoom()),
        new SlotViewTableData("Student Batch",slotInfo.getStudentList())
        );
        infoTable.setItems(slotTable);

        property.setCellValueFactory(
                new PropertyValueFactory<SlotViewTableData,String>("property")
        );
        value.setCellValueFactory(
                new PropertyValueFactory<SlotViewTableData,String>("value")
        );
    }

    public void setSlotInfo(TimeTableSlot slotInfo) {
        this.slotInfo = slotInfo;
    }

    public void setAttendance(boolean attendance) {
        isAttendance = attendance;
    }

    public boolean isAttendance() {
        return isAttendance;
    }

    public void setManager(HandyManager manager) {
        this.manager = manager;
    }
}
