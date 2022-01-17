package de.sep.sherloql.bin;

import static android.graphics.Color.argb;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;

import de.sep.sherloql.R;
import de.sep.sherloql.savestate.SaveStateHelper;
import de.sep.sherloql.story.Chapter;
import de.sep.sherloql.story.Dialogue;
import de.sep.sherloql.story.DialogueFragment;
import de.sep.sherloql.story.ParseStory;
import de.sep.sherloql.story.RiddleFragment;
import de.sep.sherloql.story.SpecialChapter;
import de.sep.sherloql.story.Story;


/**
 * @author MatthiasFranz
 * @author JudyAlchaar
 */
public class Map extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private static final String TAG = "MapsActivity";
    private static Story story;
    private static int length;
    private static ParseStory parseStory;
    private static ArrayList<Chapter> chapterArrayList;

    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String specChap = "Spezial Kapitel";
    private GoogleMap map;
    private GeofencingClient geofencingClient;
    private GeofenceHelper geofenceHelper;
    private Geocoder geocoder;
    private final float GEOFENCE_RADIUS = 50;
    private final String GEOFENCE_ID = "SOME_GEOFENCE_ID";
    private final int ACCESS_LOCATION_REQUEST_CODE = 10001;
    private final int LOCATION_ACCESS_REQUEST_CODE = 10001;
    private final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private SaveStateHelper stateHelper;
    private Button homeButton;
    //DEBUG
    private final boolean enableFences = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        boolean firstStory = sp.getBoolean("firstStory", true);
        stateHelper = new SaveStateHelper(this);
        setContentView(R.layout.activity_map);

        homeButton = findViewById(R.id.fromMap2Home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        if (parseStory == null) {
            Log.d(TAG, "map");
            parseStory = new ParseStory(this);
            story = parseStory.parse();
            length = parseStory.getItemCount();

            Log.d(TAG, "#Kapitel: " + length);
            chapterArrayList = story.getChapterArrayList();
            if (firstStory) {
                for (int i = 0; i < length; i++) {
                    if (i == 0) {

                        stateHelper.insertStory(chapterArrayList.get(i).getName(), "true", "false", chapterArrayList.get(i).getRiddle().getAnswer());

                        for (int j = 0; j < chapterArrayList.get(i).getArtefacts().size(); j++) {
                            stateHelper.insertArtefacts(chapterArrayList.get(i).getArtefacts().get(j), "false", chapterArrayList.get(i).getName());
                        }

                        for (int k = 0; k < chapterArrayList.get(i).getDependencies().size(); k++) {
                            if (chapterArrayList.get(i).getDependencies().get(k).charAt(0) == '!' && !stateHelper.checkArtefactExists(chapterArrayList.get(i).getDependencies().get(k))) {
                                stateHelper.insertArtefacts(chapterArrayList.get(i).getDependencies().get(k), "true", "dependency");
                            }
                        }

                    } else {
                        if (chapterArrayList.get(i).getFlag() == 1) {
                            stateHelper.insertStory("Spezial Kapitel", "false", "false", chapterArrayList.get(i).getRiddle().getAnswer());
                        } else {
                            stateHelper.insertStory(chapterArrayList.get(i).getName(), "false", "false", chapterArrayList.get(i).getRiddle().getAnswer());

                            for (int j = 0; j < chapterArrayList.get(i).getArtefacts().size(); j++) {
                                stateHelper.insertArtefacts(chapterArrayList.get(i).getArtefacts().get(j), "false", chapterArrayList.get(i).getName());
                            }

                            for (int k = 0; k < chapterArrayList.get(i).getDependencies().size(); k++) {
                                if (chapterArrayList.get(i).getDependencies().get(k).charAt(0) == '!' && !stateHelper.checkArtefactExists(chapterArrayList.get(i).getDependencies().get(k))) {
                                    stateHelper.insertArtefacts(chapterArrayList.get(i).getDependencies().get(k), "true", "dependency");
                                }
                            }
                        }
                    }
                    for (int j = 0; j < chapterArrayList.get(i).getRiddle().getHints().size(); j++) {
                        stateHelper.insertHints(String.valueOf(j), "false", String.valueOf(i));
                    }

                }
                SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp1.edit();
                editor.putBoolean("firstStory", false);
                editor.apply();
            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        geofencingClient = LocationServices.getGeofencingClient(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        geofenceHelper = new GeofenceHelper(this);
        geocoder = new Geocoder(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        try {
            initMarkers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        enableUserLocation();

        if (!stateHelper.getStorySolved(specChap)) {
            if (SpecialChapter.Professor) {
                homeButton.setVisibility(View.INVISIBLE);
                stateHelper.updateStory(specChap, "true", "true");
                Chapter chapter1 = chapterArrayList.get(18);
                ArrayList<Dialogue> dialogue = chapter1.getDialogueArrayBefore();
                DialogueFragment fragment = DialogueFragment.newInstance(dialogue, 0, chapter1);
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.map, fragment).addToBackStack("").commit();
            }
            if (SpecialChapter.Psycho) {
                homeButton.setVisibility(View.INVISIBLE);
                stateHelper.updateStory(specChap, "true", "true");
                Chapter chapter1 = chapterArrayList.get(19);
                ArrayList<Dialogue> dialogue = chapter1.getDialogueArrayBefore();
                DialogueFragment fragment = DialogueFragment.newInstance(dialogue, 0, chapter1);
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.map, fragment).addToBackStack("").commit();
            }
            if (SpecialChapter.Henrik) {
                homeButton.setVisibility(View.INVISIBLE);
                stateHelper.updateStory(specChap, "true", "true");
                Chapter chapter1 = chapterArrayList.get(20);
                ArrayList<Dialogue> dialogue = chapter1.getDialogueArrayBefore();
                DialogueFragment fragment = DialogueFragment.newInstance(dialogue, 0, chapter1);
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.map, fragment).addToBackStack("").commit();
            }
            if (SpecialChapter.Viktoria) {
                homeButton.setVisibility(View.INVISIBLE);
                stateHelper.updateStory(specChap, "true", "true");
                Chapter chapter1 = chapterArrayList.get(21);
                ArrayList<Dialogue> dialogue = chapter1.getDialogueArrayBefore();
                DialogueFragment fragment = DialogueFragment.newInstance(dialogue, 0, chapter1);
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.map, fragment).addToBackStack("").commit();
            }
            if (SpecialChapter.Moriarty) {
                homeButton.setVisibility(View.INVISIBLE);
                stateHelper.updateStory(specChap, "true", "true");
                Chapter chapter1 = chapterArrayList.get(22);
                ArrayList<Dialogue> dialogue = chapter1.getDialogueArrayBefore();
                DialogueFragment fragment = DialogueFragment.newInstance(dialogue, 0, chapter1);
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.map, fragment).addToBackStack("").commit();
            }
        }
    }

    public void initMarkers() {
        for (int i = 0; i < length; i++) {
            LatLng braunschweig = new LatLng(chapterArrayList.get(i).getLatitude(), chapterArrayList.get(i).getLongitude());
            MarkerOptions marker;
            if (getResources().getIdentifier(getIcon(i), "drawable", getPackageName()) != 0) {
                marker = new MarkerOptions().position(braunschweig).title("Marker in Braunschweig").snippet(Integer.toString(i)).icon(BitmapDescriptorFactory.fromBitmap(getResizedBitmap(getIcon(i), 200, 200)));
            } else {
                marker = new MarkerOptions().position(braunschweig).title("Marker in Braunschweig").snippet(Integer.toString(i)).icon(BitmapDescriptorFactory.defaultMarker());
            }
            map.moveCamera(CameraUpdateFactory.newLatLng(braunschweig));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(braunschweig, 13.0f));

            if (checkArtefacts(i)) {
                stateHelper.updateStoryUnlocked(chapterArrayList.get(i).getName(), "true");
            }

            if (!checkTTL(i)) {
                if (stateHelper.getStoryUnlocked(chapterArrayList.get(i).getName()) && !stateHelper.getStorySolved(chapterArrayList.get(i).getName())) {
                    Log.d(TAG, "marker hinzugefügt " + chapterArrayList.get(i).getName());
                    addGeofence(braunschweig, GEOFENCE_RADIUS);
                    drawCircle(braunschweig);
                    map.addMarker(marker).setVisible(true);

                }
            }
        }

        Log.d(TAG, "initMarkers: " + stateHelper.getBlub());
        map.setOnMarkerClickListener(this);
    }

    public String getIcon(int index) {
        ArrayList<String> persons = new ArrayList<>();
        int flag = chapterArrayList.get(index).getFlag();
        String icon;
        for (int i = 0; i < chapterArrayList.get(index).getDialogueArrayBefore().size(); i++) {
            if (!persons.contains(chapterArrayList.get(index).getDialogueArrayBefore().get(i).getPerson())) {
                persons.add(chapterArrayList.get(index).getDialogueArrayBefore().get(i).getPerson());
            }
        }
        for (int i = 0; i < chapterArrayList.get(index).getDialogueArrayAfter().size(); i++) {
            if (!persons.contains(chapterArrayList.get(index).getDialogueArrayAfter().get(i).getPerson())) {
                persons.add(chapterArrayList.get(index).getDialogueArrayAfter().get(i).getPerson());
            }
        }
        Collections.sort(persons);
        if (flag == 42) {
            //Nebenkapitel design
            icon = persons.get(0).toLowerCase() + "_nb_icon";
        } else {
            if (persons.size() == 1) {
                icon = persons.get(0).toLowerCase() + "_icon";
            } else {
                persons.remove("Detektiv");
                if (persons.size() >= 2) {
                    icon = persons.get(0).toLowerCase() + "und" + persons.get(1).toLowerCase() + "_icon";
                } else {
                    icon = persons.get(0).toLowerCase() + "_icon";
                }
            }
        }
        return icon;
    }

    public boolean checkArtefacts(int index) {
        Log.d(TAG, "checkArtefacts");
        ArrayList<String> dependencies = chapterArrayList.get(index).getDependencies();
        for (int i = 0; i < dependencies.size(); i++) {

            if (!stateHelper.getArtefactUnlocked(dependencies.get(i))) {
                Log.d(TAG, "false depend " + dependencies.get(i));
                return false;
            } else {
                Log.d(TAG, "true depend " + dependencies.get(i));
            }
        }
        Log.d(TAG, "checkArtefacts true");
        return true;
    }

    public boolean checkTTL(int index) {
        ArrayList<String> ttl = chapterArrayList.get(index).getTTL();
        Log.d(TAG, "checkTTL" + ttl.size());
        if (ttl == null) {
            return false;
        } else if (ttl.isEmpty()) {
            return false;
        }
        for (int i = 0; i < ttl.size(); i++) {
            if (stateHelper.getArtefactUnlocked(ttl.get(i)) == false) {
                Log.d(TAG, "ttl false depend " + ttl.get(i));
                return false;
            } else {
                Log.d(TAG, " ttl true depend " + ttl.get(i));
            }
        }
        Log.d(TAG, "checkTTL true");
        return true;
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        if (marker.getSnippet() != null) {
            homeButton.setVisibility(View.INVISIBLE);
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true));
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onMarkerClick: permission granted");
            }
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                LatLng self = new LatLng(lat, lng);
                int chapterPosition = Integer.parseInt(marker.getSnippet());
                Chapter chapter = chapterArrayList.get(chapterPosition);
                LatLng current = new LatLng(chapter.getLatitude(), chapter.getLongitude());

                // fences are enabled
                if (enableFences) {
                    if (getDistance(self, current) <= GEOFENCE_RADIUS) {
                        if (chapterPosition == 17) {
                            Log.d(TAG, "onMarkerClick: die if clause funktioniert bei onmarkerclick");
                            SpecialChapter chapFrag = SpecialChapter.getInstance();
                            if (chapFrag.isChapterFinished()) {
                                return true;
                            }
                            Log.d(TAG, "onMarkerClick: 4");
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            Log.d(TAG, "onMarkerClick: 3");
                            transaction.replace(R.id.map, chapFrag).addToBackStack("wtf").commit();
                            Log.d(TAG, "onMarkerClick: 1");

                        } else {
                            if (!stateHelper.getStorySolved(chapter.getName())) {
                                ArrayList<Dialogue> dialogue = chapter.getDialogueArrayBefore();
                                Log.d(TAG, "dialoooog Map: " + dialogue.size());
                                // prüfen ob es ein Dialogue gibt.
                                if (dialogue != null) {
                                    DialogueFragment fragment = DialogueFragment.newInstance(dialogue, 0, chapter);
                                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.map, fragment).addToBackStack("").commit();
                                } else {
                                    RiddleFragment fragment = RiddleFragment.newInstance(chapter);
                                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.map, fragment).addToBackStack("").commit();
                                }
                            }
                        }
                    } else {
                        Toast.makeText(this, "Bitte erst den Standort erreichen", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "distance: " + getDistance(self, current));
                    }
                } else {
                    if (chapterPosition == 17) {
                        Log.d(TAG, "onMarkerClick: die if clause funktioniert bei onmarkerclick");
                        SpecialChapter chapFrag = SpecialChapter.getInstance();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.map, chapFrag).addToBackStack("wtf").commit();

                    } else {
                        if (!stateHelper.getStorySolved(chapter.getName())) {
                            ArrayList<Dialogue> dialogue = chapter.getDialogueArrayBefore();
                            // prüfen ob es ein Dialogue gibt.
                            if (dialogue != null) {
                                DialogueFragment fragment = DialogueFragment.newInstance(dialogue, 0, chapter);
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.map, fragment).addToBackStack("").commit();
                            } else {
                                RiddleFragment fragment = RiddleFragment.newInstance(chapter);
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.map, fragment).addToBackStack("").commit();
                            }
                        }
                    }
                }

            }
        }
        return true;
    }

    public Bitmap getResizedBitmap(String iconName, int newWidth, int newHeight) {
        Log.d(TAG, "getResizedBitmap: " + iconName);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getPackageName()));
        if (bm == null) {
            Log.d(TAG, "getResizedBitmap: nulll" + iconName);
        }
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public double getDistance(LatLng LatLng1, LatLng LatLng2) {
        double distance = 0;
        Location locationA = new Location("A");
        locationA.setLatitude(LatLng1.latitude);
        locationA.setLongitude(LatLng1.longitude);
        Location locationB = new Location("B");
        locationB.setLatitude(LatLng2.latitude);
        locationB.setLongitude(LatLng2.longitude);
        distance = locationA.distanceTo(locationB);

        return distance;
    }

    @Override
    public void onBackPressed() {
        openLastActivity();
        super.onBackPressed();
    }

    private void openLastActivity() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            finish();
            startActivity(getIntent());
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    protected void addGeofence(LatLng latLng, float radius) {
        Geofence geofence = geofenceHelper.getGeofence(GEOFENCE_ID, latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest geofencingRequest = geofenceHelper.getGeofencingRequest(geofence);
        PendingIntent pendingIntent = geofenceHelper.getPendingIntent();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            geofencingClient.addGeofences(geofencingRequest, pendingIntent);
        }
    }


    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "enableUserLocation: permissions granted");
            if (map != null) {
                Log.d(TAG, "enableUserLocation: map is not null");
                map.setMyLocationEnabled(true);
                zoomToUserLocation();
            } else {
                openLastActivity();
            }
        } else {
            Log.d(TAG, "enableUserLocation: permissions not granted");
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    private void zoomToUserLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
            locationTask.addOnSuccessListener(location -> {
                Log.d(TAG, "zoomToUserLocation: permissions granted");
                if(map != null || location != null){
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                } else {
                    openLastActivity();
                }
            });
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        zoomToUserLocation();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_ACCESS_REQUEST_CODE) {
            Log.d(TAG, "onRequestPermissionsResult: correct request code");
            if(isPermissionGranted(permissions, grantResults)) {
                Log.d(TAG, "onRequestPermissionsResult: permissions granted");
                enableUserLocation();
            } else {
                Log.d(TAG, "onRequestPermissionsResult: permissions not granted");
                showMissingPermissionError();
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                openLastActivity();
            }
        }
    }

    /**
     * Checks if all requested permissions are granted.
     */
    private boolean isPermissionGranted(String[] grantPermissions, int[] grantResults) {
        boolean fine = false, coarse = false;
        for (int i = 0; i < grantPermissions.length; i++) {
            if (grantPermissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION))
                fine = grantResults[i] == PackageManager.PERMISSION_GRANTED;
            if (grantPermissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION))
                coarse = grantResults[i] == PackageManager.PERMISSION_GRANTED;
        }
        return (coarse && fine);
    }

    private void showMissingPermissionError() {
        Toast.makeText(this,
                "Exact location is required to play the game.", Toast.LENGTH_SHORT).show();
    }


    private void drawCircle(LatLng point) {

        // Instantiating CircleOptions to draw a circle around the marker
        CircleOptions circleOptions = new CircleOptions();

        // Specifying the center of the circle
        circleOptions.center(point);

        // Radius of the circle
        circleOptions.radius(GEOFENCE_RADIUS);

        // Border color of the circle
        circleOptions.strokeColor(Color.WHITE);

        // Fill color of the circle
        circleOptions.fillColor(argb(130, 135, 163, 214));

        // Border width of the circle
        circleOptions.strokeWidth(2);

        // Adding the circle to the GoogleMap
        map.addCircle(circleOptions);

    }
}
