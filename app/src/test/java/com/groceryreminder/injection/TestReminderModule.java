package com.groceryreminder.injection;

import com.groceryreminder.services.GroceryLocatorService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import se.walkercrou.places.GooglePlacesInterface;

import static org.mockito.Mockito.mock;

@Module(
        overrides = true,
        includes = ReminderModule.class,
        injects = {
                GroceryLocatorService.class
        }
)
public class TestReminderModule {

    private GooglePlacesInterface googlePlacesMock = mock(GooglePlacesInterface.class);

    @Provides
    @Singleton
    public GooglePlacesInterface getGooglePlaces() {
        return googlePlacesMock;
    }
}
