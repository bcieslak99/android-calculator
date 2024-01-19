package pl.cieslak.bartosz.projects.android.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pl.cieslak.bartosz.projects.android.calculator.enums.OperationType;

public class BasicCalculator extends AppCompatActivity
{
    private static final char DOT = '.';
    private static final char MINUS = '-';
    private static final char ZERO = '0';
    private final int QUANTITY_OF_BUTTONS_WITH_VALUES = 10;
    private TextView calculatorPanel = null;
    private Button[] valueButtons = new Button[QUANTITY_OF_BUTTONS_WITH_VALUES];
    private Button clearAllButton = null;
    private Button clearButton = null;
    private Button changeSignButton = null;
    private Button divideButton = null;
    private Button multiplyButton = null;
    private Button subtractButton = null;
    private Button addButton = null;
    private Button dotButton = null;
    private Button equalButton = null;
    private Button backToMainButton = null;
    private String valueInPanel = null;
    private Double result = null;
    private OperationType operationType = null;

    public void prepareButtonHandlers()
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

    private void deactivateOperationsButtons()
    {
        this.addButton.setEnabled(false);
        this.subtractButton.setEnabled(false);
        this.multiplyButton.setEnabled(false);
        this.divideButton.setEnabled(false);
    }

    private void activateOperationsButtons()
    {
        this.addButton.setEnabled(true);
        this.subtractButton.setEnabled(true);
        this.multiplyButton.setEnabled(true);
        this.divideButton.setEnabled(true);
    }

    private void deactivateValueButtons()
    {
        for(Button button : this.valueButtons)
            button.setEnabled(false);

        this.dotButton.setEnabled(false);
        this.changeSignButton.setEnabled(false);
    }

    private void activateValueButtons()
    {
        for(Button button : this.valueButtons)
            button.setEnabled(true);

        this.dotButton.setEnabled(true);
        this.changeSignButton.setEnabled(true);
    }

    private void setDefaultButtonsSettings()
    {
        this.addButton.setEnabled(true);
        this.subtractButton.setEnabled(true);
        this.multiplyButton.setEnabled(true);
        this.divideButton.setEnabled(true);
        this.equalButton.setEnabled(false);

        for(Button button : this.valueButtons)
            button.setEnabled(true);

        this.dotButton.setEnabled(true);
        this.changeSignButton.setEnabled(true);
    }

    private void addExitTask()
    {
        this.backToMainButton.setOnClickListener(view -> finish());
    }

    public double roundValue(double value)
    {
        final int N = 10000000;
        return (double) Math.round(value * N) / N;
    }

    public String getResultAsString()
    {
        if(result == null) return "";

        LinkedList<Character> characters = new LinkedList<>();

        double tmpResult = roundValue(this.result);

        for(Character character : Double.toString(tmpResult).toCharArray())
            characters.addLast(character);

        if (characters.size() > 0 && characters.getLast().equals(ZERO))
            characters.removeLast();

        if(characters.getLast().equals(DOT)) characters.removeLast();

        StringBuilder stringBuilder = new StringBuilder();
        characters.forEach(stringBuilder::append);

        return stringBuilder.toString();
    }

    private void setValueInCalculatorPanel(String value)
    {
        this.valueInPanel = value;
        this.calculatorPanel.setText(value);
    }

    private void addClearTask()
    {
        this.clearAllButton.setOnClickListener(view ->
        {
            setValueInCalculatorPanel(null);
            this.result = null;
            setDefaultButtonsSettings();
        });

        this.clearButton.setOnClickListener(view ->
        {
            if(this.valueInPanel != null && !this.valueInPanel.trim().isEmpty())
            {
                if(this.result != null) this.calculatorPanel.setText(getResultAsString());
                else this.calculatorPanel.setText(null);
                valueInPanel = null;
            }
            else
            {
                this.result = null;
                this.calculatorPanel.setText(null);
                setDefaultButtonsSettings();
            }
        });
    }

    private void showToastWithInformation(String message)
    {
        if(message == null || message.trim().isEmpty()) return;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void addValueTasks()
    {
        for(int i = 0; i < this.valueButtons.length; i++)
        {
            final int VALUE_OF_BUTTON = i;
            this.valueButtons[i].setOnClickListener(view ->
            {
                String newValue = this.valueInPanel;

                if(newValue == null || newValue.trim().isEmpty()) newValue = Integer.toString(VALUE_OF_BUTTON);
                else
                {
                    if(!newValue.trim().isEmpty() && newValue.startsWith(String.valueOf(ZERO)) && VALUE_OF_BUTTON == 0) return;
                    newValue += Integer.toString(VALUE_OF_BUTTON);
                }

                setValueInCalculatorPanel(newValue);
            });
        }

        this.dotButton.setOnClickListener(view ->
        {
            final String NO_INTEGER_MESSAGE = "Nie wprowadzono części całkowitej!";
            final String DOT_EXISTS_MESSAGE = "Separator został już wprowadzony!";

            if(this.valueInPanel == null || this.valueInPanel.trim().isEmpty())
                showToastWithInformation(NO_INTEGER_MESSAGE);
            else if(this.valueInPanel.contains(String.valueOf(DOT))) showToastWithInformation(DOT_EXISTS_MESSAGE);
            else
            {
                String newValue = this.valueInPanel + DOT;
                setValueInCalculatorPanel(newValue);
            }
        });

        this.changeSignButton.setOnClickListener(view ->
        {
            final String NO_VALUE_MESSAGE = "Wprawadź wartość aby zmienić znak!";
            final String VALUE_ENDS_WITH_DOT = "Wartość nie może kończyć się kropką!";
            final String ZERO_WITH_MINUS_MESSAGE = "Nie można ustawić minusa dla zera!";

            if(this.valueInPanel == null || this.valueInPanel.trim().isEmpty())
                showToastWithInformation(NO_VALUE_MESSAGE);
            else if(this.valueInPanel.endsWith(String.valueOf(DOT))) showToastWithInformation(VALUE_ENDS_WITH_DOT);
            else if(this.valueInPanel.equals(String.valueOf(ZERO))) showToastWithInformation(ZERO_WITH_MINUS_MESSAGE);
            else
            {
                if(valueInPanel.startsWith(String.valueOf(MINUS))) setValueInCalculatorPanel(this.valueInPanel.substring(1));
                else setValueInCalculatorPanel(MINUS + this.valueInPanel);
            }
        });
    }

    private void addOperationTasks()
    {
        final String VALUE_ENDS_WITH_DOT_MESSAGE = "Wartość nie może kończyć się separatorem!";
        final String NO_VALUE_MESSAGE = "Nie została jeszcze wprowadzona wartość!";
        final String CONVERSION_FROM_STRING_TO_DOUBLE_ERROR_MESSAGE = "Nie udało się przetworzyć danych!";
        final String DIVIDE_BY_ZERO_MESSAGE = "Nie można dzielić przez zero!";

        this.addButton.setOnClickListener(view ->
        {
            if((this.valueInPanel == null || this.valueInPanel.trim().isEmpty()) && this.result == null)
            {
                showToastWithInformation(NO_VALUE_MESSAGE);
                return;
            }
            else if(this.valueInPanel != null && this.valueInPanel.endsWith(String.valueOf(DOT)))
            {
                showToastWithInformation(VALUE_ENDS_WITH_DOT_MESSAGE);
                return;
            }

            if(result != null)
            {
                this.operationType = OperationType.ADD;
                this.equalButton.setEnabled(true);
                deactivateOperationsButtons();
                activateValueButtons();
                return;
            }

            Double tmpResult = null;

            try
            {
                if(this.valueInPanel == null) throw new Exception(CONVERSION_FROM_STRING_TO_DOUBLE_ERROR_MESSAGE);
                tmpResult = Double.valueOf(this.valueInPanel);
            }
            catch (Exception exception)
            {
                showToastWithInformation(CONVERSION_FROM_STRING_TO_DOUBLE_ERROR_MESSAGE);
                return;
            }

            this.operationType = OperationType.ADD;
            if(this.result == null)
            {
                this.result = tmpResult;
                this.valueInPanel = null;
                this.equalButton.setEnabled(true);
                deactivateOperationsButtons();
                this.equalButton.setEnabled(true);
            }
        });

        this.subtractButton.setOnClickListener(view ->
        {
            if((this.valueInPanel == null || this.valueInPanel.trim().isEmpty()) && this.result == null)
            {
                showToastWithInformation(NO_VALUE_MESSAGE);
                return;
            }
            else if(this.valueInPanel != null && this.valueInPanel.endsWith(String.valueOf(DOT)))
            {
                showToastWithInformation(VALUE_ENDS_WITH_DOT_MESSAGE);
                return;
            }

            if(result != null)
            {
                this.operationType = OperationType.SUBTRACT;
                this.equalButton.setEnabled(true);
                deactivateOperationsButtons();
                activateValueButtons();
                return;
            }

            Double tmpResult = null;

            try
            {
                if(this.valueInPanel == null) throw new Exception(CONVERSION_FROM_STRING_TO_DOUBLE_ERROR_MESSAGE);
                tmpResult = Double.valueOf(this.valueInPanel);
            }
            catch (Exception exception)
            {
                showToastWithInformation(CONVERSION_FROM_STRING_TO_DOUBLE_ERROR_MESSAGE);
                return;
            }

            this.operationType = OperationType.SUBTRACT;
            if(this.result == null)
            {
                this.result = tmpResult;
                this.valueInPanel = null;
                this.equalButton.setEnabled(true);
                deactivateOperationsButtons();
                this.equalButton.setEnabled(true);
            }
        });

        this.multiplyButton.setOnClickListener(view ->
        {
            if((this.valueInPanel == null || this.valueInPanel.trim().isEmpty()) && this.result == null)
            {
                showToastWithInformation(NO_VALUE_MESSAGE);
                return;
            }
            else if(this.valueInPanel != null && this.valueInPanel.endsWith(String.valueOf(DOT)))
            {
                showToastWithInformation(VALUE_ENDS_WITH_DOT_MESSAGE);
                return;
            }

            if(result != null)
            {
                this.operationType = OperationType.MULTIPLY;
                this.equalButton.setEnabled(true);
                deactivateOperationsButtons();
                activateValueButtons();
                return;
            }

            Double tmpResult = null;

            try
            {
                if(this.valueInPanel == null) throw new Exception(CONVERSION_FROM_STRING_TO_DOUBLE_ERROR_MESSAGE);
                tmpResult = Double.valueOf(this.valueInPanel);
            }
            catch (Exception exception)
            {
                showToastWithInformation(CONVERSION_FROM_STRING_TO_DOUBLE_ERROR_MESSAGE);
                return;
            }

            this.operationType = OperationType.MULTIPLY;
            if(this.result == null)
            {
                this.result = tmpResult;
                this.valueInPanel = null;
                this.equalButton.setEnabled(true);
                deactivateOperationsButtons();
                this.equalButton.setEnabled(true);
            }
        });

        this.divideButton.setOnClickListener(view ->
        {
            if((this.valueInPanel == null || this.valueInPanel.trim().isEmpty()) && this.result == null)
            {
                showToastWithInformation(NO_VALUE_MESSAGE);
                return;
            }
            else if(this.valueInPanel != null && this.valueInPanel.endsWith(String.valueOf(DOT)))
            {
                showToastWithInformation(VALUE_ENDS_WITH_DOT_MESSAGE);
                return;
            }

            if(result != null)
            {
                this.operationType = OperationType.DIVIDE;
                this.equalButton.setEnabled(true);
                deactivateOperationsButtons();
                activateValueButtons();
                return;
            }

            Double tmpResult = null;

            try
            {
                if(this.valueInPanel == null) throw new Exception(CONVERSION_FROM_STRING_TO_DOUBLE_ERROR_MESSAGE);
                tmpResult = Double.valueOf(this.valueInPanel);
            }
            catch (Exception exception)
            {
                showToastWithInformation(CONVERSION_FROM_STRING_TO_DOUBLE_ERROR_MESSAGE);
                return;
            }

            this.operationType = OperationType.DIVIDE;
            if(this.result == null)
            {
                this.result = tmpResult;
                this.valueInPanel = null;
                this.equalButton.setEnabled(true);
                deactivateOperationsButtons();
                this.equalButton.setEnabled(true);
            }
        });

        this.equalButton.setOnClickListener(view ->
        {
            if(this.valueInPanel == null || this.valueInPanel.trim().isEmpty())
            {
                showToastWithInformation(NO_VALUE_MESSAGE);
                return;
            }
            else if(this.valueInPanel.endsWith(String.valueOf(DOT)))
            {
                showToastWithInformation(VALUE_ENDS_WITH_DOT_MESSAGE);
                return;
            }

            Double tmpValue = null;

            try
            {
                tmpValue = Double.valueOf(this.valueInPanel);
            }
            catch(Exception exception)
            {
                showToastWithInformation(CONVERSION_FROM_STRING_TO_DOUBLE_ERROR_MESSAGE);
                return;
            }

            switch (this.operationType)
            {
                case ADD:
                    this.result = roundValue(this.result + tmpValue);
                    break;
                case SUBTRACT:
                    this.result = roundValue(this.result - tmpValue);
                    break;
                case MULTIPLY:
                    this.result = roundValue(this.result * tmpValue);
                    break;
                case DIVIDE:
                    if(tmpValue == 0)
                    {
                        showToastWithInformation(DIVIDE_BY_ZERO_MESSAGE);
                        return;
                    }
                    this.result = roundValue(this.result / tmpValue);
                    break;
            }

            this.valueInPanel = null;
            deactivateValueButtons();
            activateOperationsButtons();
            this.equalButton.setEnabled(false);
            this.calculatorPanel.setText(Double.toString(this.result));
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
        setDefaultButtonsSettings();
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
            this.valueInPanel = savedInstanceState.getString("valueInPanel");
            this.calculatorPanel.setText(savedInstanceState.getString("calculatorPanel"));
            this.result = savedInstanceState.getDouble("result");
            this.operationType = (OperationType) savedInstanceState.getSerializable("operationType");
            this.clearAllButton.setEnabled(savedInstanceState.getBoolean("clearAllButton"));
            this.clearButton.setEnabled(savedInstanceState.getBoolean("clearButton"));
            this.changeSignButton.setEnabled(savedInstanceState.getBoolean("changeSignButton"));
            this.divideButton.setEnabled(savedInstanceState.getBoolean("divideButton"));
            this.multiplyButton.setEnabled(savedInstanceState.getBoolean("multiplyButton"));
            this.subtractButton.setEnabled(savedInstanceState.getBoolean("subtractButton"));
            this.addButton.setEnabled(savedInstanceState.getBoolean("addButton"));
            this.dotButton.setEnabled(savedInstanceState.getBoolean("dotButton"));
            this.equalButton.setEnabled(savedInstanceState.getBoolean("equalButton"));

            for(int i = 0; i < this.valueButtons.length; i++)
                this.valueButtons[i].setEnabled(savedInstanceState.getBoolean("value" + i + "Button"));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("valueInPanel", this.valueInPanel);
        outState.putString("calculatorPanel", (String) this.calculatorPanel.getText());
        if(result != null) outState.putDouble("result", this.result);
        outState.putSerializable("operationType", this.operationType);
        outState.putBoolean("clearAllButton", this.clearAllButton.isEnabled());
        outState.putBoolean("clearButton", this.clearButton.isEnabled());
        outState.putBoolean("changeSignButton", this.changeSignButton.isEnabled());
        outState.putBoolean("divideButton", this.divideButton.isEnabled());
        outState.putBoolean("multiplyButton", this.multiplyButton.isEnabled());
        outState.putBoolean("subtractButton", this.subtractButton.isEnabled());
        outState.putBoolean("addButton", this.addButton.isEnabled());
        outState.putBoolean("dotButton", this.dotButton.isEnabled());
        outState.putBoolean("equalButton", this.equalButton.isEnabled());

        for(int i = 0; i < this.valueButtons.length; i++)
            outState.putBoolean("value" + i + "Button", this.valueButtons[i].isEnabled());
    }
}