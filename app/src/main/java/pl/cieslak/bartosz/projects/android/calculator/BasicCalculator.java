package pl.cieslak.bartosz.projects.android.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import pl.cieslak.bartosz.projects.android.calculator.enums.OperationType;
import pl.cieslak.bartosz.projects.android.calculator.exceptions.BadValueInCalculatorPanelException;

public class BasicCalculator extends AppCompatActivity
{
    protected static final char DOT = '.';
    protected static final char MINUS = '-';
    protected static final char ZERO = '0';
    protected final int QUANTITY_OF_BUTTONS_WITH_VALUES = 10;
    protected Button[] valueButtons = new Button[QUANTITY_OF_BUTTONS_WITH_VALUES];
    protected Button clearAllButton = null;
    protected Button clearButton = null;
    protected Button changeSignButton = null;
    protected Button divideButton = null;
    protected Button multiplyButton = null;
    protected Button subtractButton = null;
    protected Button addButton = null;
    protected Button dotButton = null;
    protected Button equalButton = null;
    protected Button backToMainButton = null;
    protected TextView calculatorPanel = null;
    protected boolean resultInCalculatorPanel = false;
    protected Double firstValue = null;
    protected Double secondValue = null;
    protected OperationType operationType = null;
    protected Double lastResult = null;

    private void prepareButtonHandlers()
    {
        this.calculatorPanel = findViewById(R.id.displayData);
        this.valueButtons[0] = findViewById(R.id.value0Button);
        this.valueButtons[1] = findViewById(R.id.value1Button);
        this.valueButtons[2] = findViewById(R.id.value2Button);
        this.valueButtons[3] = findViewById(R.id.value3Button);
        this.valueButtons[4] = findViewById(R.id.value4Button);
        this.valueButtons[5] = findViewById(R.id.value5Button);
        this.valueButtons[6] = findViewById(R.id.value6Button);
        this.valueButtons[7] = findViewById(R.id.value7Button);
        this.valueButtons[8] = findViewById(R.id.value8Button);
        this.valueButtons[9] = findViewById(R.id.value9Button);
        this.clearButton = findViewById(R.id.clearButton);
        this.clearAllButton = findViewById(R.id.clearAllButton);
        this.changeSignButton = findViewById(R.id.changeSign);
        this.divideButton = findViewById(R.id.divideButton);
        this.multiplyButton = findViewById(R.id.multiplyButton);
        this.subtractButton = findViewById(R.id.subtractButton);
        this.addButton = findViewById(R.id.addButton);
        this.dotButton = findViewById(R.id.dotButton);
        this.equalButton = findViewById(R.id.equalsButton);
        this.backToMainButton = findViewById(R.id.backFromBasicCalculatorToMain);
    }

    protected double roundValue(double value)
    {
        final int N = 10000000;
        return (double) Math.round(value * N) / N;
    }

    protected String getResultAsString(Double value)
    {
        if(value == null) return "";

        LinkedList<Character> characters = new LinkedList<>();

        double tmpResult = roundValue(value);

        for(Character character : Double.toString(tmpResult).toCharArray())
            characters.addLast(character);

        if(characters.size() > 0 && characters.getLast().equals(ZERO))
            characters.removeLast();

        if(characters.getLast().equals(DOT)) characters.removeLast();

        StringBuilder stringBuilder = new StringBuilder();
        characters.forEach(stringBuilder::append);

        return stringBuilder.toString();
    }

    protected Double convertStringToDouble(String value)
    {
        Double result = null;

        try
        {
            result = Double.valueOf(value);
        }
        catch(Exception exception)
        {
            return null;
        }

        return result;
    }

    protected void showToastWithInformation(String message)
    {
        if(message == null || message.trim().isEmpty()) return;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void addExitTask()
    {
        this.backToMainButton.setOnClickListener(view -> finish());
    }

    protected void addClearTask()
    {
        this.clearAllButton.setOnClickListener(view ->
        {
            this.calculatorPanel.setText("");
            this.firstValue = null;
            this.secondValue = null;
            this.operationType = null;
            this.lastResult = null;
            this.resultInCalculatorPanel = false;
        });

        this.clearButton.setOnClickListener(view ->
        {
            if(resultInCalculatorPanel)
            {
                this.calculatorPanel.setText("");
                this.firstValue = null;
                this.secondValue = null;
                this.operationType = null;
                this.lastResult = null;
                this.resultInCalculatorPanel = false;
            }
            if(lastResult != null)
            {
                this.firstValue = this.lastResult;
                this.lastResult = null;
                this.operationType = null;
                this.secondValue = null;
                this.calculatorPanel.setText(getResultAsString(this.firstValue));
            }
        });
    }

    protected void addValueTasks()
    {
        for(int i = 0; i < this.valueButtons.length; i++)
        {
            final int VALUE_OF_BUTTON = i;

            this.valueButtons[i].setOnClickListener(view ->
            {
                if(this.resultInCalculatorPanel)
                {
                    this.calculatorPanel.setText("");
                    this.resultInCalculatorPanel = false;
                }

                String currentValue = this.calculatorPanel.getText().toString().trim();
                StringBuilder stringBuilder = new StringBuilder();

                if(VALUE_OF_BUTTON == 0)
                {
                    if(currentValue.isEmpty()) stringBuilder.append(ZERO).append(DOT);
                    else stringBuilder.append(currentValue).append(ZERO);
                }
                else stringBuilder.append(currentValue).append(VALUE_OF_BUTTON);

                this.calculatorPanel.setText(stringBuilder.toString());
            });
        }

        this.dotButton.setOnClickListener(view ->
        {
            if(this.resultInCalculatorPanel)
            {
                showToastWithInformation("Nie można dodać separatora do wyniku!");
                return;
            }

            String currentValue = this.calculatorPanel.getText().toString().trim();

            if(currentValue.isEmpty())
            {
                showToastWithInformation("Ta operacja nie może zostać wykonana!");
                return;
            }

            if(currentValue.contains(String.valueOf(DOT)))
            {
                showToastWithInformation("Separator został już dodany!");
                return;
            }

            String result = currentValue + DOT;
            this.calculatorPanel.setText(result);
        });

        this.changeSignButton.setOnClickListener(view ->
        {
            if(this.resultInCalculatorPanel)
            {
                showToastWithInformation("Nie można zmienić znaku dla wyniku!");
                return;
            }

            String currentValue = this.calculatorPanel.getText().toString().trim();

            if(currentValue.isEmpty())
            {
                showToastWithInformation("Nie wprowadzono jeszcze wartości!");
                return;
            }

            if(currentValue.endsWith(String.valueOf(DOT)))
            {
                showToastWithInformation("Wartość nie może kończyć się separatorem!");
                return;
            }

            Double tmpValue = convertStringToDouble(currentValue);

            if((tmpValue == null || tmpValue.equals(0.0D)) && !currentValue.startsWith(String.valueOf(MINUS)))
            {
                showToastWithInformation("Nie można zmienić znaku dla zera!");
                return;
            }

            StringBuilder stringBuilder = new StringBuilder();

            if(currentValue.startsWith(String.valueOf(MINUS))) stringBuilder.append(currentValue.substring(1));
            else stringBuilder.append(MINUS).append(currentValue);

            this.calculatorPanel.setText(stringBuilder);
        });
    }

    protected void validValueInCalculatorPanel() throws BadValueInCalculatorPanelException
    {
        String currentValueInCalculatorPanel = this.calculatorPanel.getText().toString().trim();

        if(currentValueInCalculatorPanel.isEmpty())
            throw new BadValueInCalculatorPanelException("Wartość nie może być pusta!");

        if(currentValueInCalculatorPanel.endsWith(String.valueOf(DOT)))
            throw new BadValueInCalculatorPanelException("Wartość nie może kończyć się separatorem");

        Double value = convertStringToDouble(currentValueInCalculatorPanel);
        if(value == null) throw new BadValueInCalculatorPanelException("Nieznany błąd!");
    }

    protected void addOperationTasks()
    {
        this.addButton.setOnClickListener(view ->
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


            this.operationType = OperationType.ADD;
            this.resultInCalculatorPanel = true;
        });

        this.subtractButton.setOnClickListener(view ->
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

            this.operationType = OperationType.SUBTRACT;
            this.resultInCalculatorPanel = true;
        });

        this.multiplyButton.setOnClickListener(view ->
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

            this.operationType = OperationType.MULTIPLY;
            this.resultInCalculatorPanel = true;
        });

        this.divideButton.setOnClickListener(view ->
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

            this.operationType = OperationType.DIVIDE;
            this.resultInCalculatorPanel = true;
        });

        this.equalButton.setOnClickListener(view ->
        {
            if(this.firstValue == null)
            {
                showToastWithInformation("Nie można wykonać teraz tej operacji!");
                return;
            }

            try
            {
                validValueInCalculatorPanel();
            }
            catch(BadValueInCalculatorPanelException exception)
            {
                showToastWithInformation(exception.getMessage());
                return;
            }

            if(this.secondValue == null)
            {
                this.secondValue = this.firstValue;
                this.firstValue = convertStringToDouble(this.calculatorPanel.getText().toString());
            }
            else
            {
                this.firstValue = convertStringToDouble(this.calculatorPanel.getText().toString());
            }

            if(this.operationType == OperationType.DIVIDE && firstValue.equals(0.0D))
            {
                showToastWithInformation("Nie można dzielić przez zero!");
                return;
            }

            double tmpValue = 0.0D;

            switch(this.operationType)
            {
                case ADD:
                    tmpValue = this.secondValue + this.firstValue;
                    break;
                case SUBTRACT:
                    tmpValue = this.secondValue - this.firstValue;
                    break;
                case MULTIPLY:
                    tmpValue = this.secondValue * this.firstValue;
                    break;
                case DIVIDE:
                    tmpValue = this.secondValue / this.firstValue;
                    break;
            }

            this.lastResult = roundValue(tmpValue);
            this.calculatorPanel.setText(getResultAsString(this.lastResult));
            this.resultInCalculatorPanel = true;
        });
    }

    private void addTasksToButtons()
    {
        addExitTask();
        addClearTask();
        addValueTasks();
        addOperationTasks();
    }

    private void initButtons()
    {
        prepareButtonHandlers();
        addTasksToButtons();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_calculator);
        initButtons();

        if(savedInstanceState != null)
        {
            this.calculatorPanel.setText(savedInstanceState.getString("calculatorPanel"));
            this.firstValue = savedInstanceState.getDouble("firstValue");
            this.secondValue = savedInstanceState.getDouble("secondValue");
            this.lastResult = savedInstanceState.getDouble("lastResult");
            this.operationType = (OperationType) savedInstanceState.getSerializable("operationType");
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("calculatorPanel", this.calculatorPanel.getText().toString());
        if(this.firstValue != null) outState.putDouble("firstValue", this.firstValue);
        if(this.secondValue != null) outState.putDouble("secondValue", this.secondValue);
        if(this.lastResult != null) outState.putDouble("lastResult", this.lastResult);
        outState.putSerializable("operationType", this.operationType);
    }
}