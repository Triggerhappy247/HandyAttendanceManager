import java.sql.*;

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

    public void addBatch(String SQL) throws SQLException {
        stmt.addBatch(SQL);
    }

    public void batchUpdate() throws SQLException {
        stmt.executeBatch();
        stmt.clearBatch();
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

    public Statement getStmt() {
        return stmt;
    }
}
