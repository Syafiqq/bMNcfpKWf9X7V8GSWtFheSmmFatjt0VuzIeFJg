package main;

import model.database.DBProperties;

/*
 * This <Skripsi_003> project in package <PACKAGE_NAME> created by :
 * Name         : syafiq
 * Date / Time  : 05 May 2016, 4:38 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Main
{
    public static void getMyDatabaseAccount()
    {
        DBProperties properties = DBProperties.getInstance();
        properties.host = "localhost";
        properties.port = 3306;
        properties.database = "schedule";
        properties.username = "root";
        properties.password = "Muhammad_Syafiq";
    }
}
