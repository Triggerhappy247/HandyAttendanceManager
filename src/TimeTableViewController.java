import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Double.MAX_VALUE;

public class TimeTableViewController implements Initializable{

    @FXML
    private Label mondayDate,tuesdayDate,wednesdayDate,thursdayDate,fridayDate,saturdayDate,test;

    @FXML
    private ArrayList<HBox> SlotSpace;

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
        ArrayList<TimeTableSlot> slots = new ArrayList<TimeTableSlot>(Arrays.asList(faculty.getTimeTable().getSlotIds()));
        ArrayList<Label> slotLabel = new ArrayList<Label>();
        String content;

        for(ListIterator<TimeTableSlot> iterator = slots.listIterator(); iterator.hasNext();){
            TimeTableSlot slot = iterator.next();
            content = String.format("%s %s\n%s\n%s",slot.getSubject().getIdSubject(),slot.getSlotType(),slot.getStudentList(),slot.getRoom());
            Label label = new Label();
            EventHandler<MouseEvent> middle = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String borderStyle = label.getStyle();
                    if(borderStyle == "-fx-border-width: 2;-fx-border-style: hidden hidden hidden solid" || borderStyle == "-fx-border-width: 2;-fx-border-style: hidden solid hidden hidden")
                        label.setStyle(borderStyle + ";-fx-background-color: #e0e0e0");
                    else
                        label.setStyle("-fx-background-color: #e0e0e0");
                }
            };
            label.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String borderStyle = label.getStyle();
                    if(borderStyle == "-fx-border-width: 2;-fx-border-style: hidden hidden hidden solid" || borderStyle == "-fx-border-width: 2;-fx-border-style: hidden solid hidden hidden")
                        label.setStyle(borderStyle + ";-fx-background-color: #c3c3c3");
                    else
                        label.setStyle("-fx-background-color: #c3c3c3");
                }
            });
            label.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String borderStyle = label.getStyle();
                    if(borderStyle == "-fx-border-width: 2;-fx-border-style: hidden hidden hidden solid")
                        label.setStyle("-fx-border-width: 2;-fx-border-style: hidden hidden hidden solid;-fx-background-color: #f4f4f4");
                    else
                        label.setStyle("-fx-background-color: #f4f4f4");
                }
            });
            label.setUserData(slot);
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Label clicked = (Label)event.getSource();
                    TimeTableSlot clickedSlot = (TimeTableSlot) clicked.getUserData();
                    System.out.println(clickedSlot.getIdTimeTableSlot());
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
                        filler.setStyle("-fx-border-width: 2;-fx-border-style: hidden hidden hidden solid");
                        SlotSpace.get(slotIndex).getChildren().addAll(slotLabel.get(labelIndex), filler);
                        break;
                    case "09:50:00":
                    case "12:15:00":
                    case "15:00:00":
                    case "17:15:00":
                        filler.setStyle("-fx-border-width: 2;-fx-border-style: hidden solid hidden hidden");
                        SlotSpace.get(slotIndex).getChildren().addAll(filler, slotLabel.get(labelIndex));
                        break;
                }
            } else if(SlotSpace.get(slotIndex).getChildren().size() == 2){
                Label first = (Label) SlotSpace.get(slotIndex).getChildren().get(0);
                if(first.getText() != "                       ") {
                    first.setStyle("-fx-border-width: 2;-fx-border-style: hidden solid hidden hidden;");
                    SlotSpace.get(slotIndex).getChildren().clear();
                    SlotSpace.get(slotIndex).getChildren().addAll(first, slotLabel.get(labelIndex));
                } else{
                    Label second = (Label) SlotSpace.get(slotIndex).getChildren().get(1);
                    second.setStyle("-fx-border-width: 2;-fx-border-style: hidden hidden hidden solid;");
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


    @FXML
    private void hoverStart(){
        test.setStyle("-fx-background-color: #e0e0e0");
    }

    @FXML
    private void hoverEnd(){
        test.setStyle("-fx-background-color: #f4f4f4");
    }
}
