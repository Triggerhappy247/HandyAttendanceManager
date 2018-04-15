import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ListIterator;

public class Attendance {

    public static ArrayList<LocalDate> getAllDates(TimeTableSlot timeTableSlot,TimeTable timeTable,DatabaseConnection db){

        ArrayList<LocalDate> allDates = new ArrayList<>();
        ArrayList<TimeTableSlot> timeSlots = new ArrayList<TimeTableSlot>();
        timeSlots.add(timeTableSlot);
        if(timeTableSlot.getSlotType().equalsIgnoreCase("Lecture"))
        {
            TimeTableSlot slots[] = timeTable.getSlotIds();
            for(TimeTableSlot slot : slots){
                if(timeTableSlot.isSameLecture(slot)){
                    timeSlots.add(slot);
                }
            }
        }
        //Get all dates of this particular slot beginning from the start of the semester
        try {
            LocalDate datePicker;
            LocalDate today;
            ArrayList<LocalDate> holidayDates;
            datePicker = LocalDate.now();
            today = LocalDate.now();
            String holidays[];
            holidayDates = new ArrayList<>();
            ResultSet rs = db.queryDatabase(String.format("SELECT * FROM attendance_manager.semester where startingDate < '%s' and endingDate > '%s'",today,today));
            if(rs.next()){

                datePicker = rs.getDate("startingDate").toLocalDate();
                holidays = rs.getString("holidays").split(";");
                LocalDate temp;
                for (String holiday : holidays){
                    temp = LocalDate.parse(holiday);
                    holidayDates.add(temp);
                }
            }
            ArrayList<DayOfWeek> days = new ArrayList<>();
            for(ListIterator<TimeTableSlot> slotListIterator = timeSlots.listIterator();slotListIterator.hasNext();){
                TimeTableSlot slot = slotListIterator.next();
                switch (slot.getDayOfWeek()) {
                    case "Monday":
                        days.add(DayOfWeek.MONDAY);
                        break;
                    case "Tuesday":
                        days.add(DayOfWeek.TUESDAY);
                        break;
                    case "Wednesday":
                        days.add(DayOfWeek.WEDNESDAY);
                        break;
                    case "Thursday":
                        days.add(DayOfWeek.THURSDAY);
                        break;
                    case "Friday":
                        days.add(DayOfWeek.FRIDAY);
                        break;
                    case "Saturday":
                        days.add(DayOfWeek.SATURDAY);
                        break;
                }
            }

            for(;datePicker.isBefore(today);datePicker = datePicker.plusDays(1)){
                if(days.contains(datePicker.getDayOfWeek()) && !holidayDates.contains(datePicker))
                    allDates.add(datePicker);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allDates;
    }

    public static ArrayList<LocalDate> getAbsentDates(String studentID,String multipleSlots,DatabaseConnection db){
        ArrayList<LocalDate> absentDates = new ArrayList<>();
        try {
            ResultSet rs = db.queryDatabase(String.format("select * from student_attendance_absent where timeTableSlot = %s and idStudent = \'%s\'",multipleSlots,studentID));
            while (rs.next()){
                    absentDates.add(rs.getDate("date").toLocalDate());
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return absentDates;
    }

    public static ArrayList<LocalDate> getFreezeDates(DatabaseConnection db){

        ArrayList<LocalDate> frozenDates = new ArrayList<>();

        try {
            LocalDate today = LocalDate.now();
            String frozen[];
            ResultSet rs = db.queryDatabase(String.format("SELECT * FROM attendance_manager.semester where startingDate < '%s' and endingDate > '%s'",today,today));
            if(rs.next()){
                frozen = rs.getString("reviewDates").split(";");
                LocalDate temp;
                for (String holiday : frozen){
                    temp = LocalDate.parse(holiday);
                    frozenDates.add(temp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return frozenDates;
    }

}
