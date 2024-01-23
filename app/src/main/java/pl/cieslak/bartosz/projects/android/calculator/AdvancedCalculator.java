package pl.cieslak.bartosz.projects.android.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import pl.cieslak.bartosz.projects.android.calculator.enums.OperationType;
import pl.cieslak.bartosz.projects.android.calculator.exceptions.BadValueInCalculatorPanelException;

public class AdvancedCalculator extends BasicCalculator
{
    protected Button percentButton = null;
    protected Button sinButton = null;
    protected Button cosButton = null;
    protected Button tanButton = null;
    protected Button lnButton = null;
    protected Button sqrtButton = null;
    protected Button xPow2Button = null;
    protected Button xPowYButton = null;
    protected Button logButton = null;


    public void addAdvancedOperationTasks()
    {
        this.percentButton.setOnClickListener(view ->
        {
            try
            {
                validValueInCalculatorPanel();
            }
            catch(BadValueInCalculatorPanelException exception)
            {
                showToastWithInformation(exception.getMessage());
                return;
            }

            String currentValueInCalculatorPanel = this.calculatorPanel.getText().toString().trim();
            Double value = convertStringToDouble(currentValueInCalculatorPanel);

            if(value == null)
            {
                showToastWithInformation("Nieznany błąd!");
                return;
            }

            this.calculatorPanel.setText(getResultAsString(roundValue(value / 100)));
        });

        this.sinButton.setOnClickListener(view ->
        {
            try
            {
                validValueInCalculatorPanel();
            }
            catch(BadValueInCalculatorPanelException exception)
            {
                showToastWithInformation(exception.getMessage());
                return;
            }

            String currentValueInCalculatorPanel = this.calculatorPanel.getText().toString().trim();
            Double value = convertStringToDouble(currentValueInCalculatorPanel);

            if(value == null)
            {
                showToastWithInformation("Nieznany błąd!");
                return;
            }

            this.calculatorPanel.setText(getResultAsString(roundValue(Math.sin(value))));
        });

        this.cosButton.setOnClickListener(view ->
        {
            try
            {
                validValueInCalculatorPanel();
            }
            catch(BadValueInCalculatorPanelException exception)
            {
                showToastWithInformation(exception.getMessage());
                return;
            }

            String currentValueInCalculatorPanel = this.calculatorPanel.getText().toString().trim();
            Double value = convertStringToDouble(currentValueInCalculatorPanel);

            if(value == null)
            {
                showToastWithInformation("Nieznany błąd!");
                return;
            }

            this.calculatorPanel.setText(getResultAsString(roundValue(Math.cos(value))));
        });

        this.tanButton.setOnClickListener(view ->
        {
            try
            {
                validValueInCalculatorPanel();
            }
            catch(BadValueInCalculatorPanelException exception)
            {
                showToastWithInformation(exception.getMessage());
                return;
            }

            String currentValueInCalculatorPanel = this.calculatorPanel.getText().toString().trim();
            Double value = convertStringToDouble(currentValueInCalculatorPanel);

            if(value == null)
            {
                showToastWithInformation("Nieznany błąd!");
                return;
            }

            this.calculatorPanel.setText(getResultAsString(roundValue(Math.tan(value))));
        });

        this.sqrtButton.setOnClickListener(view ->
        {
            try
            {
                validValueInCalculatorPanel();
            }
            catch(BadValueInCalculatorPanelException exception)
            {
                showToastWithInformation(exception.getMessage());
                return;
            }

            String currentValueInCalculatorPanel = this.calculatorPanel.getText().toString().trim();
            Double value = convertStringToDouble(currentValueInCalculatorPanel);

            if(value == null)
            {
                showToastWithInformation("Nieznany błąd!");
                return;
            }

            this.calculatorPanel.setText(getResultAsString(roundValue(Math.sqrt(value))));
        });

        this.lnButton.setOnClickListener(view ->
        {
            try
            {
                validValueInCalculatorPanel();
            }
            catch(BadValueInCalculatorPanelException exception)
            {
                showToastWithInformation(exception.getMessage());
                return;
            }

            String currentValueInCalculatorPanel = this.calculatorPanel.getText().toString().trim();
            Double value = convertStringToDouble(currentValueInCalculatorPanel);

            if(value == null)
            {
                showToastWithInformation("Nieznany błąd!");
                return;
            }

            if(value <= 0)
            {
                showToastWithInformation("Wartość musi być dodatnia!");
                return;
            }

            this.calculatorPanel.setText(getResultAsString(roundValue(Math.log(value))));
        });

        this.logButton.setOnClickListener(view ->
        {
            try
            {
                validValueInCalculatorPanel();
            }
            catch(BadValueInCalculatorPanelException exception)
            {
                showToastWithInformation(exception.getMessage());
                return;
            }

            String currentValueInCalculatorPanel = this.calculatorPanel.getText().toString().trim();
            Double value = convertStringToDouble(currentValueInCalculatorPanel);

            if(value == null)
            {
                showToastWithInformation("Nieznany błąd!");
                return;
            }

            if(value <= 0)
            {
                showToastWithInformation("Wartość musi być dodatnia!");
                return;
            }

            this.calculatorPanel.setText(getResultAsString(roundValue(Math.log10(value))));
        });

        this.xPow2Button.setOnClickListener(view ->
        {
            try
            {
                validValueInCalculatorPanel();
            }
            catch(BadValueInCalculatorPanelException exception)
            {
                showToastWithInformation(exception.getMessage());
                return;
            }

            String currentValueInCalculatorPanel = this.calculatorPanel.getText().toString().trim();
            Double value = convertStringToDouble(currentValueInCalculatorPanel);

            if(value == null)
            {
                showToastWithInformation("Nieznany błąd!");
                return;
            }

            this.calculatorPanel.setText(getResultAsString(roundValue(Math.pow(value, 2))));
        });

        this.xPowYButton.setOnClickListener(view ->
        {
            try
            {
                validValueInCalculatorPanel();
            }
            catch(BadValueInCalculatorPanelException exception)
            {
                showToastWithInformation(exception.getMessage());
                return;
            }

            if(this.lastResult == null)
            {
                this.firstValue = convertStringToDouble(this.calculatorPanel.getText().toString());
            }
            else
            {
                this.firstValue = this.lastResult;
                this.secondValue = convertStringToDouble(this.calculatorPanel.getText().toString());
            }

            this.operationType = OperationType.POW;
            this.resultInCalculatorPanel = true;
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        savedInstanceState = savedInstanceState != null ? savedInstanceState : new Bundle();
        savedInstanceState.putBoolean("adv", true);
        super.onCreate(savedInstanceState);

        this.percentButton = findViewById(R.id.percentButton);
        this.sinButton = findViewById(R.id.sinButton);
        this.cosButton = findViewById(R.id.cosButton);
        this.tanButton = findViewById(R.id.tanButton);
        this.lnButton = findViewById(R.id.lnButton);
        this.logButton = findViewById(R.id.logButton);
        this.sqrtButton = findViewById(R.id.sqrtButton);
        this.xPow2Button = findViewById(R.id.xPow2Button);
        this.xPowYButton = findViewById(R.id.xPowYButton);

        addAdvancedOperationTasks();
    }
}
