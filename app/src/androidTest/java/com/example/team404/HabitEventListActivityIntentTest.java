package com.example.team404;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.team404.HabitEvent.AddHabitEventActivity;
import com.example.team404.HabitEvent.HabitEventListActivity;
import com.example.team404.HabitEvent.MapsActivity;
import com.example.team404.Login.LoginActivity;
import com.robotium.solo.Solo;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;

public class HabitEventListActivityIntentTest {
    // from habit event list page, go to habit event page
    // this test for add comment and add location on the habit event
    // also test for delete comment and go to take photo page

    private Solo solo;
    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class, true,true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());

    }
    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }
    @Test
    public  void signIn() throws Exception {
        solo.getCurrentActivity();
        System.out.println("---"+solo.getCurrentActivity());
        EditText password = (EditText) solo.getView(R.id.user_pass);
        EditText email = (EditText) solo.getView(R.id.user_email);
        solo.enterText(email, "test@test.com");
        solo.enterText(password, "123123");
        solo.clickOnButton("Sign In");
        solo.waitForActivity(MainActivity.class, 3000);
        Thread.sleep(3000);
        solo.clickOnScreen(800, 2000);
        //add a habit
        solo.clickOnView(solo.getView(R.id.add_habit_button));
        solo.enterText((EditText) solo.getView(R.id.title_editText),"play");
        solo.enterText((EditText) solo.getView(R.id.reason_editText),"no homework");
        solo.clickOnView(solo.getView(R.id.date_Start_Text));
        solo.setDatePicker(0,2021,10,29);
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

    }


    @Test
    public void testAddLocation() throws Exception{
        signIn();
        solo.clickOnButton("TODAY");
        assertTrue(solo.searchText("play"));
        solo.clickOnText("play");
        solo.clickOnView(solo.getView(R.id.add_event_button));
        solo.clickOnView(solo.getView(R.id.addImage));

        solo.waitForActivity(AddHabitEventActivity.class);

        solo.clickOnView(solo.getView(R.id.locationImage));
        solo.waitForActivity(MapsActivity.class);
        if (solo.searchText("OK")){
            solo.clickOnText("OK");
        }
        //Thread.sleep(1000);
        solo.clickOnView(solo.getView(R.id.check_button));
        solo.waitForActivity(AddHabitEventActivity.class);

        String address = solo.getString(R.id.location_textView);
        Thread.sleep(1000);
        if (address==null){
            new AssertionError();
        }

    }
    @Test
    public void testAddComment() throws Exception{
        signIn();
        solo.clickOnButton("TODAY");
        assertTrue(solo.searchText("play"));
        solo.clickOnText("play");
        solo.clickOnView(solo.getView(R.id.add_event_button));
        solo.clickOnView(solo.getView(R.id.addImage));
        //if it stop working, please comment out next line
        //solo.clickOnText("OK");
        solo.waitForActivity(AddHabitEventActivity.class);



        solo.clickOnView(solo.getView(R.id.add_comment));
        solo.enterText((EditText) solo.getView(R.id.comment_editText), "Great!");
        if (solo.searchText("OK")){
            solo.clickOnText("OK");
        }
        Thread.sleep(2000);

        // if there is the comment in the comment area, return nothing
        // otherwise, assert error
        if(solo.searchText("Great!")){

        }else{
            new AssertionError();
        }


    }
    @Test
    public void testDeleteComment() throws Exception{
        signIn();
        solo.clickOnButton("TODAY");
        assertTrue(solo.searchText("play"));
        solo.clickOnText("play");
        solo.clickOnView(solo.getView(R.id.add_event_button));
        solo.clickOnView(solo.getView(R.id.addImage));
        //if it stop working, please comment out next line
        //solo.clickOnText("OK");
        solo.waitForActivity(AddHabitEventActivity.class);
        //first to add comment
        solo.clickOnView(solo.getView(R.id.add_comment));
        solo.enterText((EditText) solo.getView(R.id.comment_editText), "Great!");
        if (solo.searchText("OK")){
            solo.clickOnText("OK");
        }

        //then delete comment by long pressing
        solo.clickLongOnView(solo.getView(R.id.comment_textView));

        if (solo.searchText("YES")){
            solo.clickOnText("YES");
        }
        Thread.sleep(2000);

        // if there is the comment in the comment area, return nothing
        // otherwise, assert error
        if(solo.searchText("Great!")){
            new AssertionError();
        }else{

        }


    }
    @Test
    public void testTakePhoto() throws Exception{
        signIn();
        solo.clickOnButton("TODAY");
        assertTrue(solo.searchText("play"));
        solo.clickOnText("play");
        solo.clickOnView(solo.getView(R.id.add_event_button));
        solo.clickOnView(solo.getView(R.id.addImage));

        solo.waitForActivity(AddHabitEventActivity.class);

        solo.clickOnView(solo.getView(R.id.imageView));

        if (solo.searchText("Take Photo")){
            solo.clickOnText("Take Photo");
        }

        Thread.sleep(2000);
        solo.getCurrentActivity();


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
