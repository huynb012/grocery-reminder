package com.groceryreminder.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.groceryreminder.BuildConfig;
import com.groceryreminder.RobolectricTestBase;
import com.groceryreminder.testUtils.ReminderValuesBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ReminderContentProviderTest extends RobolectricTestBase {

    private ReminderContentProvider provider;

    @Before
    public void setUp() {
        super.setUp();
        provider = new ReminderContentProvider();
        provider.onCreate();
    }

    @Test
    public void whenTheProviderIsCreatedThenItShouldBeInitialized() {
        ReminderContentProvider provider = new ReminderContentProvider();
        assertTrue(provider.onCreate());
    }

    @Test
    public void givenAReminderContentTypeAndContentValuesWhenARecordIsInsertedThenAURIWithRecordIDShouldBeReturned()
    {
        ContentValues values = createDefaultReminderValues();
        Uri expectedUri = provider.insert(ReminderContract.Reminders.CONTENT_URI, values);

        assertNotNull(expectedUri);
        assertEquals(1, ContentUris.parseId(expectedUri));
    }

    private ContentValues createDefaultReminderValues() {
        return new ReminderValuesBuilder().createDefaultReminderValues().build();
    }

    @Test
    public void whenADuplicateReminderIsInsertedThenItThereIsNoConflict() {
        ContentValues initialValues = createDefaultReminderValues();
        provider.insert(ReminderContract.Reminders.CONTENT_URI, initialValues);
        ContentValues duplicateValues = createDefaultReminderValues();
        duplicateValues.put(ReminderContract.Reminders.DESCRIPTION, initialValues.getAsString(ReminderContract.Reminders.DESCRIPTION));

        Uri expectedUri = provider.insert(ReminderContract.Locations.CONTENT_URI, duplicateValues);

        Cursor cursor = provider.query(expectedUri, ReminderContract.Reminders.PROJECT_ALL, "", null, null);
        assertEquals(2, cursor.getCount());
    }
}