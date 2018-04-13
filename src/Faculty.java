import java.sql.ResultSet;
import java.sql.SQLException;

public class Faculty {
    private String idFaculty;
    private String password;
    private Subject subjects[];
    private TimeTable timeTable;

    public Faculty(String idFaculty, String password, DatabaseConnection db) {
        try {
            ResultSet rs = db.queryDatabase(String.format("select * from faculty where idFaculty = '%s' AND password = '%s'", idFaculty, password));
            if(rs.next()){
                setIdFaculty(rs.getString("idFaculty"));
                setPassword(rs.getString("password"));
                String subjects[] = rs.getString("subjects").split(";");
                Subject subs[] = new Subject[subjects.length];
                int i = 0;
                for(String Sub : subjects){
                    subs[i] = new Subject(Sub,db);
                    i++;
                }
                setSubjects(subs);
                setTimeTable(new TimeTable(getIdFaculty(),db));
            }
            else {
                setIdFaculty("NULL");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Faculty(TimeTable timeTable) {
        this.timeTable = timeTable;
        setIdFaculty("NULL");
        setPassword("NULL");
    }

    public String getIdFaculty() {
        return idFaculty;
    }

    public void setIdFaculty(String idFaculty) {
        this.idFaculty = idFaculty;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Subject[] getSubjects() {
        return subjects;
    }

    public void setSubjects(Subject[] subjects) {
        this.subjects = subjects;
    }

    public TimeTable getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }
}
