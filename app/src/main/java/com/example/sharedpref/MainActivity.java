package com.example.sharedpref;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    // Current count
    private int mCount = 0;
    // Current background color
    private int mColor;
    // Text view to display both count and color
    private TextView mShowCountTextView;

    // Key for current count
    private final String COUNT_KEY = "count";
    // Key for current color
    private final String COLOR_KEY = "color";

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.hellosharedprefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views, color
        mShowCountTextView = findViewById(R.id.count_textview);
        mColor = ContextCompat.getColor(this, R.color.default_background);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        mCount = mPreferences.getInt(COUNT_KEY, 0);
        mShowCountTextView.setText(String.format("%s", mCount));

        mColor = mPreferences.getInt(COLOR_KEY, mColor);
        mShowCountTextView.setBackgroundColor(mColor);

        // Restore the saved instance state.
        if (savedInstanceState != null) {

            mCount = savedInstanceState.getInt(COUNT_KEY);
            if (mCount != 0) {
                mShowCountTextView.setText(String.format("%s", mCount));
            }

            mColor = savedInstanceState.getInt(COLOR_KEY);
            mShowCountTextView.setBackgroundColor(mColor);
        }
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
//        preferencesEditor.putInt(COUNT_KEY, mCount);
//        preferencesEditor.putInt(COLOR_KEY, mColor);
//        preferencesEditor.apply();
//
//    }

    public void changeBackground(View view) {
        int color = ((ColorDrawable) view.getBackground()).getColor();
        mShowCountTextView.setBackgroundColor(color);
        mColor = color;
    }

    public void countUp(View view) {
        mCount++;
        mShowCountTextView.setText(String.format("%s", mCount));
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putInt(COUNT_KEY, mCount);
//        outState.putInt(COLOR_KEY, mColor);
//    }

    public void reset() {
        // Reset count
        mCount = 0;
        mShowCountTextView.setText(String.format("%s", mCount));

        // Reset color
        mColor = ContextCompat.getColor(this,
                R.color.default_background);
        mShowCountTextView.setBackgroundColor(mColor);

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.apply();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case(R.id.action_save):
                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                preferencesEditor.putInt(COUNT_KEY, mCount);
                preferencesEditor.putInt(COLOR_KEY, mColor);
                preferencesEditor.apply();
                Toast.makeText(this, "Instance is saved", Toast.LENGTH_SHORT).show();
                return true;
            case (R.id.action_reset):
                reset();
                Toast.makeText(this, "Instance is reset", Toast.LENGTH_SHORT).show();
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }
}