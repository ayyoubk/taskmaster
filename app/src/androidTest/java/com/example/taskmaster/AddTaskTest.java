package com.example.taskmaster;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.room.Room;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AddTaskTest {
    @Rule
    public ActivityScenarioRule<AddTaskAstivity> addTaskActivity
            = new ActivityScenarioRule<>(AddTaskAstivity.class);


    @Test
    public void addTaskTest() {
        // Type text and then press the button.
        onView(withId(R.id.titleInput)).perform(typeText("solve cc"),closeSoftKeyboard());
        onView(withId(R.id.bodyInput)).perform(typeText("solve cc before medNight"),closeSoftKeyboard());
        onView(withId(R.id.stateInput)).perform(typeText("Done"),closeSoftKeyboard());
        onView(withId(R.id.addTaskButton)).perform(click());
        AppDB appDB = Room.databaseBuilder(getApplicationContext(), AppDB.class, "tasks").allowMainThreadQueries().build();
        TaskDao taskDao = appDB.taskDao();
        // This view is in a differ ent Activity, no need to tell Espresso.
        onView(withId(R.id.sumOfTasks)).check(matches(withText("Total Tasks: "+taskDao.getAllTasks().size())));
    }
}
