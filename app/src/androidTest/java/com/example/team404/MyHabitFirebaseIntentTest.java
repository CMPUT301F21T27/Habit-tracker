package com.example.team404;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ListView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.team404.Account.AccountActivity;
import com.example.team404.Habit.Habit;
import com.example.team404.Login.LoginActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;

public class MyHabitFirebaseIntentTest {
    // I use Pixel 4 API 29,
    // so I set each navigation bar for each coordinate is :
    //Home: 0, 2000
    //Subscribe: 500, 2000
    //My habit: 800, 2000
    //Account: 1000, 2000
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


    }
    /**
     * Test add habit function
     */
    @Test
    public void testAddHabit() throws Exception{
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
// add a habit
        solo.clickOnView(solo.getView(R.id.add_habit_button));
        solo.enterText((EditText) solo.getView(R.id.title_editText),"test");
        solo.enterText((EditText) solo.getView(R.id.reason_editText),"intent");
        solo.clickOnView(solo.getView(R.id.date_Start_Text));
        solo.setDatePicker(0,2022,11,12);
        solo.clickOnText("OK");
        solo.clickOnButton("Confirm");

        assertTrue(solo.searchText("test"));
        assertTrue(solo.searchText("intent"));
        assertTrue(solo.searchText("2022-12-12"));

        //delete what I added
        solo.clickOnText("test");
        solo.clickOnButton("Delete");


    }
    /**
     * Test delete habit function
     */
    @Test
    public void testDeleteHabit() throws Exception{

        //add
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
        //add a a habit for delete
        solo.assertCurrentActivity("",MyActivity.class);
        solo.clickOnView(solo.getView(R.id.add_habit_button));
        solo.enterText((EditText) solo.getView(R.id.title_editText),"delete");
        solo.enterText((EditText) solo.getView(R.id.reason_editText),"delete intent");
        solo.clickOnView(solo.getView(R.id.date_Start_Text));
        solo.setDatePicker(0,2022,11,13);
        solo.clickOnText("OK");
        solo.clickOnButton("Confirm");

        //delete
        solo.clickOnText("delete");
        solo.clickOnButton("Delete");
        assertFalse(solo.searchText("delete"));
        assertFalse(solo.searchText("delete intent"));
        assertFalse(solo.searchText("2022-12-13"));





    }
    /**
     * Test edit habit function
     */
    @Test
    public void testEditHabit() throws Exception{
        //add a habit for edit
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

        solo.clickOnView(solo.getView(R.id.add_habit_button));
        solo.enterText((EditText) solo.getView(R.id.title_editText),"edit");
        solo.enterText((EditText) solo.getView(R.id.reason_editText),"edit intent");
        solo.clickOnView(solo.getView(R.id.date_Start_Text));
        solo.setDatePicker(0,2022,11,15);
        solo.clickOnText("OK");
        solo.clickOnButton("Confirm");

        //edit
        solo.clickOnText("edit");
        solo.clearEditText((EditText) solo.getView(R.id.title_editText));
        solo.clearEditText((EditText) solo.getView(R.id.reason_editText));
        solo.enterText((EditText) solo.getView(R.id.title_editText),"csgo");
        solo.enterText((EditText) solo.getView(R.id.reason_editText),"rush B");
        solo.clickOnButton("Confirm");
        assertTrue(solo.searchText("csgo"));
        assertTrue(solo.searchText("rush B"));
        assertTrue(solo.searchText("2022-12-15"));

        //delete what I added
        solo.clickOnText("csgo");
        solo.clickOnButton("Delete");





    }









}
