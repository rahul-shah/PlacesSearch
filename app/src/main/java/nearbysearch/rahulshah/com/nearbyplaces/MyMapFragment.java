package nearbysearch.rahulshah.com.nearbyplaces;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import nearbysearch.rahulshah.com.nearbyplaces.model.MapResultObject;
import nearbysearch.rahulshah.com.nearbyplaces.model.PlacesSearchResults;
import nearbysearch.rahulshah.com.nearbyplaces.utils.PreferencesManager;

import static nearbysearch.rahulshah.com.nearbyplaces.AppConfig.OK;
import static nearbysearch.rahulshah.com.nearbyplaces.AppConfig.PLAY_SERVICES_RESOLUTION_REQUEST;
import static nearbysearch.rahulshah.com.nearbyplaces.AppConfig.STATUS;
import static nearbysearch.rahulshah.com.nearbyplaces.AppConfig.ZERO_RESULTS;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyMapFragment extends Fragment implements OnMapReadyCallback
{
    private static final String TAG = "MyMapFragment.java";
    private GoogleMap mMap;
    LocationManager locationManager;
    CoordinatorLayout mainCoordinatorLayout;
    private static View view;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public MyMapFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MyMapFragment newInstance(int sectionNumber) {
        MyMapFragment fragment = new MyMapFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_maps, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        if (!isGooglePlayServicesAvailable()) {
            return;
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MyMapFragment.this);

        mainCoordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.mainCoordinatorLayout);
        /*locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showLocationSettings();
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);*/
    }

    private void showLocationSettings()
    {
        Snackbar snackbar = Snackbar
                .make(mainCoordinatorLayout, "Location Error: GPS Disabled!",
                        Snackbar.LENGTH_LONG)
                .setAction("Enable", new View.OnClickListener() {
                    @Override                    public void onClick(View v) {

                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng latLng = new LatLng(PreferencesManager.getInstance().getLong(MainActivity.CURRENT_LATITUDE,0),PreferencesManager.getInstance().getLong(MainActivity.CURRENT_LONGITUDE,0));
        mMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    private void showCurrentLocation()
    {
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);

        if (location != null) {
            //onLocationChanged(location);
        }
        /*locationManager.requestLocationUpdates(bestProvider, MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);*/
    }

        private void parseLocationResult(JSONObject result)
        {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            MapResultObject apiResponseObject = gson.fromJson(result.toString(),MapResultObject.class);

            try {

                if (apiResponseObject.getStatus().equalsIgnoreCase(OK)) {

                    mMap.clear();

                    for (PlacesSearchResults placesResult: apiResponseObject.getResults())
                    {
                        MarkerOptions markerOptions = new MarkerOptions();
                        LatLng latLng = new LatLng(placesResult.getPlaceGeometry().getLocation().getLat(), placesResult.getPlaceGeometry().getLocation().getLng());
                        markerOptions.position(latLng);
                        markerOptions.title(placesResult.getName() + " : " + placesResult.getVicinity());

                        mMap.addMarker(markerOptions);
                    }
                } else if (result.getString(STATUS).equalsIgnoreCase(ZERO_RESULTS)) {
                    Toast.makeText(getActivity().getBaseContext(), "No Bars found in 5KM radius!!!",
                            Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {

                e.printStackTrace();
                Log.e(TAG, "parseLocationResult: Error=" + e.getMessage());
            }
        }

       /* @Override
        public void onLocationChanged(Location location)
        {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            LatLng latLng = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            //loadNearByPlaces(latitude, longitude);
        }*/

        private boolean isGooglePlayServicesAvailable() {
            GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
            int resultCode = apiAvailability.isGooglePlayServicesAvailable(getActivity());
            if (resultCode != ConnectionResult.SUCCESS) {
                if (apiAvailability.isUserResolvableError(resultCode)) {
                    apiAvailability.getErrorDialog(getActivity(), resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
                } else {
                    Log.i(TAG, "This device is not supported.");
                    getActivity().finish();
                }
                return false;
            }
            return true;
        }
}