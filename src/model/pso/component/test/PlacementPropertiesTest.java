package model.pso.component.test;

import model.pso.component.PlacementProperties;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.pso.component.test> created by :
 * Name         : syafiq
 * Date / Time  : 16 June 2016, 9:56 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class PlacementPropertiesTest
{
    @Test public void TestPlacment()
    {
        PlacementProperties properties = new PlacementProperties(2, 3, 4, 5, 6, 8);
        System.out.println(properties.lecture_placement.length);
        System.out.println(properties.class_placement.length);
        System.out.println(properties.lecture_placement[0].placement.length);
        System.out.println(properties.lecture_placement[0].placement[0].length);
        System.out.println(properties.class_placement[0].placement.length);
        System.out.println(properties.class_placement[0].placement[0].length);
    }
}