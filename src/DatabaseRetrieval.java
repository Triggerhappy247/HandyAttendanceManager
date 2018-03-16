import java.util.ListIterator;
import java.util.Scanner;

public class DatabaseRetrieval {
    public static void main(String args[]){
        String user,pass;
        Scanner scanner = new Scanner(System.in);
        user = scanner.nextLine();
        pass = scanner.nextLine();
        DatabaseConnection db = new DatabaseConnection();
        db.connect("jdbc:mysql://localhost:3310/attendance_manager?useSSL=false");
        Faculty faculty = new Faculty(user,pass,db);
        System.out.println("Id = " + faculty.getIdFaculty());
        System.out.println("Pass = " + faculty.getPassword());
        System.out.println("Subjects");
        for (Subject subject : faculty.getSubjects()){
            System.out.println(subject.getIdSubject() + " " + subject.getSubName());
        }

        System.out.println("Time Slots");
        for(TimeTableSlot slots : faculty.getTimeTable().getSlotIds()){
            System.out.println(slots.getIdTimeTableSlot() + " " + slots.getSubject().getSubName()+ " " + slots.getDayOfWeek() + " " + slots.getTime().toString() + " " + slots.getRoom() + " " + slots.getStudentList() + " " + slots.getSlotType());

        }

        System.out.println("Students");
        StudentList studentList = new StudentList(faculty.getTimeTable().getSlotIds()[5].getStudentList(),faculty.getTimeTable().getSlotIds()[5].getSubject().getIdSubject(),db);
        for(ListIterator<Student> iterator = studentList.getStudent().listIterator();iterator.hasNext();){
            Student i = iterator.next();
            System.out.println(i.getIdStudent());
        }
        db.close();
    }
}
