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
import com.example.team404.Login.LoginActivity;
import com.robotium.solo.Solo;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class HabitEventListActivityIntentTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<HabitEventListActivity> rule = new ActivityTestRule<>(HabitEventListActivity.class, true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());

    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }
    //
    @Test
    public void testAddHabitEvent() throws Exception{
        solo.assertCurrentActivity("current Activity", HabitEventListActivity.class);
        solo.getCurrentActivity();
        solo.clickOnView(solo.getView(R.id.add_event_button));

        Thread.sleep(3000);
        solo.assertCurrentActivity("current Activity", AddHabitEventActivity.class);
        solo.clickOnView(solo.getView(R.id.add_comment));
        EditText comment = (EditText) solo.getView(R.id.comment_editText);
        solo.enterText(comment, "Great!");
        solo.getButton(AlertDialog.BUTTON_POSITIVE);
        solo.waitForDialogToClose();
        Thread.sleep(3000);
        solo.clickOnView(solo.getView(R.id.saveImage));





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
