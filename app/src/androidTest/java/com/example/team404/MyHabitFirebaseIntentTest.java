package com.example.team404;

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

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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

    /**
     * test if we can log in and successfully go to main activity
     * @throws Exception
     */
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

        /**
         * test if we can log in and successfully go to my activity
         * @throws Exception
         */
    }
    @Test
    public void checkUserList()throws Exception{
        solo.getCurrentActivity();
        EditText password = (EditText) solo.getView(R.id.user_pass);
        EditText email = (EditText) solo.getView(R.id.user_email);
        solo.enterText(email, "test@test.com");
        solo.enterText(password, "123123");
        solo.clickOnButton("Sign In");
        solo.waitForActivity(MainActivity.class, 3000);
        Thread.sleep(3000);
        solo.clickOnScreen(800, 2000);
        assertTrue(solo.searchText("play"));
        assertTrue(solo.searchText("no homework"));
        assertTrue(solo.searchText("2021-12-15"));

    }
    /**
     * test if we can log in and successfully go to the subscribe activity
     * @throws Exception
     */
    @Test
    public void checkSubscribeList()throws Exception{
        solo.getCurrentActivity();
        EditText password = (EditText) solo.getView(R.id.user_pass);
        EditText email = (EditText) solo.getView(R.id.user_email);
        solo.enterText(email, "bai@gmail.com");
        solo.enterText(password, "123456");
        solo.clickOnButton("Sign In");
        solo.waitForActivity(MainActivity.class, 3000);
        Thread.sleep(3000);
        solo.clickOnScreen(500, 2000);
        assertTrue(solo.searchText("video games"));
        assertTrue(solo.searchText("I like to play video games"));
        assertTrue(solo.searchText("2021-10-28"));

    }
    /**
     * test if we can log in and successfully go to the profile activity
     * @throws Exception
     */
    @Test
    public void checkProfile()throws Exception {
        solo.getCurrentActivity();
        EditText password = (EditText) solo.getView(R.id.user_pass);
        EditText email = (EditText) solo.getView(R.id.user_email);
        solo.enterText(email, "test@test.com");
        solo.enterText(password, "123123");
        solo.clickOnButton("Sign In");
        solo.waitForActivity(MainActivity.class, 3000);
        Thread.sleep(3000);
        solo.clickOnScreen(980, 2000);
        assertTrue(solo.searchText("Doer 1"));
        assertTrue(solo.searchText("780-123-1234"));
        assertTrue(solo.searchText("test@test.com"));


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
