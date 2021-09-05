package com.example.taskmaster;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

//    @Rule
//    public ActivityScenarioRule<MainActivity> activityScenarioRule
//            = new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public ActivityScenarioRule<SettingsPage> activitySetting
            = new ActivityScenarioRule<>(SettingsPage.class);

    public static final String STRING_TO_BE_TYPED = "Ayyoub";
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.taskmaster", appContext.getPackageName());
    }

    @Test
    public void changeUserNameFromSettings() {
        // Type text and then press the button.
        onView(withId(R.id.nameInput)).perform(typeText(STRING_TO_BE_TYPED),
                closeSoftKeyboard());
        onView(withId(R.id.submitName)).perform(click());

        // This view is in a different Activity, no need to tell Espresso.
        onView(withId(R.id.headTitle)).check(matches(withText(STRING_TO_BE_TYPED+"\'s Tasks")));
    }




}