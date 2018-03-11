import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;



public class LoginController {

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

        if(faculty != null)
        {

        }

        db.close();
    }

}
