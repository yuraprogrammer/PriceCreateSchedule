package pricecreator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class JDbConnection {
    private String url;
    public Connection db;
    //private DriverManager dm;
    private String serverName;
    private String dbName;
    //private String port;
    private String user;
    
    private String driver;
    
    public JDbConnection(String driver, String dbName, String serverName, String userName, String password){
        
    
        this.serverName = serverName;
        //port = sysProp.getProperty("Port");
        this.dbName = dbName;
        user = userName;
        
        url = this.serverName+";databaseName="+this.dbName;
        this.driver = driver;
        
        try {
            db = getConnection(this.driver, url, user, password);
            System.out.println(url + " connection successfull!!!");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    
    public Connection getConnection(String driver, String url, String user, String password) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        
        Class.forName(driver).newInstance();
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;        
    }
}
