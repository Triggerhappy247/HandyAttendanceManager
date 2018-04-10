import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
    private String idStudent,studentName;
    private Attendance attendance;
    private Date lastUpdate;

    public Student(String idStudent,DatabaseConnection db) {
        try {
            ResultSet rs = db.queryDatabase(String.format("select * from student where idStudent = '%s'",idStudent));
            if (rs.next()){
                setIdStudent(rs.getString("idStudent"));
                setStudentName(rs.getString("studentName"));
                setLastUpdate(rs.getDate("lastUpdate"));
                //setAttendance(new Attendance(getIdStudent(), db));
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

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
