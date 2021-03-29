package com.example.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SecondActivity extends AppCompatActivity {

    static final String STATE_FRAGMENT = "state_of_fragment";
    private Button mButton;
    private boolean isFragmentDisplayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        // initialize the button
        mButton = findViewById(R.id.open_button2);

        // restore the state if it is previously saved
        if (savedInstanceState != null) {
            isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);

            if (isFragmentDisplayed) {
                // if the fragment is displayed, change button to close
                mButton.setText("Close");
            }
        }

        // set the click listener for the button
        mButton.setOnClickListener(view -> {
            if (!isFragmentDisplayed) {
                displayFragment();
            } else {
                closeFragment();
            }
        });
    }

    private void displayFragment() {
        SimpleFragment simpleFragment = SimpleFragment.newInstance();

        // get the fragment manager and start a transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // add the simple fragment
        fragmentTransaction.add(R.id.fragment_container, simpleFragment).addToBackStack(null).commit();

        // update the button text
        mButton.setText("Close");

        // set boolean flag to indicate fragment is open
        isFragmentDisplayed = true;
    }

    private void closeFragment() {
        // get the fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // check to see if the fragment is already showing
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        if (simpleFragment != null) {
            // create and commit the transaction to remote the fragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }

        // update the button text
        mButton.setText("Open");

        // set boolean flag to indicate fragment is closed
        isFragmentDisplayed = false;
    }

    public void launchMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // save the state of the fragment
        outState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
    }
}