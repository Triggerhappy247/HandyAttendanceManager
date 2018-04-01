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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SlotViewController implements Initializable {

    @FXML
    private Button attendanceButton,backButton;

    @FXML
    private TableColumn property,value;
    @FXML
    private TableView<SlotViewTableData> infoTable;

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
        {
            attendanceButton.setDisable(true);
        }

        List slotTable = new ArrayList();
        slotTable.add(new SlotViewTableData("Subject Code",slotInfo.getSubject().getIdSubject()));
        slotTable.add(new SlotViewTableData("Subject",slotInfo.getSubject().getSubName()));
        slotTable.add(new SlotViewTableData("Slot Type",slotInfo.getSlotType()));
        slotTable.add(new SlotViewTableData("Day",slotInfo.getDayOfWeek()));
        slotTable.add(new SlotViewTableData("Time",slotInfo.getTime()));
        slotTable.add(new SlotViewTableData("Room",slotInfo.getRoom()));
        slotTable.add(new SlotViewTableData("Student Batch",slotInfo.getStudentList()));

        ObservableList<SlotViewTableData> data = FXCollections.observableList(slotTable);
        infoTable.setItems(data);

        property.setCellValueFactory(new PropertyValueFactory<SlotViewTableData,String>("property"));
        value.setCellValueFactory(new PropertyValueFactory<SlotViewTableData,String>("value"));
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
}
