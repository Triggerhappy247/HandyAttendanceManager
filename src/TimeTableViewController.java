import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeTableViewController {

    @FXML
    private Label mondayDate,tuesdayDate,wednesdayDate,thursdayDate,fridayDate,saturdayDate,test;


    public TimeTableViewController() {

    }

    @FXML
    private void initialize(){
        GregorianCalendar today = (GregorianCalendar) Calendar.getInstance();
        today.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        mondayDate.setText(format.format(today.getTime()));
        today.add(Calendar.DAY_OF_WEEK,1);
        tuesdayDate.setText(format.format(today.getTime()));
        today.add(Calendar.DAY_OF_WEEK,1);
        wednesdayDate.setText(format.format(today.getTime()));
        today.add(Calendar.DAY_OF_WEEK,1);
        thursdayDate.setText(format.format(today.getTime()));
        today.add(Calendar.DAY_OF_WEEK,1);
        fridayDate.setText(format.format(today.getTime()));
        today.add(Calendar.DAY_OF_WEEK,1);
        saturdayDate.setText(format.format(today.getTime()));
    }

    @FXML
    private void hoverStart(){
        test.setStyle("-fx-background-color: #878787");
    }

    @FXML
    private void hoverEnd(){
        test.setStyle("-fx-background-color: #b7b7b7");
    }
}
