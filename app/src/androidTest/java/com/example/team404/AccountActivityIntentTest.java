package com.example.team404;

import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.team404.Account.AccountActivity;
import com.example.team404.Account.AccountEditActivity;
import com.example.team404.Account.AccountPwdEditActivity;
import com.example.team404.Habit.Habit;
import com.example.team404.Login.LoginActivity;
import com.robotium.solo.Solo;

import junit.framework.AssertionFailedError;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;
public class AccountActivityIntentTest {
    // I use Pixel 4 API 29,
    // so I set each navigation bar for each coordinate is :
    //Four navigation bar title : x, y
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
     * test if we can go to account activity
     * @throws Exception
     */
    @Test
    public void GoToAccountActivityTest() throws Exception{
        //solo.assertCurrentActivity("current Activity", LoginActivity.class);
        solo.getCurrentActivity();
        System.out.println("---"+solo.getCurrentActivity());
        EditText password = (EditText) solo.getView(R.id.user_pass);
        EditText email = (EditText) solo.getView(R.id.user_email);
        solo.enterText(email, "test@test.com");
        solo.enterText(password, "123123");
        solo.clickOnButton("Sign In");

        solo.assertCurrentActivity("current Activity",MainActivity.class);
        solo.waitForActivity(MainActivity.class, 6000);
        //I set the time is 6000, it really depend on the internet
        Thread.sleep(6000);
        //click account button
        solo.clickOnScreen(1000, 2000);
        // check if the current page is account page
        solo.assertCurrentActivity("Current Activity", AccountActivity.class);
        Thread.sleep(6000);
        solo.waitForActivity(AccountActivity.class);
        String current_email = solo.getString(R.id.email);
        System.out.println("-------------"+current_email);
        current_email= solo.getString(R.id.email);
        if(solo.searchText("test@test.com")==true){
            System.out.println("-------Find current email is equal the test@test.com------");
        }else{
            new AssertionFailedError();
        }


    }
    //Edit account information
    // test if go to the edit page after click edit button
    @Test
    public void EditAccountActivityTest() throws Exception{

        GoToAccountActivityTest();

        solo.clickOnView(solo.getView(R.id.EditImage));
        solo.waitForActivity(AccountEditActivity.class);
        solo.assertCurrentActivity("current Activity", AccountEditActivity.class);
        solo.clickOnView(solo.getView(R.id.saveImage));
        solo.waitForActivity(AccountActivity.class);
        solo.assertCurrentActivity("current Activity", AccountActivity.class);

    }
    //Edit account password
    // test if go to the edit page after click edit button
    @Test
    public void EditAccountPwdActivityTest() throws Exception{

        GoToAccountActivityTest();

        solo.clickOnView(solo.getView(R.id.change_password_Image));
        solo.waitForActivity(AccountEditActivity.class);
        solo.assertCurrentActivity("current Activity", AccountPwdEditActivity.class);
        solo.clickOnView(solo.getView(R.id.backImage));
        solo.waitForActivity(AccountActivity.class);
        solo.assertCurrentActivity("current Activity", AccountActivity.class);

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
