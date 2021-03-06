package com.groceryreminder.injection;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;

import com.groceryreminder.domain.GroceryStoreLocationManager;
import com.groceryreminder.views.reminders.RemindersActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.LOCATION_SERVICE;

@Module(library = true
)
public class AndroidModule {

    private ReminderApplication application;

    public AndroidModule(ReminderApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ForApplication
    public Application getApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    public LocationManager getLocationManager() {
        return (LocationManager) application.getSystemService(LOCATION_SERVICE);
    }
}
