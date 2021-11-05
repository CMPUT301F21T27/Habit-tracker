package com.example.team404;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;

import com.robotium.solo.Solo;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;

import javax.xml.transform.SourceLocator;

public class MyActivityIntentTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MyActivity> rule = new ActivityTestRule<>(MyActivity.class, true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());

    }
    /**
     * Simple test case to verify if everything's ok
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }
    /**
     * Test add habit function
     */
    @Test
    public void testAddHabit() throws Exception{
        solo.assertCurrentActivity("",MyActivity.class);
        solo.clickOnView(solo.getView(R.id.add_habit_button));
        solo.enterText((EditText) solo.getView(R.id.title_editText),"play");
        solo.enterText((EditText) solo.getView(R.id.reason_editText),"no homework");
        solo.clickOnView(solo.getView(R.id.date_Start_edit_button));
        solo.setDatePicker(0,2021,11,15);
        solo.clickOnText("OK");
        solo.clickOnButton("Confirm");

        assertTrue(solo.searchText("play"));
        assertTrue(solo.searchText("no homework"));
        assertTrue(solo.searchText("2021-12-15"));


    }
    /**
     * Test delete habit function
     */
    @Test
    public void testDeleteHabit() throws Exception{
        //add
        solo.assertCurrentActivity("",MyActivity.class);
        solo.clickOnView(solo.getView(R.id.add_habit_button));
        solo.enterText((EditText) solo.getView(R.id.title_editText),"play");
        solo.enterText((EditText) solo.getView(R.id.reason_editText),"no homework");
        solo.clickOnView(solo.getView(R.id.date_Start_edit_button));
        solo.setDatePicker(0,2021,11,15);
        solo.clickOnText("OK");
        solo.clickOnButton("Confirm");

        //delete
        solo.clickOnText("play");
        solo.clickOnButton("Delete");
        assertFalse(solo.searchText("play"));
        assertFalse(solo.searchText("no homework"));
        assertFalse(solo.searchText("2021-12-15"));





    }
    /**
     * Test edit habit function
     */
    @Test
    public void testEditHabit() throws Exception{
        //add
        solo.assertCurrentActivity("",MyActivity.class);
        solo.clickOnView(solo.getView(R.id.add_habit_button));
        solo.enterText((EditText) solo.getView(R.id.title_editText),"play");
        solo.enterText((EditText) solo.getView(R.id.reason_editText),"no homework");
        solo.clickOnView(solo.getView(R.id.date_Start_edit_button));
        solo.setDatePicker(0,2021,11,15);
        solo.clickOnText("OK");
        solo.clickOnButton("Confirm");

        //edit
        solo.clickOnText("play");
        solo.clearEditText((EditText) solo.getView(R.id.title_editText));
        solo.clearEditText((EditText) solo.getView(R.id.reason_editText));
        solo.enterText((EditText) solo.getView(R.id.title_editText),"csgo");
        solo.enterText((EditText) solo.getView(R.id.reason_editText),"rush B");
        solo.clickOnView(solo.getView(R.id.date_Start_edit_button));
        solo.setDatePicker(0,2022,10,13);
        solo.clickOnText("OK");
        solo.clickOnButton("Confirm");
        assertTrue(solo.searchText("csgo"));
        assertTrue(solo.searchText("rush B"));
        assertTrue(solo.searchText("2022-11-13"));




    }
    /**
     * Test today function
     */
    @Test
    public void testTodayHabit() throws Exception{
        //add
        solo.assertCurrentActivity("",MyActivity.class);
        solo.clickOnView(solo.getView(R.id.add_habit_button));
        solo.enterText((EditText) solo.getView(R.id.title_editText),"play");
        solo.enterText((EditText) solo.getView(R.id.reason_editText),"no homework");
        solo.clickOnView(solo.getView(R.id.date_Start_edit_button));
        solo.setDatePicker(0,2021,11,15);
        solo.clickOnText("OK");
        //get the current day of the week
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_WEEK);

                switch (day) {
                    case Calendar.SUNDAY:
                        solo.clickOnView(solo.getView(R.id.sunday_check));

                        break;
                    case Calendar.MONDAY:
                        solo.clickOnView(solo.getView(R.id.monday_check));

                        break;
                    case Calendar.TUESDAY:
                        solo.clickOnView(solo.getView(R.id.tuesday_check));
                        break;
                    case Calendar.WEDNESDAY:
                        solo.clickOnView(solo.getView(R.id.wednesday_check));
                        break;
                    case Calendar.THURSDAY:
                        solo.clickOnView(solo.getView(R.id.thursday_check));
                        break;
                    case Calendar.FRIDAY:
                        solo.clickOnView(solo.getView(R.id.friday_check));
                        break;
                    case Calendar.SATURDAY:
                        solo.clickOnView(solo.getView(R.id.saturday_check));
                        break;

                }
             solo.clickOnButton("Confirm");
                solo.clickOnButton("TODAY");
                assertTrue(solo.searchText("play"));






}}
