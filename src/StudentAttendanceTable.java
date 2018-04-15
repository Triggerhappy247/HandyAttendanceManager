import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class StudentAttendanceTable {
    private SimpleStringProperty studentID;
    private RadioButton present;
    private RadioButton absent;
    private ToggleGroup toggleGroup;
    private MarkAttendanceController updater;

    public StudentAttendanceTable(String studentID,MarkAttendanceController updater) {
        setUpdater(updater);
        this.studentID = new SimpleStringProperty(studentID);
        toggleGroup = new ToggleGroup();
        present = new RadioButton("Present");
        absent = new RadioButton("Absent");
        present.setToggleGroup(toggleGroup);
        absent.setToggleGroup(toggleGroup);
        present.setSelected(true);

        present.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                updater.deleteRecord(getStudentID());
            }
        });

        absent.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                updater.saveRecord(getStudentID());
            }
        });
    }

    public StudentAttendanceTable(String studentID) {
        this.studentID = new SimpleStringProperty(studentID);
    }

    public String getStudentID() {
        return studentID.get();
    }

    public SimpleStringProperty studentIDProperty() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID.set(studentID);
    }

    public RadioButton getPresent() {
        return present;
    }

    public void setPresent(RadioButton present) {
        this.present = present;
    }

    public RadioButton getAbsent() {
        return absent;
    }

    public void setAbsent(RadioButton absent) {
        this.absent = absent;
    }

    public ToggleGroup getToggleGroup() {
        return toggleGroup;
    }

    public void setUpdater(MarkAttendanceController updater) {
        this.updater = updater;
    }

    @Override
    public boolean equals(Object obj) {
        StudentAttendanceTable temp = (StudentAttendanceTable) obj;
        return this.getStudentID().equalsIgnoreCase(temp.getStudentID());
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return getStudentID();
    }
}
