package model.dataset;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.sql.SQLException;
import model.database.DBComponent;
import model.dataset.component.Lesson;
import model.dataset.component.Timeoff;
import model.dataset.core.Dataset1;
import model.dataset.core.DatasetBuilder;
import model.dataset.core.DatasetConverter;

/**
 * This <Skripsi_003> project in package <model.dataset.test> created by :
 * Name         : syafiq
 * Date / Time  : 08 May 2016, 5:29 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DatasetP2Generator2 extends DatasetBuilder<Dataset1<Timeoff, Lesson>, DatasetConverter<Int2IntLinkedOpenHashMap>>
{

    public DatasetP2Generator2(Dataset1<Timeoff, Lesson> dataset, DatasetConverter<Int2IntLinkedOpenHashMap> encoder, DatasetConverter<Int2IntLinkedOpenHashMap> decoder)
    {
        super(dataset, encoder, decoder);
    }

    @Override protected void generateLessons()
    {
        this.generateLessonSet();
        this.generateLessonAvailableClassroom();
    }

    protected void generateLessonSet()
    {
        try
        {
            DBComponent                                db_component = super.db_component;
            Dataset1<Timeoff, Lesson>                  dataset      = super.dataset;
            DatasetConverter<Int2IntLinkedOpenHashMap> encoder      = super.encoder;
            DatasetConverter<Int2IntLinkedOpenHashMap> decoder      = super.decoder;
            String                                     query        = "SELECT COUNT(`classroom_timeoff`.`id`) AS 'count' FROM `classroom_timeoff` LEFT OUTER JOIN `classroom` ON `classroom_timeoff`.`classroom` = `classroom`.`id` WHERE `classroom_timeoff`.`availability` = 1 AND`classroom`.`school` = ?";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, dataset.school);
            db_component.result_set = db_component.statement.executeQuery();
            db_component.result_set.next();
            int classroom_time_off_size = db_component.result_set.getInt("count");


            /** Query for all lessons in the specific school id*/
            query = "SELECT COUNT(`lesson`.`id`) AS 'count', SUM(`lesson`.`count` - 1) AS 'extra', SUM(`lesson`.`sks` * `count`) AS 'total' FROM `lesson` LEFT OUTER JOIN `subject` ON `lesson`.`subject` = `subject`.`id` WHERE `subject`.`school` = ?";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, dataset.school);
            db_component.result_set = db_component.statement.executeQuery();
            db_component.result_set.next();
            int lesson_size       = db_component.result_set.getInt("count");
            int lesson_extra_size = db_component.result_set.getInt("extra");
            int lesson_capacity   = db_component.result_set.getInt("total");
            int schedule_capacity = dataset.active_days.length * dataset.active_periods.length * dataset.classrooms.length;
            /** Array length = schedule capacity - lessons capacity - num of classrooms when placement is not available + Total Lesson in the database + lessons extra where lessons count more than one  +  num of classrooms when placement is not available*/
            int size                 = schedule_capacity - lesson_capacity - classroom_time_off_size + lesson_size + lesson_extra_size;
            int lesson_extra_counter = lesson_size;


            /** Query for all lessons in the specific school id*/
            query = "SELECT `lesson`.`id`, `lesson`.`subject`, COALESCE(`lesson`.`lecture`, 0) AS 'lecture', `lesson`.`sks`, `lesson`.`count`, `lesson`.`class`, COUNT(`lesson_available_classroom`.`id`) AS 'total' FROM `lesson` LEFT OUTER JOIN `subject` ON `lesson`.`subject` = `subject`.`id` LEFT OUTER JOIN `lesson_available_classroom` ON `lesson_available_classroom`.`lesson` = `lesson`.`id` WHERE `subject`.`school` = ? GROUP BY `lesson`.`id` ORDER BY `lesson`.`id` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, dataset.school);
            db_component.result_set = db_component.statement.executeQuery();

            dataset.lessons = new Lesson[lesson_size + lesson_extra_size + 1];
            dataset.data = new int[size];
            encoder.lessons = new Int2IntLinkedOpenHashMap(lesson_size);
            decoder.lessons = new Int2IntLinkedOpenHashMap(lesson_size);
            IntArrayList link_temporary = new IntArrayList();
            for(int result_set_counter = 0; db_component.result_set.next(); )
            {
                if(db_component.result_set.getInt("count") > 1)
                {
                    link_temporary.clear();

                    int link_size = db_component.result_set.getInt("count") - 1;
                    link_temporary.add(++result_set_counter);

                    Lesson lesson = new Lesson(db_component.result_set.getInt("subject"), db_component.result_set.getInt("lecture"), db_component.result_set.getInt("sks"), db_component.result_set.getInt("class"), link_size, db_component.result_set.getInt("total"));
                    dataset.lessons[result_set_counter] = lesson;
                    dataset.data[result_set_counter - 1] = result_set_counter;
                    for(int link_counter = -1; ++link_counter < link_size; )
                    {
                        lesson = new Lesson(db_component.result_set.getInt("subject"), db_component.result_set.getInt("lecture"), db_component.result_set.getInt("sks"), db_component.result_set.getInt("class"), link_size, db_component.result_set.getInt("total"));
                        dataset.lessons[++lesson_extra_counter] = lesson;
                        dataset.data[lesson_extra_counter - 1] = lesson_extra_counter;
                        link_temporary.add(lesson_extra_counter);
                    }

                    for(int link : link_temporary)
                    {
                        lesson = dataset.lessons[link];
                        for(int link_counter = -1, link_lv1_counter = -1, link_lv1_size = link_temporary.size(); (++link_lv1_counter < link_lv1_size); )
                        {
                            int link_lv1 = link_temporary.getInt(link_lv1_counter);
                            if(link != link_lv1)
                            {
                                lesson.link[++link_counter] = link_lv1;
                            }
                        }
                    }
                }
                else
                {
                    dataset.lessons[++result_set_counter] = new Lesson(db_component.result_set.getInt("subject"), db_component.result_set.getInt("lecture"), db_component.result_set.getInt("sks"), db_component.result_set.getInt("class"), 0, db_component.result_set.getInt("total"));
                    dataset.data[result_set_counter - 1] = result_set_counter;
                }
                encoder.lessons.put(db_component.result_set.getInt("id"), result_set_counter);
                decoder.lessons.put(result_set_counter, db_component.result_set.getInt("id"));
            }
            dataset.lessons[0] = new Lesson(0, 0, 1, 0, 0, 0);
        }
        catch(SQLException ignore)
        {
            System.err.println("Generate Lessons Set");
            System.exit(1);
        }
    }


    @SuppressWarnings("Duplicates") protected void generateLessonAvailableClassroom()
    {
        try
        {
            DBComponent                                db_component = super.db_component;
            Dataset1<Timeoff, Lesson>                  dataset      = super.dataset;
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


    @Override protected void generateSubjects()
    {
        try
        {
            DBComponent                                db_component = super.db_component;
            Dataset1<Timeoff, Lesson>                  dataset      = super.dataset;
            DatasetConverter<Int2IntLinkedOpenHashMap> encoder      = super.encoder;
            DatasetConverter<Int2IntLinkedOpenHashMap> decoder      = super.decoder;
            /** Query for all subject in the specific school id according to lessons*/
            String query = "SELECT COUNT(DISTINCT `lesson`.`subject`) AS 'count' FROM `lesson` LEFT OUTER JOIN `subject` ON `lesson`.`subject` = `subject`.`id` WHERE `subject`.`school` = ?";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, dataset.school);
            db_component.result_set = db_component.statement.executeQuery();
            db_component.result_set.next();
            int size = db_component.result_set.getInt("count");

            /** Query for all subject in the specific school id according to lessons*/
            query = "SELECT DISTINCT `lesson`.`subject` AS 'id' FROM `lesson` LEFT OUTER JOIN `subject` ON `lesson`.`subject` = `subject`.`id` WHERE `subject`.`school` = ? ORDER BY `lesson`.`subject` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, dataset.school);
            db_component.result_set = db_component.statement.executeQuery();

            dataset.subjects = new Timeoff[size];
            encoder.subjects = new Int2IntLinkedOpenHashMap(size);
            decoder.subjects = new Int2IntLinkedOpenHashMap(size);

            for(int result_set_counter = 0; db_component.result_set.next(); ++result_set_counter)
            {
                dataset.subjects[result_set_counter] = new Timeoff(dataset.active_days.length, dataset.active_periods.length);
                encoder.subjects.put(db_component.result_set.getInt("id"), result_set_counter);
                decoder.subjects.put(result_set_counter, db_component.result_set.getInt("id"));
            }

            /** Query for all subject in the specific school id according to lessons*/
            query = "SELECT `subject_timeoff`.`subject`, `subject_timeoff`.`day`, `subject_timeoff`.`period`, `availability`.`value` FROM `subject_timeoff` LEFT OUTER JOIN `availability` ON `availability`.`id` = `subject_timeoff`.`availability` WHERE `subject_timeoff`.`subject` IN (SELECT DISTINCT `lesson`.`subject` FROM `lesson` LEFT OUTER JOIN `subject` ON `lesson`.`subject` = `subject`.`id` WHERE `subject`.`school` = ?) ORDER BY `subject_timeoff`.`subject`, `subject_timeoff`.`day`, `subject_timeoff`.`period` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, dataset.school);
            db_component.result_set = db_component.statement.executeQuery();


            while(db_component.result_set.next())
            {
                dataset.subjects[encoder.subjects.get(db_component.result_set.getInt("subject"))].add(encoder.active_days.get(db_component.result_set.getInt("day")), encoder.active_periods.get(db_component.result_set.getInt("period")), db_component.result_set.getDouble("value"));
            }
        }
        catch(SQLException ignored)
        {
            System.err.println("Generate Subject");
            System.exit(-1);
        }
    }


    @Override protected void generateLecturers()
    {
        try
        {
            DBComponent                                db_component = super.db_component;
            Dataset1<Timeoff, Lesson>                  dataset      = super.dataset;
            DatasetConverter<Int2IntLinkedOpenHashMap> encoder      = super.encoder;
            DatasetConverter<Int2IntLinkedOpenHashMap> decoder      = super.decoder;
            /** Query for all lecture in the specific school id according to lessons*/
            String query = "SELECT COUNT(DISTINCT `lesson`.`lecture`) AS 'count' FROM `lesson` LEFT OUTER JOIN `lecture` ON `lesson`.`lecture` = `lecture`.`id` WHERE `lecture`.`school` = ?";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, dataset.school);
            db_component.result_set = db_component.statement.executeQuery();
            db_component.result_set.next();
            int size = db_component.result_set.getInt("count");

            /** Query for all lecture in the specific school id according to lessons*/
            query = "SELECT DISTINCT `lesson`.`lecture` AS 'id' FROM `lesson` LEFT OUTER JOIN `lecture` ON `lesson`.`lecture` = `lecture`.`id` WHERE `lecture`.`school` = ? ORDER BY `lesson`.`lecture` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, dataset.school);
            db_component.result_set = db_component.statement.executeQuery();

            dataset.lecturers = new Timeoff[size];
            encoder.lecturers = new Int2IntLinkedOpenHashMap(size);
            decoder.lecturers = new Int2IntLinkedOpenHashMap(size);

            for(int result_set_counter = 0; db_component.result_set.next(); ++result_set_counter)
            {
                dataset.lecturers[result_set_counter] = new Timeoff(dataset.active_days.length, dataset.active_periods.length);
                encoder.lecturers.put(db_component.result_set.getInt("id"), result_set_counter);
                decoder.lecturers.put(result_set_counter, db_component.result_set.getInt("id"));
            }

            /** Query for all lecture in the specific school id according to lessons*/
            query = "SELECT `lecture_timeoff`.`lecture`, `lecture_timeoff`.`day`, `lecture_timeoff`.`period`, `availability`.`value` FROM `lecture_timeoff` LEFT OUTER JOIN `availability` ON `availability`.`id` = `lecture_timeoff`.`availability` WHERE `lecture_timeoff`.`lecture` IN (SELECT DISTINCT `lesson`.`lecture` FROM `lesson` LEFT OUTER JOIN `lecture` ON `lesson`.`lecture` = `lecture`.`id` WHERE `lecture`.`school` = ?) ORDER BY `lecture_timeoff`.`lecture`, `lecture_timeoff`.`day`, `lecture_timeoff`.`period` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, dataset.school);
            db_component.result_set = db_component.statement.executeQuery();


            while(db_component.result_set.next())
            {
                dataset.lecturers[encoder.lecturers.get(db_component.result_set.getInt("lecture"))].add(encoder.active_days.get(db_component.result_set.getInt("day")), encoder.active_periods.get(db_component.result_set.getInt("period")), db_component.result_set.getDouble("value"));
            }
        }
        catch(SQLException ignored)
        {
            System.err.println("Generate Lecture");
            System.exit(-1);
        }
    }


    @Override protected void generateClassrooms()
    {
        try
        {
            DBComponent                                db_component = super.db_component;
            Dataset1<Timeoff, Lesson>                  ddatasets    = super.dataset;
            DatasetConverter<Int2IntLinkedOpenHashMap> encoder      = super.encoder;
            DatasetConverter<Int2IntLinkedOpenHashMap> decoder      = super.decoder;
            /** Query for all classrooms in the specific school id according to lessons*/
            String query = "SELECT COUNT(DISTINCT `lesson_available_classroom`.`classroom`) AS 'count' FROM `lesson_available_classroom` RIGHT OUTER JOIN `lesson` ON `lesson_available_classroom`.`lesson` = `lesson`.`id` WHERE `lesson_available_classroom`.`classroom` IN (SELECT `classroom`.`id` FROM `classroom` WHERE `classroom`.`school` = ?)";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, ddatasets.school);
            db_component.result_set = db_component.statement.executeQuery();
            db_component.result_set.next();
            int size = db_component.result_set.getInt("count");

            /** Query for all classrooms in the specific school id according to lessons*/
            query = "SELECT DISTINCT `lesson_available_classroom`.`classroom` AS 'id' FROM `lesson_available_classroom` RIGHT OUTER JOIN `lesson` ON `lesson_available_classroom`.`lesson` = `lesson`.`id` WHERE `lesson_available_classroom`.`classroom` IN (SELECT `classroom`.`id` FROM `classroom` WHERE `classroom`.`school` = ?) ORDER BY `lesson_available_classroom`.`classroom` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, ddatasets.school);
            db_component.result_set = db_component.statement.executeQuery();

            ddatasets.classrooms = new Timeoff[size];
            encoder.classrooms = new Int2IntLinkedOpenHashMap(size);
            decoder.classrooms = new Int2IntLinkedOpenHashMap(size);

            for(int result_set_counter = 0; db_component.result_set.next(); ++result_set_counter)
            {
                ddatasets.classrooms[result_set_counter] = new Timeoff(ddatasets.active_days.length, ddatasets.active_periods.length);
                encoder.classrooms.put(db_component.result_set.getInt("id"), result_set_counter);
                decoder.classrooms.put(result_set_counter, db_component.result_set.getInt("id"));
            }

            /** Query for all classrooms in the specific school id according to lessons*/
            query = "SELECT `classroom_timeoff`.`classroom`, `classroom_timeoff`.`day`, `classroom_timeoff`.`period`, `availability`.`value` FROM `classroom_timeoff` LEFT OUTER JOIN `availability` ON `availability`.`id` = `classroom_timeoff`.`availability` WHERE `classroom_timeoff`.`classroom` IN (SELECT DISTINCT `lesson_available_classroom`.`classroom` FROM `lesson_available_classroom` WHERE `lesson_available_classroom`.`classroom` IN (SELECT `classroom`.`id` FROM `classroom` WHERE `classroom`.`school` = ?)) ORDER BY `classroom_timeoff`.`classroom`, `classroom_timeoff`.`day`, `classroom_timeoff`.`period` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, ddatasets.school);
            db_component.result_set = db_component.statement.executeQuery();


            while(db_component.result_set.next())
            {
                ddatasets.classrooms[encoder.classrooms.get(db_component.result_set.getInt("classrooms"))].add(encoder.active_days.get(db_component.result_set.getInt("day")), encoder.active_periods.get(db_component.result_set.getInt("period")), db_component.result_set.getDouble("value"));
            }
        }
        catch(SQLException ignored)
        {
            System.err.println("Generate Classroom");
            System.exit(-1);
        }
    }


    @Override protected void generateClasses()
    {
        try
        {
            DBComponent                                db_component = super.db_component;
            Dataset1<Timeoff, Lesson>                  dataset      = super.dataset;
            DatasetConverter<Int2IntLinkedOpenHashMap> encoder      = super.encoder;
            DatasetConverter<Int2IntLinkedOpenHashMap> decoder      = super.decoder;
            /** Query for all class in the specific school id according to lessons*/
            String query = "SELECT COUNT(DISTINCT `lesson`.`class`) AS 'count' FROM `lesson` LEFT OUTER JOIN `class` ON `lesson`.`class` = `class`.`id` WHERE `class`.`school` = ?";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, dataset.school);
            db_component.result_set = db_component.statement.executeQuery();
            db_component.result_set.next();
            int size = db_component.result_set.getInt("count");

            /** Query for all class in the specific school id according to lessons*/
            query = "SELECT DISTINCT `lesson`.`class` AS 'id' FROM `lesson` LEFT OUTER JOIN `class` ON `lesson`.`class` = `class`.`id` WHERE `class`.`school` = ? ORDER BY `lesson`.`class` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, dataset.school);
            db_component.result_set = db_component.statement.executeQuery();

            dataset.classes = new Timeoff[size];
            encoder.classes = new Int2IntLinkedOpenHashMap(size);
            decoder.classes = new Int2IntLinkedOpenHashMap(size);

            for(int result_set_counter = 0; db_component.result_set.next(); ++result_set_counter)
            {
                dataset.classes[result_set_counter] = new Timeoff(dataset.active_days.length, dataset.active_periods.length);
                encoder.classes.put(db_component.result_set.getInt("id"), result_set_counter);
                decoder.classes.put(result_set_counter, db_component.result_set.getInt("id"));
            }

            /** Query for all class in the specific school id according to lessons*/
            query = "SELECT `class_timeoff`.`class`, `class_timeoff`.`day`, `class_timeoff`.`period`, `availability`.`value` FROM `class_timeoff` LEFT OUTER JOIN `availability` ON `availability`.`id` = `class_timeoff`.`availability` WHERE `class_timeoff`.`class` IN (SELECT DISTINCT `lesson`.`class` FROM `lesson` LEFT OUTER JOIN `class` ON `lesson`.`class` = `class`.`id` WHERE `class`.`school` = ?) ORDER BY `class_timeoff`.`class`, `class_timeoff`.`day`, `class_timeoff`.`period` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, dataset.school);
            db_component.result_set = db_component.statement.executeQuery();


            while(db_component.result_set.next())
            {
                dataset.classes[encoder.classes.get(db_component.result_set.getInt("class"))].add(encoder.active_days.get(db_component.result_set.getInt("day")), encoder.active_periods.get(db_component.result_set.getInt("period")), db_component.result_set.getDouble("value"));
            }
        }
        catch(SQLException ignored)
        {
            System.err.println("Generate Class");
            System.exit(-1);
        }
    }


    @Override protected void generateActivePeriods()
    {
        try
        {
            DBComponent                                db_component = super.db_component;
            Dataset1<Timeoff, Lesson>                  dataset      = super.dataset;
            DatasetConverter<Int2IntLinkedOpenHashMap> encoder      = super.encoder;
            DatasetConverter<Int2IntLinkedOpenHashMap> decoder      = super.decoder;
            String                                     query        = "SELECT COUNT(`active_period`.`id`) AS 'count' FROM `active_period` WHERE `active_period`.`school` = ?";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, dataset.school);
            db_component.result_set = db_component.statement.executeQuery();
            db_component.result_set.next();
            int size = db_component.result_set.getInt("count");

            query = "SELECT `active_period`.`id` FROM `active_period` WHERE `active_period`.`school` = ? ORDER BY `active_period`.`position` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, dataset.school);
            db_component.result_set = db_component.statement.executeQuery();

            dataset.active_periods = new int[size];
            encoder.active_periods = new Int2IntLinkedOpenHashMap(size);
            decoder.active_periods = new Int2IntLinkedOpenHashMap(size);

            for(int result_set_counter = 0; db_component.result_set.next(); ++result_set_counter)
            {
                dataset.active_periods[result_set_counter] = result_set_counter;
                encoder.active_periods.put(db_component.result_set.getInt("id"), result_set_counter);
                decoder.active_periods.put(result_set_counter, db_component.result_set.getInt("id"));
            }
        }
        catch(SQLException ignored)
        {
            System.err.println("Generate Period Day");
            System.exit(-1);
        }
    }


    @Override protected void generateActiveDays()
    {
        try
        {
            DBComponent                                db_component = super.db_component;
            Dataset1<Timeoff, Lesson>                  dataset      = super.dataset;
            DatasetConverter<Int2IntLinkedOpenHashMap> encoder      = super.encoder;
            DatasetConverter<Int2IntLinkedOpenHashMap> decoder1     = super.decoder;
            String                                     query        = "SELECT COUNT(`active_day`.`id`) AS 'count' FROM `active_day` WHERE `active_day`.`school` = ?";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, dataset.school);
            db_component.result_set = db_component.statement.executeQuery();
            db_component.result_set.next();
            int size = db_component.result_set.getInt("count");

            query = "SELECT `active_day`.`id` FROM `active_day` WHERE `active_day`.`school` = ? ORDER BY `active_day`.`position` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, dataset.school);
            db_component.result_set = db_component.statement.executeQuery();

            dataset.active_days = new int[size];
            encoder.active_days = new Int2IntLinkedOpenHashMap(size);
            decoder1.active_days = new Int2IntLinkedOpenHashMap(size);

            for(int result_set_counter = 0; db_component.result_set.next(); ++result_set_counter)
            {
                dataset.active_days[result_set_counter] = result_set_counter;
                encoder.active_days.put(db_component.result_set.getInt("id"), result_set_counter);
                decoder1.active_days.put(result_set_counter, db_component.result_set.getInt("id"));
            }
        }
        catch(SQLException ignored)
        {
            System.err.println("Generate Active Day");
            System.exit(-1);
        }
    }
}
