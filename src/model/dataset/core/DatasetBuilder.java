package model.dataset.core;

import java.sql.DriverManager;
import java.sql.SQLException;
import model.database.DBComponent;
import model.database.DBProperties;
import model.dataset.component.Lesson;
import model.dataset.component.Timeoff;

/*
 * This <Skripsi_003> project in package <model.data> created by :
 * Name         : syafiq
 * Date / Time  : 05 May 2016, 4:30 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public abstract class DatasetBuilder<Dataset extends model.dataset.core.Dataset<? extends Timeoff, ? extends Lesson>, DatasetConverter extends model.dataset.core.DatasetConverter<?>>
{
    /*
     * Nomenclature :
     * <p>
     * SubType :
     * Tof = Time-off
     * Les = Lesson
     * <p>
     * Global Important Variable :
     * dataset = Data-set
     * encoder = Encode Database Index to Array Index {Database Index -> Array Index}
     * decoder = Decode Database Index to Array Index {Array Index    -> Database Index}
     */

    protected final Dataset          dataset;
    protected final DatasetConverter encoder;
    protected final DatasetConverter decoder;
    protected final DBComponent      db_component;


    public DatasetBuilder(Dataset dataset, DatasetConverter encoder, DatasetConverter decoder)
    {
        this.dataset = dataset;
        this.encoder = encoder;
        this.decoder = decoder;
        this.db_component = new DBComponent();
    }

    public void generateDataset()
    {
        Runtime runtime = Runtime.getRuntime();
        runtime.runFinalization();
        runtime.gc();
        this.activateDatabase();
        this.generateActiveDays();
        this.generateActivePeriods();
        this.generateClasses();
        this.generateClassrooms();
        this.generateLecturers();
        this.generateSubjects();
        this.generateLessons();
        this.deactivateDatabase();
        runtime.runFinalization();
        runtime.gc();
    }


    protected void deactivateDatabase()
    {
        try
        {
            this.db_component.connection.close();
        }
        catch(SQLException ignored)
        {
        }
        finally
        {
            this.db_component.connection = null;
        }
        try
        {
            this.db_component.statement.close();
        }
        catch(SQLException ignored)
        {
        }
        finally
        {
            this.db_component.statement = null;
        }
        try
        {
            this.db_component.result_set.close();
        }
        catch(SQLException ignored)
        {
        }
        finally
        {
            this.db_component.result_set = null;
        }
    }

    protected abstract void generateLessons();

    protected abstract void generateSubjects();

    protected abstract void generateLecturers();

    protected abstract void generateClassrooms();

    protected abstract void generateClasses();

    protected abstract void generateActivePeriods();

    protected abstract void generateActiveDays();

    protected void activateDatabase()
    {
        DBProperties properties = DBProperties.getInstance();
        String       url        = "jdbc:mysql://" + properties.host + ":" + properties.port + "/" + properties.database + "?user=" + properties.username + "&password=" + properties.password + "&useSSL=false&serverTimezone=Asia/Jakarta";
        try
        {
            this.db_component.connection = DriverManager.getConnection(url);
        }
        catch(SQLException ignored)
        {
            System.err.println("Activate Database");
            System.exit(-1);
        }
    }

    public Dataset getDataset()
    {
        return this.dataset;
    }

    public DatasetConverter getEncoder()
    {
        return this.encoder;
    }

    public DatasetConverter getDecoder()
    {
        return this.decoder;
    }
}
