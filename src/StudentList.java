import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentList {
    private int numberOfStudents;
    private String listId;
    private Student student[];
    public StudentList(String listId, DatabaseConnection db) {
        this.listId=listId;
        ResultSet rs = null;
        try {
            rs = db.queryDatabase(String.format("select * from studentList where listId = '%s'", listId));
            if(rs.next()){
                setNumberOfStudents(rs.getInt("numberOfStudents"));
                setListId(rs.getString("listId"));
               // setStudent(rs.getString("studentRange"));
                String Student[] = rs.getString("studentRange").split(":");
                Student student[] = new student[numberOfStudents];
                int i = 0;
                for(i=0;i<2;i++){
                    student[i] = new student(,db);
                    i++;
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

    public Student[] getStudent() {
        return student;
    }

    public void setStudent(Student[] student) {
        this.student = student;
    }
}
