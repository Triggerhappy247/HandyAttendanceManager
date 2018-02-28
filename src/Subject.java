import java.sql.ResultSet;
import java.sql.SQLException;

public class Subject {
    private String idSubject;
    private String subName;
    private boolean isLecture,isLab,isTutorial;

    public Subject(String idSubject,DatabaseConnection db) {
        try {
            ResultSet rs = db.queryDatabase(String.format("select * from subject where idSubject = '%s'", idSubject));
            if(rs.next()){
                setIdSubject(rs.getString(1));
                setSubName(rs.getString(2));
                setLecture(rs.getBoolean(3));
                setTutorial(rs.getBoolean(4));
                setLab(rs.getBoolean(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public void setLecture(boolean lecture) {
        isLecture = lecture;
    }

    public void setLab(boolean lab) {
        isLab = lab;
    }

    public void setTutorial(boolean tutorial) {
        isTutorial = tutorial;
    }

    public String getIdSubject() {
        return idSubject;
    }

    public String getSubName() {
        return subName;
    }

    public boolean isLecture() {
        return isLecture;
    }

    public boolean isLab() {
        return isLab;
    }

    public boolean isTutorial() {
        return isTutorial;
    }
}
