package pl.cieslak.bartosz.projects.android.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button ABOUT_BUTTON = findViewById(R.id.about);
        final Button BASIC_CALCULATOR_BUTTON = findViewById(R.id.basic_calculator);
        final Button ADVANCED_CALCULATOR_BUTTON = findViewById(R.id.advanced_calculator);

        ABOUT_BUTTON.setOnClickListener(view ->
        {
            Intent aboutActivity = new Intent(getApplicationContext(), About.class);
            startActivity(aboutActivity);
        });
    }
}