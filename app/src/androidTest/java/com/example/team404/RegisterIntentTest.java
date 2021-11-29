package com.example.team404;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.team404.Login.RegisterActivity;
import com.robotium.solo.Solo;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;
public class RegisterIntentTest<password, reg_btn> {
    private Solo solo;

    @Rule
    public ActivityTestRule<RegisterActivity> rule =
            new ActivityTestRule<>(RegisterActivity.class, true, true);





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
    public void testRegisterActivity(){
        solo.assertCurrentActivity("current Activity", RegisterActivity.class);
        solo.getCurrentActivity();
        System.out.println("---"+solo.getCurrentActivity());
        EditText password = (EditText) solo.getView(R.id.password);
        EditText repassword = (EditText) solo.getView(R.id.repassward);
        EditText phone = (EditText) solo.getView(R.id.reg_phone);
        EditText email = (EditText) solo.getView(R.id.reg_email);
        EditText name = (EditText) solo.getView(R.id.reg_name);
        solo.enterText(name, "test");
        solo.enterText(phone,"780-600-0000");
        solo.enterText(email, "test@test.com");
        solo.enterText(password, "123123");
        solo.enterText(repassword, "123123");
        solo.clickOnButton("SignUp");



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
