package ru.com.avs.util.DB;

import fr.roland.DB.Executor;

import java.sql.SQLException;

public class DataBaseHelper {
    public Executor executor;
    public DataBaseHelper(String databaseToConnect, String user, String pass) throws SQLException {
        String url = "jdbc:postgresql://localhost/"+ databaseToConnect+"?user="+user+"&password="+pass;
        this.executor = new Executor(url);
    }
    public DataBaseHelper(String databaseToConnect) throws SQLException {
        String url = "jdbc:postgresql://localhost/"+databaseToConnect+"?user=user2&password=123";
        this.executor = new Executor(url);
    }
    public DataBaseHelper(String databaseToConnect,  boolean full) throws SQLException {
        this.executor = new Executor(databaseToConnect);
    }
    public DataBaseHelper() throws SQLException {
        String url = "jdbc:postgresql://localhost/users?user=user2&password=123";///&ssl=true";
        this.executor = new Executor(url);
    };


}
