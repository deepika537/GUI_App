import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
public class JavaDbConnect {
    public static Connection connection=null;
    
    public static Connection dbConnect(){
    try{
    Class.forName("org.sqlite.JDBC");
    connection=DriverManager.getConnection("jdbc:sqlite://Users//deepika//NetBeansProjects/sqlitedb//FirstStat.sqlite");
    return connection;
    }
    catch(ClassNotFoundException | SQLException e){
    JOptionPane.showMessageDialog(null, e);
    return null;
    }
    }
    
}
