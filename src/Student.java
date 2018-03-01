import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
    private String idStudent;
    private Attendance attendance;
    private float labAttendance,lectureAttendance,tutorialAttendance;
    private Date lastUpdate;

    public Student(String idStudent, DatabaseConnection db) {
        try {
            ResultSet rs = db.queryDatabase(String.format("select * from student where idStudent = '%s'",idStudent));
            if (rs.next()){
                setIdStudent(rs.getString("idStudent"));
                setLectureAttendance(rs.getFloat("lectureAttendance"));
                setLabAttendance(rs.getFloat("labAttendance"));
                setTutorialAttendance(rs.getFloat("tutorialAttendance"));
                setLastUpdate(rs.getDate("lastUpdate"));
                setAttendance(new Attendance(getIdStudent(),db));
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

    public float getLabAttendance() {
        return labAttendance;
    }

    public void setLabAttendance(float labAttendance) {
        this.labAttendance = labAttendance;
    }

    public float getLectureAttendance() {
        return lectureAttendance;
    }

    public void setLectureAttendance(float lectureAttendance) {
        this.lectureAttendance = lectureAttendance;
    }

    public float getTutorialAttendance() {
        return tutorialAttendance;
    }

    public void setTutorialAttendance(float tutorialAttendance) {
        this.tutorialAttendance = tutorialAttendance;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
