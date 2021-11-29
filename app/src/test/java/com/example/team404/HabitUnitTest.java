package com.example.team404;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.example.team404.Habit.Habit;

import org.junit.Test;

public class HabitUnitTest {
    private Habit habit = new Habit("id","title","reason","1999","11","15");

    /**
     * Test title getter is working
     */
    @Test
    public void testGetTitle(){
       assertTrue(habit.getTitle().equals("title"));

    }
    /**
     * Test reason getter is working
     */
    @Test
    public void testGetReason(){
        assertTrue(habit.getReason().equals("reason"));
    }
    /**
     * Test date getter is working
     */
    @Test
    public void testGetDate(){
        assertTrue(habit.getYear().equals("1999"));
        assertTrue(habit.getMonth().equals("11"));
        assertTrue(habit.getDay().equals("15"));
    }
    /**
     * Test "days occur in a week" getter is working
     */
    @Test
    public void testGetDaysWeek(){
       assertFalse(habit.getMonday());
        assertFalse(habit.getTuesday());
        assertFalse(habit.getWednesday());
        assertFalse(habit.getThursday());
        assertFalse(habit.getFriday());
        assertFalse(habit.getSaturday());
        assertFalse(habit.getSunday());

    }
    /**
     * Test title setter is working
     */
    @Test
    public void testSetTitle(){
      habit.setTitle("play");
      assertTrue(habit.getTitle().equals("play"));
    }
    /**
     * Test reason setter is working
     */
    @Test
    public void testSetReason(){
        habit.setReason("have time");
        assertTrue(habit.getReason().equals("have time"));
    }
    /**
     * Test date setter is working
     */
    @Test
    public void testSetDate(){
        habit.setYear("2021");
        habit.setMonth("10");
        habit.setDay("02");
        assertTrue(habit.getYear().equals("2021"));
        assertTrue(habit.getMonth().equals("10"));
        assertTrue(habit.getDay().equals("02"));
    }
    /**
     * Test "days of week" setter works
     */
    @Test
    public void testSetDaysWeek(){
        habit.setMonday(true);
        habit.setTuesday(true);
        habit.setWednesday(true);
        habit.setThursday(true);
        habit.setFriday(true);
        habit.setSunday(true);
        habit.setSaturday(true);
        habit.setPub(true);

        assertTrue(habit.getMonday());
        assertTrue(habit.getTuesday());
        assertTrue(habit.getWednesday());
        assertTrue(habit.getThursday());
        assertTrue(habit.getFriday());
        assertTrue(habit.getSaturday());
        assertTrue(habit.getSunday());
        assertTrue(habit.getPub());
    }




}
