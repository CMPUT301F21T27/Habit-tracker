package com.example.team404;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.example.team404.HabitEvent.HabitEvent;

import org.junit.Test;

public class HabitEventUnitTest {
    String id ="habit event 1";
    String uri = "https://firebasestorage.googleapis.com/v0/b/team-404-5c9b1.appspot.com/o/image%2F666%40qq.com05-2021-11-28-08-53-39.png?alt=media&token=d80300e4-1734-4d66-ae86-0bb6baafef8b";
    String location = "114 Street & 87 Avenue, Edmonton, AB T6G 2S5, Canada";
    String  comments= "Great!";
    String date = "2021-11-28";
    private HabitEvent habitEvent = new HabitEvent("habit event 1","https://firebasestorage.googleapis.com/v0/b/team-404-5c9b1.appspot.com/o/image%2F666%40qq.com05-2021-11-28-08-53-39.png?alt=media&token=d80300e4-1734-4d66-ae86-0bb6baafef8b","114 Street & 87 Avenue, Edmonton, AB T6G 2S5, Canada","Great!","2021-11-28");


    @Test
    public void testGetId() {

        assertEquals("habit event 1", habitEvent.getId());
    }
    @Test
    public void testSetId() {
        habitEvent.setId("habit event 1");
        assertEquals(id, habitEvent.getId());
    }
    @Test
    public void testGetUri() {

        assertEquals("https://firebasestorage.googleapis.com/v0/b/team-404-5c9b1.appspot.com/o/image%2F666%40qq.com05-2021-11-28-08-53-39.png?alt=media&token=d80300e4-1734-4d66-ae86-0bb6baafef8b", habitEvent.getUri());
    }

   // No setUri method, it created by the other method
    @Test
    public void testGetLocation() {
        assertEquals("114 Street & 87 Avenue, Edmonton, AB T6G 2S5, Canada", habitEvent.getLocation());
    }
    @Test
    public void testSetLocation() {
        habitEvent.setLocation("114 Street & 80 Avenue, Edmonton, AB T6G 2S5, Canada");
        assertEquals("114 Street & 80 Avenue, Edmonton, AB T6G 2S5, Canada", habitEvent.getLocation());
    }
    @Test
    public void testGetComments() {
        assertEquals("Great!", habitEvent.getComments());
    }

    //no setComments method, create comment by other smethod
    @Test
    public void testGetDate() {
        assertEquals("2021-11-28", habitEvent.getDate());
    }
    @Test
    public void testSetDate() {
        habitEvent.setDate("2021-12-12");
        assertEquals("2021-12-12", habitEvent.getDate());
    }
}
