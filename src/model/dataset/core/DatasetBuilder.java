package model.dataset.core;

import java.sql.DriverManager;
import java.sql.SQLException;
import model.database.DBComponent;
import model.database.DBProperties;
import model.dataset.component.Lesson;
import model.dataset.component.Timeoff;

/**
 * This <Skripsi_003> project in package <model.data> created by :
 * Name         : syafiq
 * Date / Time  : 05 May 2016, 4:30 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public abstract class DatasetBuilder<TDataset extends Dataset<? extends Timeoff, ? extends Lesson>, TDatasetConverter extends DatasetConverter<?>>
{
    /**
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

    protected final TDataset          dataset;
    protected final TDatasetConverter encoder;
    protected final TDatasetConverter decoder;
    protected final DBComponent       db_component;


    public DatasetBuilder(TDataset dataset, TDatasetConverter encoder, TDatasetConverter decoder)
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
            assert this.db_component.connection != null;
            this.db_component.connection.close();
            this.db_component.connection = null;
        }
        catch(SQLException ignored)
        {
        }
        try
        {
            assert this.db_component.statement != null;
            this.db_component.statement.close();
            this.db_component.statement = null;
        }
        catch(SQLException ignored)
        {
        }
        try
        {
            assert this.db_component.result_set != null;
            this.db_component.result_set.close();
            this.db_component.result_set = null;
        }
        catch(SQLException ignored)
        {
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

    public TDataset getDataset()
    {
        return this.dataset;
    }

    public TDatasetConverter getEncoder()
    {
        return this.encoder;
    }

    public TDatasetConverter getDecoder()
    {
        return this.decoder;
    }
}
