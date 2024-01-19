package pl.cieslak.bartosz.projects.android.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private Button aboutButton = null;
    private Button basicCalculatorButton = null;
    private Button advancedCalculatorButton = null;
    private Button exitButton = null;

    private void initButtons()
    {
        this.basicCalculatorButton = findViewById(R.id.basic_calculator);
        this.advancedCalculatorButton = findViewById(R.id.advanced_calculator);
        this.aboutButton = findViewById(R.id.about);
        this.exitButton = findViewById(R.id.exitButtonFromApp);

        this.basicCalculatorButton.setOnClickListener(view ->
        {
            Intent aboutActivity = new Intent(getApplicationContext(), BasicCalculator.class);
            startActivity(aboutActivity);
        });

        this.aboutButton.setOnClickListener(view ->
        {
            Intent aboutActivity = new Intent(getApplicationContext(), About.class);
            startActivity(aboutActivity);
        });

        this.exitButton.setOnClickListener(view -> finish());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButtons();
    }
}
