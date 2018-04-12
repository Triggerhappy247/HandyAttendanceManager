import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class StudentAttendanceTable {
    private SimpleStringProperty studentID;
    private RadioButton present;
    private RadioButton absent;
    private ToggleGroup toggleGroup;

    public StudentAttendanceTable(String studentID) {
        this.studentID = new SimpleStringProperty(studentID);
        toggleGroup = new ToggleGroup();
        present = new RadioButton("Present");
        absent = new RadioButton("Absent");
        present.setToggleGroup(toggleGroup);
        absent.setToggleGroup(toggleGroup);
        present.setSelected(true);
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

    @Override
    public boolean equals(Object obj) {
        StudentAttendanceTable temp = (StudentAttendanceTable) obj;
        return this.getStudentID().equalsIgnoreCase(temp.getStudentID());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
