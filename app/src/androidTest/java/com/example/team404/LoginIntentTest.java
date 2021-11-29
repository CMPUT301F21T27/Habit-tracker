package com.example.team404;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.team404.Login.LoginActivity;
import com.example.team404.Login.RegisterActivity;
import com.robotium.solo.Solo;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
public class LoginIntentTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<LoginActivity> rule =
            new ActivityTestRule<>(LoginActivity.class, true, true);





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
    //Test if we can sign in if we use email and password
    @Test
    public void testSignInActivity(){
        solo.assertCurrentActivity("current Activity", LoginActivity.class);
        solo.getCurrentActivity();
        System.out.println("---"+solo.getCurrentActivity());
        EditText password = (EditText) solo.getView(R.id.user_pass);
        EditText email = (EditText) solo.getView(R.id.user_email);
        solo.enterText(email, "test@test.com");
        solo.enterText(password, "123123");
        solo.clickOnButton("Sign In");

    }
    //Test if we can go to next activity after click sign up button
    @Test
    public void testGoToSignUpActivity(){
        solo.assertCurrentActivity("current Activity", LoginActivity.class);
        solo.getCurrentActivity();
        System.out.println("---"+solo.getCurrentActivity());

        solo.clickOnView(solo.getView(R.id.signup_btn));
        solo.assertCurrentActivity("current Activity", RegisterActivity.class);

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
