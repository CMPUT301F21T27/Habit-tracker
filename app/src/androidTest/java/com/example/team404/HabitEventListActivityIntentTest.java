package com.example.team404;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.team404.HabitEvent.HabitEventListActivity;
import com.robotium.solo.Solo;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;

import javax.xml.transform.SourceLocator;
public class HabitEventListActivityIntentTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<HabitEventListActivity> rule = new ActivityTestRule<>(HabitEventListActivity.class, true,true);

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
    public void testHasHabitEvent() throws Exception{
        solo.assertCurrentActivity("",HabitEventListActivity.class);







        solo.clickOnView(solo.getView(R.id.add_event_button));
        solo.enterText((EditText) solo.getView(R.id.title_editText),"play");
        solo.enterText((EditText) solo.getView(R.id.reason_editText),"no homework");
        solo.clickOnView(solo.getView(R.id.date_Start_Text));
        solo.setDatePicker(0,2021,11,15);
        solo.clickOnText("OK");
        solo.clickOnButton("Confirm");

        assertTrue(solo.searchText("play"));
        assertTrue(solo.searchText("no homework"));
        assertTrue(solo.searchText("2021-12-15"));


    }
    /**
     * Closes the activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
