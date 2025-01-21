package com.example.simplecalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CalculatorInterface {

    @FXML
    Label mainDisplay;

    private String currentOperator = "";
    private double currentResult;
    private boolean newInput = true;

    public void handleNumber(ActionEvent event) {
        Button numberButtonPressed = (Button)event.getSource();
        String number = numberButtonPressed.getText();

        if (newInput) {
            mainDisplay.setText(number);
            newInput = false;
        } else {
            mainDisplay.setText(mainDisplay.getText() + number);
        }
    }

    public void handleOperator(ActionEvent event) {
        Button operatorButtonPressed = (Button)event.getSource();
        String operator = operatorButtonPressed.getText();

        if (!newInput) {
            calculateIntermediate();
        }

        currentOperator = operator;
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

        mainDisplay.setText(String.valueOf(currentResult));
    }
}
