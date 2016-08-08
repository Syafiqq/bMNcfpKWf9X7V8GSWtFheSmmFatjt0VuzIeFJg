package model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * This <Skripsi_003> project in package <model.database> created by :
 * Name         : syafiq
 * Date / Time  : 05 May 2016, 6:35 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DBComponent
{
    /*
     * Nomenclature
     * <p>
     * Global Important Variable :
     * connection = Connection Helper to database
     * statement = Query Statement Container
     * result_set = Query's Result Set
     */
    public Connection        connection;
    public PreparedStatement statement;
    public ResultSet         result_set;

    public DBComponent()
    {
        this.connection = null;
        this.statement = null;
        this.result_set = null;
    }
}
