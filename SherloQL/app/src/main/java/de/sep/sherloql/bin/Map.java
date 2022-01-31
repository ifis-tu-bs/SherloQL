package de.sep.sherloql.bin;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.CopyrightOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
public class Map extends FragmentActivity implements LocationListener, Marker.OnMarkerClickListener {
    private static final String TAG = "MapsActivity";
    private static Story story;
    private static int length;
    private static ParseStory parseStory;
    private static ArrayList<Chapter> chapterArrayList;
    private SaveStateHelper stateHelper;

    private String specChap = "Spezial Kapitel";

    private Button homeButton;

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private final float GEOFENCE_RADIUS = 50;
    private MapView mapView = null;
    private MyLocationNewOverlay locationOverlay;
    private IMapController controller;
    private static Location currentLocation;
    private LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                //Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx,
                PreferenceManager.getDefaultSharedPreferences(ctx));


        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        boolean firstStory = sp.getBoolean("firstStory", true);
        stateHelper = new SaveStateHelper(this);


        homeButton = findViewById(R.id.fromMap2Home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLastActivity();
            }
        });
        mapView = findViewById(R.id.map);


        if (parseStory == null) {
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
        initializeMap();
        initMarkers();
        initializeActivityBackStack();

    }

    private void initializeMap() {
        locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), mapView);
        CopyrightOverlay copyrightOverlay = new CopyrightOverlay(this);
        controller = mapView.getController();

        copyrightOverlay.setTextSize(10);

        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);
        mapView.setMultiTouchControls(true);
        mapView.getOverlays().add(locationOverlay);
        mapView.getOverlays().add(copyrightOverlay);

        Bitmap icon =
                ((BitmapDrawable) mapView.getContext().getResources().getDrawable(R.drawable.location_64)).getBitmap();
        icon.setHasAlpha(true);
        locationOverlay.setPersonHotspot((float) icon.getWidth() / 2, (float) icon.getHeight() / 2);
        locationOverlay.setDirectionArrow(icon, icon);
        locationOverlay.setPersonIcon(icon);
    }

    /**
     * Function animates the position on the map to the current location, if it has already been set
     * by the location manager. Otherwise the camera moves to a default position at IZ to load
     * a region of the map. This prevents the map showing a light blue screen.
     */
    private void setInitialLocation(){
        GeoPoint moveTo;
        if (currentLocation == null) {
            moveTo = new GeoPoint(52.27301811585339, 10.524890955810347);
        } else {
            moveTo = new GeoPoint(
                    currentLocation.getLatitude(),
                    currentLocation.getLongitude());
        }
        controller.animateTo(moveTo);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        currentLocation = location;
    }

    /**
     * Checks for each permission in the parameter array if it is already granted by the system.
     * If not it requests the permissions again.
     * @param permissions String[]
     */
    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if(ActivityCompat.checkSelfPermission(this, permissions[i])
                    != PackageManager.PERMISSION_GRANTED)
                permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            lm.removeUpdates(this);
        } catch (Exception ignored) {
        }

        locationOverlay.disableFollowLocation();
        locationOverlay.disableMyLocation();
        mapView.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onResume() {
        super.onResume();
        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                //Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //check if gps is enabled. If not open a dialog to activate it.
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }

        try {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                currentLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100L, 1f, this);

            }

        } catch (Exception ignored) {
        }

        locationOverlay.enableFollowLocation();
        locationOverlay.enableMyLocation();
        mapView.onResume(); //needed for compass, my location overlays, v6.0.0 and up
        mapView.getController().setZoom(20.0);

        setInitialLocation();
    }

    private void initializeActivityBackStack() {
        if (!stateHelper.getStorySolved(specChap)) {
            if (SpecialChapter.Professor) {
                homeButton.setVisibility(View.INVISIBLE);
                stateHelper.updateStory(specChap, "true", "true");
                Chapter chapter1 = chapterArrayList.get(18);
                ArrayList<Dialogue> dialogue = chapter1.getDialogueArrayBefore();
                DialogueFragment fragment = DialogueFragment.newInstance(dialogue, 0, chapter1);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_map_root, fragment).addToBackStack("").commit();
            }
            if (SpecialChapter.Psycho) {
                homeButton.setVisibility(View.INVISIBLE);
                stateHelper.updateStory(specChap, "true", "true");
                Chapter chapter1 = chapterArrayList.get(19);
                ArrayList<Dialogue> dialogue = chapter1.getDialogueArrayBefore();
                DialogueFragment fragment = DialogueFragment.newInstance(dialogue, 0, chapter1);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_map_root, fragment).addToBackStack("").commit();
            }
            if (SpecialChapter.Henrik) {
                homeButton.setVisibility(View.INVISIBLE);
                stateHelper.updateStory(specChap, "true", "true");
                Chapter chapter1 = chapterArrayList.get(20);
                ArrayList<Dialogue> dialogue = chapter1.getDialogueArrayBefore();
                DialogueFragment fragment = DialogueFragment.newInstance(dialogue, 0, chapter1);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_map_root, fragment).addToBackStack("").commit();
            }
            if (SpecialChapter.Viktoria) {
                homeButton.setVisibility(View.INVISIBLE);
                stateHelper.updateStory(specChap, "true", "true");
                Chapter chapter1 = chapterArrayList.get(21);
                ArrayList<Dialogue> dialogue = chapter1.getDialogueArrayBefore();
                DialogueFragment fragment = DialogueFragment.newInstance(dialogue, 0, chapter1);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_map_root, fragment).addToBackStack("").commit();
            }
            if (SpecialChapter.Moriarty) {
                homeButton.setVisibility(View.INVISIBLE);
                stateHelper.updateStory(specChap, "true", "true");
                Chapter chapter1 = chapterArrayList.get(22);
                ArrayList<Dialogue> dialogue = chapter1.getDialogueArrayBefore();
                DialogueFragment fragment = DialogueFragment.newInstance(dialogue, 0, chapter1);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_map_root, fragment).addToBackStack("").commit();
            }
        }
    }

    public void initMarkers() {
        for (int i = 0; i < length; i++) {
            GeoPoint point = new GeoPoint(chapterArrayList.get(i).getLatitude(),
                    chapterArrayList.get(i).getLongitude());
            Marker marker = new Marker(mapView);
            marker.setPosition(point);
            marker.setSnippet(Integer.toString(i));
            if (getResources().getIdentifier(getIcon(i), "drawable", getPackageName()) != 0) {
                marker.setIcon(new BitmapDrawable(getResources(),
                        getResizedBitmap(getIcon(i), 200, 200)));
            } else {
                marker.setDefaultIcon();
            }


            if (checkArtefacts(i)) {
                stateHelper.updateStoryUnlocked(chapterArrayList.get(i).getName(), "true");
            }

            if (!checkTTL(i)) {
                if (stateHelper.getStoryUnlocked(chapterArrayList.get(i).getName()) && !stateHelper.getStorySolved(chapterArrayList.get(i).getName())) {
                    Log.d(TAG, "marker hinzugefügt " + chapterArrayList.get(i).getName());
                    marker.setOnMarkerClickListener(this);
                    drawCircle(point);
                    mapView.getOverlayManager().add(marker);
                    mapView.invalidate();
                }
            }
        }

        Log.d(TAG, "initMarkers: " + stateHelper.getBlub());
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
    public boolean onMarkerClick(Marker marker, MapView mapView) {
        if (marker.getSnippet() != null) {

            if(ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                return false;

            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                GeoPoint self = new GeoPoint(lat, lng);
                int chapterPosition = Integer.parseInt(marker.getSnippet());
                Chapter chapter = chapterArrayList.get(chapterPosition);
                GeoPoint current = new GeoPoint(chapter.getLatitude(), chapter.getLongitude());

                if (getDistance(self, current) <= GEOFENCE_RADIUS) {
                    homeButton.setVisibility(View.INVISIBLE);
                    if (chapterPosition == 17) {
                        Log.d(TAG, "onMarkerClick: die if clause funktioniert bei onmarkerclick");
                        SpecialChapter chapFrag = SpecialChapter.getInstance();
                        if (chapFrag.isChapterFinished()) {
                            return true;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.activity_map_root, chapFrag).addToBackStack("").commit();

                    } else {
                        if (!stateHelper.getStorySolved(chapter.getName())) {
                            ArrayList<Dialogue> dialogue = chapter.getDialogueArrayBefore();
                            Log.d(TAG, "dialog Map: " + dialogue.size());
                            // prüfen ob es ein Dialogue gibt.
                            if (dialogue != null) {
                                DialogueFragment fragment = DialogueFragment.newInstance(dialogue, 0, chapter);
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.activity_map_root, fragment).addToBackStack("").commit();
                            } else {
                                RiddleFragment fragment = RiddleFragment.newInstance(chapter);
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.activity_map_root, fragment).addToBackStack("").commit();
                            }
                        }
                    }
                } else {
                    if(stateHelper.getLanguage() == 0) {
                        Toast.makeText(this, "Bitte erst den Standort erreichen",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this,"Please reach the location first",
                                Toast.LENGTH_SHORT).show();
                    }

                    Log.d(TAG, "distance: " + getDistance(self, current));
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

    public double getDistance(GeoPoint pointA, GeoPoint pointB) {
        return pointA.distanceToAsDouble(pointB);
    }

    private void openLastActivity() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            finish();
            startActivity(getIntent());
            getSupportFragmentManager().popBackStackImmediate();
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void drawCircle(GeoPoint point) {
        List<GeoPoint> circle = Polygon.pointsAsCircle(point, GEOFENCE_RADIUS);
        Polygon polygon = new Polygon(mapView);
        polygon.setPoints(circle);
        polygon.getFillPaint().setARGB(128,74, 137, 243);
        polygon.getOutlinePaint().setARGB(255,74,137,243);
        polygon.getOutlinePaint().setStrokeWidth(5.0f);
        polygon.setInfoWindow(null);
        mapView.getOverlayManager().add(polygon);
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(stateHelper.getLanguage() == 0) { //deutsch
            builder.setMessage("Dein Standort scheint deaktiviert zu sein, möchtest Du es " +
                    "aktivieren?")
                    .setCancelable(false)
                    .setPositiveButton("Ja",
                            (dialog, id) -> {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        dialog.dismiss();
                            })
                    .setNegativeButton("Nein", (dialog, id) -> {dialog.dismiss();openLastActivity();});
            final AlertDialog alert = builder.create();
            alert.show();
        } else { //english
            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> startActivity(
                            new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                    .setNegativeButton("No", (dialog, id) -> {dialog.cancel();openLastActivity();});
            final AlertDialog alert = builder.create();
            alert.show();
        }


    }

    @Override
    public void onBackPressed() {
        openLastActivity();
        super.onBackPressed();
    }
}
