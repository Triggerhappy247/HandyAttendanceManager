import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Attendance {

    Subject subject;

    ArrayList<Calendar> dates;

    public Attendance(String idStudent, String subjectID,DatabaseConnection db) {
        try {
            ResultSet rs = db.queryDatabase(String.format("select * from %s where idStudent = '%s'",subjectID.toLowerCase(),idStudent));
            if (rs.next()){
                setSubject(new Subject(subjectID,db));
            }
            Calendar today = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            rs = db.queryDatabase(String.format("SELECT * FROM attendance_manager.semester where startingDate < '%s' and endingDate > '%s'",format.format(today.getTime()),format.format(today.getTime())));
            if(rs.next()){
                today.setTime(rs.getDate("startingDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public ArrayList<Calendar> getDates() {
        return dates;
    }

    public void setDates(ArrayList<Calendar> dates) {
        this.dates = dates;
    }
}
