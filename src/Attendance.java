import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ListIterator;

public class Attendance {

    private ArrayList<TimeTableSlot> timeSlots;
    private ArrayList<LocalDate> allDates;
    private ArrayList<LocalDate> absentDates;

    public Attendance(String studentID,TimeTableSlot timeTableSlot,TimeTable timeTable,DatabaseConnection db) {
        try {
            String multipleSlots = String.format("\'%s\'",timeTableSlot.getIdTimeTableSlot());
            setTimeSlots(new ArrayList<>());
            setAllDates(new ArrayList<>());
            setAbsentDates(new ArrayList<>());
            getTimeSlots().add(timeTableSlot);

            //If the slot is a lecture, get the other lectures

            if(timeTableSlot.getSlotType().equalsIgnoreCase("Lecture"))
            {
                TimeTableSlot slots[] = timeTable.getSlotIds();
                for(TimeTableSlot slot : slots){
                    if(timeTableSlot.isSameLecture(slot)){
                        getTimeSlots().add(slot);
                        multipleSlots = String.format("%s or \'%s\'",multipleSlots,slot.getIdTimeTableSlot());
                    }
                }
            }

            //Get all dates of this particular slot beginning from the start of the semester
            LocalDate datePicker = LocalDate.now();
            LocalDate today = LocalDate.now();
            String holidays[];
            ArrayList<LocalDate> holidayDates = new ArrayList<>();
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

            rs = db.queryDatabase(String.format("select * from student_attendance_absent where timeTableSlot = %s and idStudent = \'%s\'",multipleSlots,studentID));
            while (rs.next()){
                absentDates.add(rs.getDate("date").toLocalDate());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<TimeTableSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(ArrayList<TimeTableSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public ArrayList<LocalDate> getAllDates() {
        return allDates;
    }

    public void setAllDates(ArrayList<LocalDate> allDates) {
        this.allDates = allDates;
    }

    public ArrayList<LocalDate> getAbsentDates() {
        return absentDates;
    }

    public void setAbsentDates(ArrayList<LocalDate> absentDates) {
        this.absentDates = absentDates;
    }
}
