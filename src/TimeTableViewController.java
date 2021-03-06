import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Double.MAX_VALUE;

public class TimeTableViewController implements Initializable{

    private HandyManager manager;
    private GregorianCalendar today;
    @FXML
    private Label mondayDate,tuesdayDate,wednesdayDate,thursdayDate,fridayDate,saturdayDate;

    @FXML
    private ArrayList<HBox> SlotSpace;

    @FXML
    private MenuItem logoutOption,closeOption,aboutOption;

    public TimeTableViewController() {

    }

    @FXML
    public void initialize(URL url, ResourceBundle rb){
        today = (GregorianCalendar) Calendar.getInstance();
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

    public void populateTimeTable(TimeTable timeTable){

        ArrayList<TimeTableSlot> slots = new ArrayList<TimeTableSlot>(Arrays.asList(timeTable.getSlotIds()));
        ArrayList<Label> slotLabel = new ArrayList<Label>();
        String content;

        for(ListIterator<TimeTableSlot> iterator = slots.listIterator(); iterator.hasNext();){
            TimeTableSlot slot = iterator.next();
            content = String.format("%s %s\n%s\n%s",slot.getSubject().getIdSubject(),slot.getSlotType(),slot.getStudentList(),slot.getRoom());
            Label label = new Label();
            EventHandler<MouseEvent> middle = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String style = label.getStyle();
                    if(style.contains("-fx-background-color"))
                        style = style.replaceFirst("-fx-background-color: #[a-f,0-9]{6}","-fx-background-color: #e0e0e0");
                    else
                        style = style.concat("-fx-background-color: #e0e0e0");
                    label.setStyle(style);
                }
            };
            label.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String style = label.getStyle();
                    if(style.contains("-fx-background-color"))
                        style = style.replaceFirst("-fx-background-color: #[a-f,0-9]{6}","-fx-background-color: #c3c3c3");
                    else
                        style = style.concat("-fx-background-color: #c3c3c3");
                    label.setStyle(style);
                }
            });
            label.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String style = label.getStyle();
                    if(style.contains("-fx-background-color"))
                        style = style.replaceFirst("-fx-background-color: #[a-f,0-9]{6}","-fx-background-color: #f4f4f4");
                    else
                        style = style.concat("-fx-background-color: #f4f4f4");
                    label.setStyle(style);
                }
            });
            label.setUserData(slot);
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Label clicked = (Label)event.getSource();
                    TimeTableSlot clickedSlot = (TimeTableSlot) clicked.getUserData();
                    today = (GregorianCalendar) Calendar.getInstance();
                    GregorianCalendar selectedDay = (GregorianCalendar) Calendar.getInstance();
                    switch (clickedSlot.getDayOfWeek())
                    {
                        case "Monday" : selectedDay.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
                            break;
                        case "Tuesday" : selectedDay.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
                            break;
                        case "Wednesday" : selectedDay.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
                            break;
                        case "Thursday" : selectedDay.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
                            break;
                        case "Friday" : selectedDay.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
                            break;
                        case "Saturday" : selectedDay.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
                            break;
                    }
                    selectedDay.set(Calendar.HOUR_OF_DAY,Integer.parseInt(clickedSlot.getTime().substring(0,2)));
                    manager.showSlot(clickedSlot,selectedDay.after(today));
                }
            });
            label.setOnMouseEntered(middle);
            label.setOnMouseReleased(middle);
            label.setAlignment(Pos.CENTER);
            label.setTextAlignment(TextAlignment.CENTER);
            label.setText(content);
            HBox.setHgrow(label,Priority.ALWAYS);
            label.setMaxSize(MAX_VALUE,MAX_VALUE);
            slotLabel.add(label);
        }

        for(ListIterator<TimeTableSlot> iterator = slots.listIterator(); iterator.hasNext();){
            int labelIndex = iterator.nextIndex();
            TimeTableSlot slot = iterator.next();
            int slotIndex = getPosition(slot);
            if(slot.getSlotType().equals("Lab")) {
                SlotSpace.get(slotIndex).getChildren().add(slotLabel.get(labelIndex));
            } else if(SlotSpace.get(slotIndex).getChildren().size() == 0){
                Label filler = new Label("                       ");
                HBox.setHgrow(filler, Priority.ALWAYS);
                filler.setMaxSize(MAX_VALUE, MAX_VALUE);
                switch (slot.getTime()) {
                    case "09:00:00":
                    case "11:15:00":
                    case "14:00:00":
                    case "16:15:00":
                        filler.setStyle("-fx-border-width: 2;-fx-border-style: hidden hidden hidden solid;-fx-border-color: #898989;");
                        SlotSpace.get(slotIndex).getChildren().addAll(slotLabel.get(labelIndex), filler);
                        break;
                    case "09:50:00":
                    case "12:15:00":
                    case "15:00:00":
                    case "17:15:00":
                        filler.setStyle("-fx-border-width: 2;-fx-border-style: hidden solid hidden hidden;-fx-border-color: #898989;");
                        SlotSpace.get(slotIndex).getChildren().addAll(filler, slotLabel.get(labelIndex));
                        break;
                }
            } else if(SlotSpace.get(slotIndex).getChildren().size() == 2){
                Label first = (Label) SlotSpace.get(slotIndex).getChildren().get(0);
                if(first.getText() != "                       ") {
                    first.setStyle("-fx-border-width: 2;-fx-border-style: hidden solid hidden hidden;-fx-border-color: #898989;");
                    SlotSpace.get(slotIndex).getChildren().clear();
                    SlotSpace.get(slotIndex).getChildren().addAll(first, slotLabel.get(labelIndex));
                } else{
                    Label second = (Label) SlotSpace.get(slotIndex).getChildren().get(1);
                    second.setStyle("-fx-border-width: 2;-fx-border-style: hidden hidden hidden solid;-fx-border-color: #898989;");
                    SlotSpace.get(slotIndex).getChildren().clear();
                    SlotSpace.get(slotIndex).getChildren().addAll(slotLabel.get(labelIndex),second);
                }
            }
        }
    }

   private int getPosition(TimeTableSlot slot){
       String time = slot.getTime();
       String day = slot.getDayOfWeek();
       switch (day){
           case "Monday" :  return TimeTableConstants.MONDAY + getTimeRange(time);
           case "Tuesday" : return TimeTableConstants.TUESDAY + getTimeRange(time);
           case "Wednesday" : return TimeTableConstants.WEDNESDAY + getTimeRange(time);
           case "Thursday" : return TimeTableConstants.THUSRDAY + getTimeRange(time);
           case "Friday" : return TimeTableConstants.FRIDAY + getTimeRange(time);
           case "Saturday" :return TimeTableConstants.SATURDAY + getTimeRange(time);
       }
       return -1;
   }

   private int getTimeRange(String time){
        switch (time){
            case "09:00:00" :
            case "09:50:00" : return TimeTableConstants.NINE;
            case "11:15:00" :
            case "12:15:00" : return TimeTableConstants.ELEVEN;
            case "14:00:00" :
            case "15:00:00" : return TimeTableConstants.FOURTEEN;
            case "16:15:00" :
            case "17:15:00" : return TimeTableConstants.SIXTEEN;
        }
        return -1;
   }

   public void closeApplication(){
        manager.getDb().close();
        Platform.exit();
   }

   public void logout() throws IOException {
        manager.logoutPage();
   }

   public void showAboutPage() throws IOException{
        manager.aboutPage();
   }



   public void setManager(HandyManager manager) {
        this.manager = manager;
    }
}