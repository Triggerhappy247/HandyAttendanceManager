import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
    private String idStudent,studentName;

    public Student(String idStudent,TimeTableSlot timeTableSlot,TimeTable timeTable,DatabaseConnection db) {
        try {
            ResultSet rs = db.queryDatabase(String.format("select * from student where idStudent = '%s'",idStudent));
            if (rs.next()){
                setIdStudent(rs.getString("idStudent"));
                setStudentName(rs.getString("studentName"));
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

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
