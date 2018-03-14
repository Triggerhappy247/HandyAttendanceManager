import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class TimeTableViewController implements Initializable{

    @FXML
    private Label mondayDate,tuesdayDate,wednesdayDate,thursdayDate,fridayDate,saturdayDate,test;

    public TimeTableViewController() {

    }

    @FXML
    public void initialize(URL url, ResourceBundle rb){
        GregorianCalendar today = (GregorianCalendar) Calendar.getInstance();
        today.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        mondayDate.setText(String.format("Monday\n%s", format.format(today.getTime())));
        today.add(Calendar.DAY_OF_WEEK,1);
        tuesdayDate.setText(String.format("Tuesday\n%s", format.format(today.getTime())));
        today.add(Calendar.DAY_OF_WEEK,1);
        wednesdayDate.setText(String.format("Wednesday\n%s", format.format(today.getTime())));
        today.add(Calendar.DAY_OF_WEEK,1);
        thursdayDate.setText(String.format("Thursday\n%s", format.format(today.getTime())));
        today.add(Calendar.DAY_OF_WEEK,1);
        fridayDate.setText(String.format("Friday\n%s", format.format(today.getTime())));
        today.add(Calendar.DAY_OF_WEEK,1);
        saturdayDate.setText(String.format("Saturday\n%s", format.format(today.getTime())));
    }

    public void populateTimeTable(Faculty faculty){
        try{
            System.out.println(faculty.getPassword());
        }
        catch (NullPointerException e){
            System.out.println("Faculty is NULL bro");
        }
    }


    @FXML
    private void hoverStart(){
        test.setStyle("-fx-background-color: #b7b7b7");
    }

    @FXML
    private void hoverEnd(){
        test.setStyle("-fx-background-color: #f4f4f4");
    }
}
