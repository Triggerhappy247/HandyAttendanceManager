import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Faculty {
    public static void main(String args[]){
        String user,pass;
        Scanner scanner = new Scanner(System.in);
        user = scanner.nextLine();
        pass = scanner.nextLine();
        DatabaseConnection db = new DatabaseConnection();
        try {
            db.connect("jdbc:mysql://localhost:3310/attendance_manager?useSSL=false");
            ResultSet rs = db.queryDatabase(String.format("select * from faculty where idFaculty = '%s' AND password = '%s'", user, pass));
            if(rs.next()) {
                System.out.println(rs.getString(1) + "  " + rs.getString(2));
                String Subs[] = rs.getString(3).split(";");
                Subject Subjects[] = new Subject[Subs.length];
                for (int i = 0; i < Subs.length;i++) {
                    Subjects[i] = new Subject(Subs[i],db);
                    System.out.println(Subjects[i].getIdSubject());
                }
                rs = db.queryDatabase(String.format("select * from timetable where idTimetable='%s';", user));
                if(rs.next()) {
                    System.out.println(rs.getString(1));
                    String Slots[] = rs.getString(2).split(";");
                    for (String Slot : Slots) {
                        System.out.println(Slot);
                    }
                }
            }db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
