import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentList {
    private int numberOfStudents;
    private String listId;
    private ArrayList<Student> student;
    public StudentList(String listId, DatabaseConnection db) {
        this.listId=listId;
        ResultSet rs;
        try {
            rs = db.queryDatabase(String.format("select * from studentList where idStudentList = '%s'", listId));
            if(rs.next()){
                setNumberOfStudents(rs.getInt("numberOfStudents"));
                setListId(rs.getString("idStudentList"));
                String Student[] = rs.getString("studentRange").split(":");
                String startRoll = Student[0].substring(0,5);
                int startNum = Integer.parseInt(Student[0].substring(5));
                int endNum = Integer.parseInt(Student[1].substring(5));
                student = new ArrayList<>();
                for(int i=startNum;i <= endNum;i++){
                    student.add(new Student(String.format("%s%03d",startRoll,i),db));
                }
                setStudent(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public ArrayList<Student> getStudent() {
        return student;
    }

    public void setStudent(ArrayList<Student> student) {
        this.student = student;
    }
}
