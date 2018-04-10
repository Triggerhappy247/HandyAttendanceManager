import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {

    private Statement stmt;
    private Connection con;

    public void connect(String URL)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    URL,"root","123456");
            stmt = con.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet queryDatabase(String SQL) throws SQLException {
        return stmt.executeQuery(SQL);
    }

    public int updateDatabase(String SQL) throws SQLException {
        return stmt.executeUpdate(SQL);
    }

    public int[] batchUpdate(String SQL[]) throws SQLException {
        for (String sql : SQL){
            stmt.addBatch(sql);
        }

        return stmt.executeBatch();
    }

    public void close()
    {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getCon() {
        return con;
    }
}
