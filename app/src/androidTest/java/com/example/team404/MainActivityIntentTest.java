package com.example.team404;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.service.voice.VoiceInteractionSession;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.team404.Account.AccountActivity;
import com.example.team404.Habit.Habit;
import com.example.team404.HabitEvent.HabitEventListActivity;
import com.example.team404.Login.LoginActivity;
import com.robotium.solo.Solo;
import com.robotium.solo.Timeout;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
public class MainActivityIntentTest {
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
    // test home page
    // if there is some habit from firebase in the home page

    @Test
    public void MainListTest() throws Exception{
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
        ListView habit_list_in_home_page = solo.getCurrentActivity().findViewById(R.id.main_list);
        Thread.sleep(3000);
        Habit habit = (Habit) habit_list_in_home_page.getItemAtPosition(0);

        if (habit!=null){

        }else{
            throw  new AssertionError();
        }
        ListView habit_list_in_my_page = solo.getCurrentActivity().findViewById(R.id.habit_list);
        Thread.sleep(6000);
        Habit habit_my = (Habit) habit_list_in_home_page.getItemAtPosition(0);
        if (habit_my!=null){

        }else{
            throw  new AssertionError();
        }


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
        ListView habit_list_in_home_page = solo.getCurrentActivity().findViewById(R.id.main_list);
        Thread.sleep(6000);
        Habit habit = (Habit) habit_list_in_home_page.getItemAtPosition(0);

        if (habit!=null){

        }else{
            throw  new AssertionError();
        }

        solo.clickOnScreen(800, 2000);

        solo.assertCurrentActivity("Current Activity", MyActivity.class);



    }
    @Test
    public void GoToSubscribeActivityTest() throws Exception{
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
        ListView habit_list_in_home_page = solo.getCurrentActivity().findViewById(R.id.main_list);
        Thread.sleep(6000);
        Habit habit = (Habit) habit_list_in_home_page.getItemAtPosition(0);

        if (habit!=null){

        }else{
            throw  new AssertionError();
        }

        solo.clickOnScreen(500, 2000);

        solo.assertCurrentActivity("Current Activity", SubscribeActivity.class);


    }
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
        solo.waitForActivity(MainActivity.class, 3000);
        //I set the time is 6000, it really depend on the internet
        //it needs some times to reload the list of habit from Firebase
        // if it is not pass, you just need to extends the time, until the list of habit is show in the list
        ListView habit_list_in_home_page = solo.getCurrentActivity().findViewById(R.id.main_list);
        Thread.sleep(6000);
        Habit habit = (Habit) habit_list_in_home_page.getItemAtPosition(0);

        if (habit!=null){

        }else{
            throw  new AssertionError();
        }

        solo.clickOnScreen(1000, 2000);

        solo.assertCurrentActivity("Current Activity", AccountActivity.class);


    }



}
