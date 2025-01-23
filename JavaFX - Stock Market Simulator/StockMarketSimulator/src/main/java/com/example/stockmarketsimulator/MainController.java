package com.example.stockmarketsimulator;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Random;

public class MainController {

    private int currentDay = 1;
    private ArrayList<XYChart.Data<String, Number>> indexHistory = new ArrayList<>();

    @FXML
    LineChart<String, Number> mainChart;

    public void initialize() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Stock Market Index");

        for (int i = 0; i<4; i++) {
            Random random = new Random();

            int randomPrice = (int)(random.nextDouble()*1000);
            XYChart.Data<String, Number> newData = new XYChart.Data<>("Day " + currentDay++, randomPrice);
            series.getData().add(newData);
            indexHistory.add(newData);
        }

        mainChart.getData().add(series);
    }

    public void nextDay() {
        Random random = new Random();
        XYChart.Series<String, Number> series = mainChart.getData().getFirst();

        int randomPrice = (int)(random.nextDouble()*1000);
        XYChart.Data<String, Number> newData = new XYChart.Data<>("Day " + currentDay++, randomPrice);

        series.getData().removeFirst();
        series.getData().add(newData);
        indexHistory.add(newData);
    }

}
