package model.dataset;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import java.sql.SQLException;
import model.database.DBComponent;
import model.dataset.component.Lesson;
import model.dataset.component.Timeoff;
import model.dataset.core.Dataset;
import model.dataset.core.DatasetConverter;

/**
 * This <Skripsi_003> project in package <model.dataset> created by :
 * Name         : syafiq
 * Date / Time  : 08 May 2016, 4:08 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

/**
 * Dataset P2 Generator With Simple Lesson Generator
 */
public class DatasetP2Generator1 extends DatasetP2Generator
{
    public DatasetP2Generator1(Dataset<Timeoff, Lesson> dataset, DatasetConverter<Int2IntLinkedOpenHashMap> encoder, DatasetConverter<Int2IntLinkedOpenHashMap> decoder)
    {
        super(dataset, encoder, decoder);
    }

    @SuppressWarnings("Duplicates") @Override protected void generateLessonAvailableClassroom()
    {
        try
        {
            DBComponent                                db_component = super.db_component;
            Dataset<Timeoff, Lesson>                   dataset      = super.dataset;
            DatasetConverter<Int2IntLinkedOpenHashMap> encoder      = super.encoder;
            /** Query for all lessons in the specific school id according to lessons*/
            String query = "SELECT `lesson`.`id`, `lesson_available_classroom`.`classroom` FROM `lesson` LEFT OUTER JOIN `subject` ON `lesson`.`subject` = `subject`.`id` LEFT OUTER JOIN `lesson_available_classroom` ON `lesson_available_classroom`.`lesson` = `lesson`.`id` WHERE `subject`.`school` = ? ORDER BY `lesson`.`id`, `lesson_available_classroom`.`classroom` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, dataset.school);
            db_component.result_set = db_component.statement.executeQuery();

            int   lesson_index        = 0;
            int   index               = 0;
            int   available_classroom = -1;
            int[] link                = new int[0];
            if(db_component.result_set.next())
            {
                index = db_component.result_set.getInt("id");
                lesson_index = encoder.lessons.get(index);
                link = dataset.lessons[lesson_index].link;
                db_component.result_set.previous();
            }

            while(db_component.result_set.next())
            {
                if(index != db_component.result_set.getInt("id"))
                {
                    available_classroom = -1;
                    index = db_component.result_set.getInt("id");
                    lesson_index = encoder.lessons.get(index);
                    link = dataset.lessons[lesson_index].link;
                }
                int classroom = encoder.classrooms.get(db_component.result_set.getInt("classrooms"));
                dataset.lessons[lesson_index].available_classroom[++available_classroom] = classroom;
                for(int lnk_idx : link)
                {
                    dataset.lessons[lnk_idx].available_classroom[available_classroom] = classroom;
                }
            }
        }
        catch(SQLException ignore)
        {
            System.err.println("Generate Lesson Available Classroom");
            System.exit(1);
        }
    }
}
