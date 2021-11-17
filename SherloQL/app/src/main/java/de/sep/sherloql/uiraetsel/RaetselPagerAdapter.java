package de.sep.sherloql.uiraetsel;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


/**
 * Hier wird die Klasse gebraucht, um durch die einzigen
 * Raetseln  Seitenansichten zu verwalten.
 *
 * ,
 */
public  class RaetselPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "RaetselPagerAdapter";
    public ArrayList<Raetsel> raetselArrayList;

    /**
     * Konstruktor von Klasse RaetselPagerAdapter
     * @param supportFragmentManager
     * @param raetselArrayList
     */
    public RaetselPagerAdapter(FragmentManager supportFragmentManager, ArrayList<Raetsel> raetselArrayList) {
        super(supportFragmentManager);
        this.raetselArrayList = raetselArrayList;
    }

    @Override
    /**
     * gibt fragment zurück.
     */
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem: starts    MAIN FRAGMENT");
        return RaetselMainFragment.newInstance(raetselArrayList.get(position), position);
    }

    @Override
    /**
     * gibt die Länge des ArrayLists zurück.
     */
    public int getCount() {
        return raetselArrayList.size();
    }

    @Override
    /**
     * gibt Titel des Raetsels zurück
     */
    public CharSequence getPageTitle(int position) {
        return raetselArrayList.get(position).getName();
    }
    /**
     * gibt Raetsel Typ zurück
     * @param position
     * @return typ als String
     */
    public String getRaetselType(int position) {
        return raetselArrayList.get(position).getType();
    }
}