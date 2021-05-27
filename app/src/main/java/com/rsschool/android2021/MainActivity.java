package com.rsschool.android2021;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MainActivityInterface {
    private static String current_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(0, getSupportFragmentManager());
    }

    @Override
    public void openFirstFragment(int previousNumber, FragmentManager fragmentManager) {
        current_fragment = "first";
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, firstFragment);
        transaction.commit();
    }

    @Override
    public void openSecondFragment(int min, int max, FragmentManager fragmentManager) {
        current_fragment = "second";
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, secondFragment, "Second");
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        switch (current_fragment) {
            case "first": {
                super.onBackPressed();
                break;
            }
            case "second": {
                openFirstFragment(SecondFragment.getRandomNum(), getSupportFragmentManager());
                break;
            }
        }
    }
}