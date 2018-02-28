import java.sql.ResultSet;
import java.sql.SQLException;

public class TimeTable {
    private String idTimetable;
    private TimeTableSlot slotIds[];

    public TimeTable(String idTimetable, DatabaseConnection db) {
        try {
            ResultSet rs = db.queryDatabase(String.format("select * from timetable where idTimetable = '%s'", idTimetable));
            if(rs.next()){
                setIdTimetable(rs.getString("idTimetable"));
                String slots[] = rs.getString("slotIds").split(";");
                TimeTableSlot timeTableSlots[] = new TimeTableSlot[slots.length];
                int i = 0;
                for(String  slot: slots){
                    timeTableSlots[i] = new TimeTableSlot(slot,db);
                    i++;
                }
                setSlotIds(timeTableSlots);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getIdTimetable() {
        return idTimetable;
    }

    public void setIdTimetable(String idTimetable) {
        this.idTimetable = idTimetable;
    }

    public TimeTableSlot[] getSlotIds() {
        return slotIds;
    }

    public void setSlotIds(TimeTableSlot[] slotIds) {
        this.slotIds = slotIds;
    }
}
