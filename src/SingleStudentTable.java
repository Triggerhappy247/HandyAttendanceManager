import javafx.beans.property.SimpleStringProperty;

public class SingleStudentTable {
    private SimpleStringProperty date;
    private SimpleStringProperty attendanceValue;

    public SingleStudentTable(String date, String attendanceValue) {
        this.date = new SimpleStringProperty(date);
        this.attendanceValue = new SimpleStringProperty(attendanceValue);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getAttendanceValue() {
        return attendanceValue.get();
    }

    public SimpleStringProperty attendanceValueProperty() {
        return attendanceValue;
    }

    public void setAttendanceValue(String attendanceValue) {
        this.attendanceValue.set(attendanceValue);
    }

    @Override
    public String toString() {
        return String.format("%s - %s",getDate(),getAttendanceValue());
    }
}
