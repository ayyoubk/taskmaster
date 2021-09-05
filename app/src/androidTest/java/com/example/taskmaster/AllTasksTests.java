package com.example.taskmaster;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;


import androidx.room.Room;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AllTasksTests {

    @Rule
    public ActivityScenarioRule<AllTasksActivity> allTasksActivity
            = new ActivityScenarioRule<>(AllTasksActivity.class);
    @Test
    public void allTasksRecycle() {
        onView(withId(R.id.recycleId)).check(matches(isDisplayed()));
    }

    @Test
    public void headingIsVisible() {
        onView(withText("All Tasks")).check(matches(isDisplayed()));
    }

    @Test
    public void taskDetailsTest(){
        onView(withId(R.id.recycleId))
                .perform(actionOnItemAtPosition(0, click()));
        AppDB appDB = Room.databaseBuilder(getApplicationContext(), AppDB.class, "tasks").allowMainThreadQueries().build();
        TaskDao taskDao = appDB.taskDao();
        onView(withText(taskDao.getAllTasks().get(0).title)).check(matches(isDisplayed()));
    }
}
