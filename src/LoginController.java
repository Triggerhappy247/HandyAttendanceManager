import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;



public class LoginController {

    @FXML
    private TextArea outputArea;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;



    public LoginController() {
    }

    @FXML
    private void initialize(){
    }

    @FXML
    private void loginFaculty(){
        DatabaseConnection db = new DatabaseConnection();
        db.connect("jdbc:mysql://localhost:3310/attendance_manager?useSSL=false");
        Faculty faculty = new Faculty(username.getText(),password.getText(),db);
        StringBuilder output =  new StringBuilder("Id = " + faculty.getIdFaculty());
        output.append("\nPass = " + faculty.getPassword());
        output.append("\nSubjects");
        for (Subject subject : faculty.getSubjects()){
            output.append(subject.getIdSubject() + " " + subject.getSubName() + "\n");
        }

        output.append("Time Slots\n");
        for(TimeTableSlot slots : faculty.getTimeTable().getSlotIds()){
            output.append(slots.getIdTimeTableSlot() + " " + slots.getSubject().getSubName()+ " " + slots.getDayOfWeek() + " " + slots.getTime().toString() + " " + slots.getRoom() + " " + slots.getStudentList() + " " + slots.getSlotType() + "\n");

        }
        outputArea.setText(output.toString());
        db.close();
    }

}
