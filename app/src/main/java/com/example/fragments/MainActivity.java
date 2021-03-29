package com.example.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    static final String STATE_FRAGMENT = "state_of_fragment";
    private Button mButton;
    private boolean isFragmentDisplayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.open_button);

        if (savedInstanceState != null) {
            isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);

            if (isFragmentDisplayed) {
                // if the fragment is displayed, change button to close
                mButton.setText(R.string.close);
            }
        }

        // set the click listener for the button
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFragmentDisplayed) {
                    displayFragment();
                } else {
                    closeFragment();
                }
            }
        });
    }

    public void displayFragment() {
        SimpleFragment simpleFragment = SimpleFragment.newInstance();

        // get the fragment manager and start a transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // add the simple fragment
        fragmentTransaction.add(R.id.fragment_container, simpleFragment).addToBackStack(null).commit();

        // update the button text
        mButton.setText(R.string.close);

        // set boolean flag to indicate fragment is open
        isFragmentDisplayed = true;
    }

    public void closeFragment() {
        // get the fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // check to see if the fragment is already showing
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        if (simpleFragment != null) {
            // create and commit the transaction to remove the fragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }

        // update the button text
        mButton.setText(R.string.open);

        // set boolean flag to indicate fragment is closed
        isFragmentDisplayed = false;

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // save the state of the fragment (true=open, false=close)
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
    }

    public void launchSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}