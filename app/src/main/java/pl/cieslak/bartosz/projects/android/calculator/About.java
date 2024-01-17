package pl.cieslak.bartosz.projects.android.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class About extends AppCompatActivity
{
    private void prepareTextAboutApplication(final TextView textView)
    {
        String textAboutApplication =  "Autor aplikacji: Bartosz Cieślak\n" +
                "Numer albumu: 236386\n\n" +
                "Aplikacja ma pełnić funkcję kalkulatora z podziałem na moduły: podstawowy i zaawansowany.";

        textView.setText(textAboutApplication);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        final TextView ABOUT_APPLICATIONS = findViewById(R.id.aboutApplication);
        final Button BACK_FROM_INFORMATION_TO_MAIN = findViewById(R.id.backFromInformationToMain);
        final TextView ABOUT_TITLE = findViewById(R.id.aboutTitle);

        ABOUT_TITLE.setText("Informacje o aplikacji");
        prepareTextAboutApplication(ABOUT_APPLICATIONS);
        BACK_FROM_INFORMATION_TO_MAIN.setOnClickListener(view -> finish());
    }
}