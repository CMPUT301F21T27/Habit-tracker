package com.example.team404;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.example.team404.Habit.Habit;

import org.junit.Test;

public class HabitUnitTest {
    private Habit habit = new Habit("id","title","reason","1999","11","15");
    /**
     * Test id getter is working
     */
    @Test
    public void testGetId(){
        assertTrue(habit.getId().equals("id"));
    }
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
     * Test last day getter is working
     */
    @Test
    public void testGetLastDay(){
        assertTrue(habit.getLastDay().equals(""));
    }

    /**
     * Test total habit day getter is working
     */
    @Test
    public void testGetTotalHabitDay(){
        assertTrue(habit.getTotal_habit_day() == 1);
    }

    /**
     * Test total did getter is working
     */
    @Test
    public void testGetTotalDid(){
        assertTrue(habit.getTotal_did() == 0);
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
     * Test public getter is working
     */
    @Test
    public void testGetPublic(){
        assertFalse(habit.getPub());
    }

    /**
     * Test id setter is working
     */
    @Test
    public void testSetId(){
        habit.setId("idid");
        assertTrue(habit.getId().equals("idid"));
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
     * Test total habit day setter is working
     */
    @Test
    public void testSetTotalHabitDay(){
        habit.setTotal_habit_day(2);
        assertTrue(habit.getTotal_habit_day()==2);
    }

    /**
     * Test total habit day did setter is working
     */
    @Test
    public void testSetTotalHabitDayDid(){
        habit.setTotal_did(1);
        assertTrue(habit.getTotal_did()==1);
    }

    /**
     * Test total habit day did setter is working
     */
    @Test
    public void testSetLastDay(){
        habit.setLastDay("LastDay");
        assertTrue(habit.getLastDay().equals("LastDay"));
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

        assertTrue(habit.getMonday());
        assertTrue(habit.getTuesday());
        assertTrue(habit.getWednesday());
        assertTrue(habit.getThursday());
        assertTrue(habit.getFriday());
        assertTrue(habit.getSaturday());
        assertTrue(habit.getSunday());

    }

    /**
     * Test public setter is working
     */
    @Test
    public void testPublic(){
        habit.setPub(true);
        assertTrue(habit.getPub());
    }



}
