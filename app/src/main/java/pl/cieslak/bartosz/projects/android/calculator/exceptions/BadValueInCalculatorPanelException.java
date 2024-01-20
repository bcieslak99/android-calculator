package pl.cieslak.bartosz.projects.android.calculator.exceptions;

public class BadValueInCalculatorPanelException extends Exception
{
    private BadValueInCalculatorPanelException() {}

    public BadValueInCalculatorPanelException(String message)
    {
        super(message);
    }
}
