import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable{

    private HandyManager manager;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label incorrectLabel;

    public LoginController() {
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb){
    }

    @FXML
    private void loginFaculty(){
        DatabaseConnection db = new DatabaseConnection();
        manager.setDb(db);
        db.connect("jdbc:mysql://localhost:3310/attendance_manager?useSSL=false");
        manager.setFaculty(new Faculty(username.getText(),password.getText(),db));

        if(manager.getFaculty().getIdFaculty() != "NULL")
        {
            manager.showTimeTable();
        }
        else
        {
            incorrectLabel.setVisible(true);
        }
    }

    public void setManager(HandyManager manager) {
        this.manager = manager;
    }
}
