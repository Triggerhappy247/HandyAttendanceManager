import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;


public class TimeTableSlot {
    private String idTimeTableSlot;
    private Subject subject;
    private String studentList,dayOfWeek,room,slotType;
    private int slotLength;
    private Time time;
    private float averageAttendance;

    public TimeTableSlot(String idTimeTableSlot, DatabaseConnection db) {
        try {
            ResultSet rs = db.queryDatabase(String.format("select * from timetableslot where idTimeTableSlot = '%s'", idTimeTableSlot));
            if(rs.next()){
                setIdTimeTableSlot(rs.getString("idTimeTableSlot"));
                setStudentList(rs.getString("studentList"));
                setSlotLength(rs.getInt("slotLength"));
                setSlotType(rs.getString("slotType"));
                setTime(rs.getTime("time"));
                setAverageAttendance(rs.getFloat("averageAttendance"));
                setDayOfWeek(rs.getString("dayOfWeek"));
                setRoom(rs.getString("room"));
                String subject = rs.getString("subject");
                setSubject(new Subject(subject,db));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getIdTimeTableSlot() {
        return idTimeTableSlot;
    }

    public void setIdTimeTableSlot(String idTimeTableSlot) {
        this.idTimeTableSlot = idTimeTableSlot;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getStudentList() {
        return studentList;
    }

    public void setStudentList(String studentList) {
        this.studentList = studentList;
    }

    public int getSlotLength() {
        return slotLength;
    }

    public void setSlotLength(int slotLength) {
        this.slotLength = slotLength;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public float getAverageAttendance() {
        return averageAttendance;
    }

    public void setAverageAttendance(float averageAttendance) {
        this.averageAttendance = averageAttendance;
    }

    public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }


}
