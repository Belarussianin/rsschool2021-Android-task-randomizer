package com.rsschool.android2021;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements Shit {
    static final String MY_PREFERENCES = "preferences";
    static final String MY_PREFERENCES_AUTO_CLEAR = "autoClearOption";
    static final String MY_PREFERENCES_EQUALITY = "equalityOption";
    static final String MY_PREFERENCES_PREVIOUS_RESULT = "previousResult";
    static Boolean autoClearOption = false;
    static Boolean equalityPossible = true;
    private static String current_fragment;
    private static int previous;
    static int min;
    static int max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        autoClearOption = loadBooleanPreference(MY_PREFERENCES_AUTO_CLEAR);
        equalityPossible = loadBooleanPreference(MY_PREFERENCES_EQUALITY);
        previous = loadIntPreference(MY_PREFERENCES_PREVIOUS_RESULT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(getSupportFragmentManager());
    }

    @Override
    protected void onStop() {
        saveBooleanPreference(MY_PREFERENCES_AUTO_CLEAR, autoClearOption);
        saveIntPreference(MY_PREFERENCES_PREVIOUS_RESULT, previous);
        saveBooleanPreference(MY_PREFERENCES_EQUALITY, equalityPossible);
        super.onStop();
    }

    public void saveBooleanPreference(String key, Boolean value) {
        getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).edit().putBoolean(key, value).apply();
    }

    public void saveIntPreference(String key, int value) {
        getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).edit().putInt(key, value).apply();
    }

    public Boolean loadBooleanPreference(String key) {
        return getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getBoolean(key, true);
    }

    public int loadIntPreference(String key) {
        return getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getInt(key, 0);
    }

    @Override
    public void onGenerateRandom(int random) {
        MainActivity.previous = random;
    }

    @Override
    public void onGenerateButtonClick(int min, int max) {
        MainActivity.min = min;
        MainActivity.max = max;
    }

    public void openFirstFragment(@NotNull FragmentManager fragmentManager) {
        current_fragment = getResources().getString(R.string.first_fragment_tag);
        final Fragment firstFragment = FirstFragment.newInstance(previous);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, firstFragment);
        transaction.commit();
    }

    public void openSecondFragment(@NotNull FragmentManager fragmentManager) {
        current_fragment = getResources().getString(R.string.second_fragment_tag);
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, secondFragment);
        transaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (getResources().getString(R.string.second_fragment_tag).equals(current_fragment)) {
            openFirstFragment(getSupportFragmentManager());
        } else {
            super.onBackPressed();
        }
    }
}