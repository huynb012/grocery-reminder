package com.groceryreminder.services;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;

import com.groceryreminder.BuildConfig;
import com.groceryreminder.RobolectricTestBase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class GroceryStoreLocationListenerTest extends RobolectricTestBase {

    private GroceryStoreLocationListener groceryStoreLocationListener;
    private LocationUpdater locationUpdaterMock;

    @Before
    public void setUp() {
        super.setUp();
        this.locationUpdaterMock = mock(LocationUpdater.class);
        groceryStoreLocationListener = new GroceryStoreLocationListener(locationUpdaterMock);
    }

    @Test
    public void givenALocationIsBetterWhenALocationIsUpdatedThenTheLocationUpdaterHandlesTheUpdate() {
        Location location = new Location(LocationManager.GPS_PROVIDER);

        when(locationUpdaterMock.isBetterThanCurrentLocation(location)).thenReturn(true);

        groceryStoreLocationListener.onLocationChanged(location);

        verify(locationUpdaterMock).isBetterThanCurrentLocation(location);
        verify(locationUpdaterMock).handleLocationUpdated(location);
    }

    @Test
    public void givenALocationIsNotBetterWhenTheLocationIsChangedThenTheLocationIsNotHandled() {
        Location location = new Location(LocationManager.GPS_PROVIDER);

        when(locationUpdaterMock.isBetterThanCurrentLocation(location)).thenReturn(false);

        groceryStoreLocationListener.onLocationChanged(location);

        verify(locationUpdaterMock, times(0)).handleLocationUpdated(location);
    }

    @Test
    public void whenTheStatusChangesTheLocationUpdaterShouldRequestToListenForUpdates() {
        groceryStoreLocationListener.onStatusChanged(LocationManager.GPS_PROVIDER, LocationProvider.AVAILABLE, null);

        verify(locationUpdaterMock).removeGPSListener();
        verify(locationUpdaterMock).listenForLocationUpdates(false);
    }

    @Test
    public void whenAProviderIsEnabledTheLocationUpdaterShouldRequestToListenForUpdates() {
        groceryStoreLocationListener.onProviderEnabled(LocationManager.GPS_PROVIDER);

        verify(locationUpdaterMock).removeGPSListener();
        verify(locationUpdaterMock).listenForLocationUpdates(false);
    }

    @Test
    public void whenAProviderIsDisabledTheLocationUpdaterShouldRequestToListenForUpdates() {
        groceryStoreLocationListener.onProviderEnabled(LocationManager.GPS_PROVIDER);

        verify(locationUpdaterMock).removeGPSListener();
        verify(locationUpdaterMock).listenForLocationUpdates(false);
    }
}
