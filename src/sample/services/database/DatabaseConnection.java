package sample.services.database;
import java.sql.*;
public class DatabaseConnection implements DBservice {

    public static Connection connection;
    public static Statement statement;

    private void ConnectionSQLite() {
        Connection connection = getConnection();
        System.out.println("Connection");
    }
    private Connection getConnection() {
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:D:\\Java\\StoreDbJavaFX\\Store.DB");
//            System.out.println("Connection");
            return connection;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    private void close() {
        try{
            connection.close();
            System.out.println("Close connection");
        }catch(Exception e)
        {
            System.out.print(e.getMessage());
        }
    }
    @Override
    public void databaseConnection() {
        ConnectionSQLite();
    }

    @Override
    public void databaseClose() {
        close();
    }
}
