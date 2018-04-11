import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
    private String idStudent,studentName;
    private Attendance attendance;

    public Student(String idStudent,TimeTableSlot timeTableSlot,TimeTable timeTable,DatabaseConnection db) {
        try {
            ResultSet rs = db.queryDatabase(String.format("select * from student where idStudent = '%s'",idStudent));
            if (rs.next()){
                setIdStudent(rs.getString("idStudent"));
                setStudentName(rs.getString("studentName"));
                setAttendance(new Attendance(getIdStudent(),timeTableSlot,timeTable,db));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
