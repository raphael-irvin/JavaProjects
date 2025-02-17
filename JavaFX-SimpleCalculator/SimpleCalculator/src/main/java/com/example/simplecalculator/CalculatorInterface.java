package com.example.simplecalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CalculatorInterface {

    @FXML
    Label mainDisplay;
    @FXML
    Label resultDisplay;
    @FXML
    Label operatorDisplay;

    private String currentOperator = "";
    private double currentResult;
    private boolean newInput = true;

    public void handleNumber(ActionEvent event) {
        String number = ((Button)event.getSource()).getText();

        if (newInput) {
            mainDisplay.setText(number);
            newInput = false;
        } else {
            mainDisplay.setText(mainDisplay.getText() + number);
        }
    }

    public void handleOperator(ActionEvent event) {
        String operator = ((Button)event.getSource()).getText();

        if (!newInput) {
            calculateIntermediate();
            mainDisplay.setText("");
        }

        currentOperator = operator;
        operatorDisplay.setText(operator);
        newInput = true;
    }

    public void calculate(ActionEvent event) {
        if (!newInput) {
            calculateIntermediate();
            currentOperator = "";
        }
    }

    public void clear(ActionEvent event) {
        currentResult = 0;
        currentOperator = "";
        mainDisplay.setText("");
        resultDisplay.setText("");
        newInput = true;
    }

    public void calculateIntermediate() {
        double displayedNumber = Double.parseDouble(mainDisplay.getText());

        switch (currentOperator) {
            case "+":
                currentResult += displayedNumber;
                break;
            case "-":
                currentResult -= displayedNumber;
                break;
            case "*":
                currentResult *= displayedNumber;
                break;
            case "/":
                currentResult /= displayedNumber;
                break;
            default:
                currentResult = displayedNumber;
                break;
        }

        resultDisplay.setText(String.valueOf(currentResult));
        operatorDisplay.setText("");
    }
}
