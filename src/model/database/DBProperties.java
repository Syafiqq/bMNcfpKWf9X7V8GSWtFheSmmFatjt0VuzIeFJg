package model.database;

/*
 * This <Skripsi_003> project in package <model.database> created by :
 * Name         : syafiq
 * Date / Time  : 05 May 2016, 4:22 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DBProperties
{
    private static DBProperties instance = new DBProperties();
    public String host;
    public int    port;
    public String database;
    public String username;
    public String password;

    private DBProperties()
    {

    }

    public static DBProperties getInstance()
    {
        return instance;
    }
}
