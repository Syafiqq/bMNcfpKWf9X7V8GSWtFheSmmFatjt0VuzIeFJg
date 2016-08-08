package model.database.test;

import java.sql.DriverManager;
import java.sql.SQLException;
import main.Main;
import model.database.DBComponent;
import model.database.DBProperties;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.database.test> created by :
 * Name         : syafiq
 * Date / Time  : 05 May 2016, 8:27 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DBPropertiesTest
{
    @Test public void TestConnectionWay_001()
    {
        Main.getMyDatabaseAccount();
        DBComponent  dbs        = new DBComponent();
        DBProperties properties = DBProperties.getInstance();
        String       url        = "jdbc:mysql://" + properties.host + ":" + properties.port + "/" + properties.database + "?user=" + properties.username + "&password=" + properties.password + "&useSSL=false";
        try
        {
            dbs.connection = DriverManager.getConnection(url);
        }
        catch(SQLException ignored)
        {
        }
    }
}