package com.example.team404;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.team404.Login.LoginActivity;
import com.robotium.solo.Solo;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;

import javax.xml.transform.SourceLocator;

public class TodayActivityIntentTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class, true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());

    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }
    @Test
    public void GoToMyHabitActivityTest() throws Exception{
        //solo.assertCurrentActivity("current Activity", LoginActivity.class);
        solo.getCurrentActivity();
        System.out.println("---"+solo.getCurrentActivity());
        EditText password = (EditText) solo.getView(R.id.user_pass);
        EditText email = (EditText) solo.getView(R.id.user_email);
        solo.enterText(email, "test@test.com");
        solo.enterText(password, "123123");
        solo.clickOnButton("Sign In");

        solo.assertCurrentActivity("current Activity",MainActivity.class);
        solo.waitForActivity(MainActivity.class, 3000);
        //I set the time is 6000, it really depend on the internet
        //it needs some times to reload the list of habit from Firebase
        // if it is not pass, you just need to extends the time, until the list of habit is show in the list
        Thread.sleep(6000);


        solo.clickOnScreen(800, 2000);

        solo.assertCurrentActivity("Current Activity", MyActivity.class);

        solo.clickOnText("TODAY");
        solo.assertCurrentActivity("Today Activity", TodayActivity.class);
    }
    @Test
    public void testTodayHabit() throws Exception{
        //go to my activity
        solo.getCurrentActivity();
        System.out.println("---"+solo.getCurrentActivity());
        EditText password = (EditText) solo.getView(R.id.user_pass);
        EditText email = (EditText) solo.getView(R.id.user_email);
        solo.enterText(email, "test@test.com");
        solo.enterText(password, "123123");
        solo.clickOnButton("Sign In");
        solo.waitForActivity(MainActivity.class, 3000);
        Thread.sleep(6000);
        solo.clickOnScreen(800, 2000);
        //add a habit
        solo.clickOnView(solo.getView(R.id.add_habit_button));
        solo.enterText((EditText) solo.getView(R.id.title_editText),"play");
        solo.enterText((EditText) solo.getView(R.id.reason_editText),"no homework");
        solo.clickOnView(solo.getView(R.id.date_Start_Text));
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






    }

    }
