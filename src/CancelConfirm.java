import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CancelConfirm implements Initializable {

    @FXML
    Button exit;

    MarkAttendanceController MAC;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage close = (Stage)exit.getScene().getWindow();
                close.close();
            }
        });
    }

    public CancelConfirm() {
    }

    public void setMAC(MarkAttendanceController MAC) {
        this.MAC = MAC;
    }

    public void cancelSlot(){
        MAC.confirmCancel();
        Stage close = (Stage)exit.getScene().getWindow();
        close.close();
    }
}
