package com.groceryreminder.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.groceryreminder.R;
import com.groceryreminder.RobolectricTestBase;
import com.groceryreminder.models.Reminder;
import com.groceryreminder.views.AddReminderFragment;
import com.groceryreminder.views.MainActivity;
import com.groceryreminder.views.ReminderListFragment;
import com.melnykov.fab.FloatingActionButton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class MainActivityTest extends RobolectricTestBase {

    private MainActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(MainActivity.class).create().start().get();
    }

    @Test
    public void whenTheActivityIsCreatedThenItShouldBeStarted() {
        assertFalse(activity.isFinishing());
    }

    @Test
    public void whenTheActivityIsCreatedThenTheReminderListShouldBeDisplayed() {
        ReminderListFragment reminderListFragment = getReminderListFragment();

        RecyclerView listView = (RecyclerView)reminderListFragment.getView().findViewById(R.id.reminders_recycler_view);
        assertEquals(View.VISIBLE, listView.getVisibility());
    }

    @Test
    public void whenTheAddReminderRequestButtonIsTappedThenTheAddReminderFragmentShouldBeDisplayed() {
        clickAddReminderRequestButton();
        AddReminderFragment addReminderFragment = getAddReminderFragment();
        EditText addReminderEditText = (EditText)addReminderFragment.getView().findViewById(R.id.add_reminder_edit);

        assertEquals(View.VISIBLE, addReminderEditText.getVisibility());
    }

    @Test
    public void givenTheAddReminderFragmentIsVisibleWhenAReminderIsEnteredThenTheReminderListIsUpdated() {
        clickAddReminderRequestButton();
        AddReminderFragment addReminderFragment = getAddReminderFragment();
        EditText addReminderEditText = (EditText)addReminderFragment.getView().findViewById(R.id.add_reminder_edit);
        String expectedText = "a reminder";
        addReminderEditText.setText(expectedText);

        clickAddReminderButton(addReminderFragment);

        ReminderListFragment reminderListFragment = getReminderListFragment();

        RecyclerView listView = (RecyclerView)reminderListFragment.getView().findViewById(R.id.reminders_recycler_view);
        performRobolectricMeasureAndLayoutHack(listView);
        TextView reminderText = (TextView)listView.findViewHolderForPosition(0).itemView.findViewById(R.id.reminders_text_view);
        assertEquals(reminderText.getText(), expectedText);
    }

    private ReminderListFragment getReminderListFragment() {
        return (ReminderListFragment)activity.getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
    }

    private AddReminderFragment getAddReminderFragment() {
        return (AddReminderFragment)activity.getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
    }

    private void clickAddReminderButton(AddReminderFragment addReminderFragment) {
        Button addButton = (Button)addReminderFragment.getView().findViewById(R.id.add_reminder_button);
        addButton.performClick();
    }

    private void clickAddReminderRequestButton() {
        FloatingActionButton addReminderRequestButton = (FloatingActionButton)activity.findViewById(R.id.fab);
        addReminderRequestButton.performClick();
    }
}