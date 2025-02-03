package com.example.stockmarketsimulator;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Random;

public class MainController {
    private ArrayList<XYChart.Data<String, Number>> indexHistory = new ArrayList<>();

    private int currentDay = 1;
    private int currentPrice = 0;
    private String currentMarketSentiment;

    private int currentTimeFrame = 7;

    private int timeFrameHistoryLimit = 180;

    @FXML
    LineChart<String, Number> mainChart;
    @FXML
    Label dailyPriceMovementLabel;
    @FXML
    Label weeklyPriceMovementLabel;
    @FXML
    Label marketSentimentLabel;
    @FXML
    Label currentPriceLabel;

    public void initialize() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Stock Market Index");
        Random random = new Random();

        currentPrice = (int)(random.nextDouble(0.5, 1)*1000);
        for (int i = 0; i<currentTimeFrame; i++) {
            currentPrice = (int)(currentPrice*random.nextDouble(0.8,1.4));
            XYChart.Data<String, Number> newData = new XYChart.Data<>("Day " + currentDay++, currentPrice);
            series.getData().add(newData);
            registerData(newData);
        }

        mainChart.getData().add(series);
    }

    public void nextDay() {
        Random random = new Random();
        XYChart.Series<String, Number> series = mainChart.getData().getFirst();

        //PRICE MOVEMENT SYSTEM
        currentPrice = (int)(currentPrice*random.nextDouble(0.8, 1.2));
        XYChart.Data<String, Number> newData = new XYChart.Data<>("Day " + currentDay++, currentPrice);

        //LABELING
        //Movement Percentage
        double dailyMovementPercentage = (double) Math.round(((currentPrice / series.getData().getLast().getYValue().doubleValue()) * 100 - 100) * 100) /100;
        dailyPriceMovementLabel.setText("Price Movement: " + dailyMovementPercentage + "%");

        double weeklyPriceMovementPercentage = (double) Math.round(((currentPrice / series.getData().get(1).getYValue().doubleValue())*100 - 100)*100) / 100;
        weeklyPriceMovementLabel.setText("7-Day Price Movement: " + weeklyPriceMovementPercentage + "%");

        //Market Sentiment
        marketSentimentLabel.setText("Market Sentiment: Neutral");
        if (weeklyPriceMovementPercentage <= -10 && weeklyPriceMovementPercentage >= -20) {
            marketSentimentLabel.setText("Market Sentiment: Fear");
        } else if (weeklyPriceMovementPercentage < -20) {
            marketSentimentLabel.setText("Market Sentiment: Selloff");
        } else if (weeklyPriceMovementPercentage >= 10 && weeklyPriceMovementPercentage <= 20) {
            marketSentimentLabel.setText("Market Sentiment: Greed");
        } else if (weeklyPriceMovementPercentage > 20) {
            marketSentimentLabel.setText("Market Sentiment: FOMO");
        }

        //Current Price
        currentPriceLabel.setText("Current Price: " + currentPrice);

        //CHARTING
        if (series.getData().size() >= currentTimeFrame) {
            series.getData().removeFirst();
        }
        series.getData().add(newData);
        registerData(newData);
    }

    public void registerData(XYChart.Data<String, Number> data) {
        if (indexHistory.size() > timeFrameHistoryLimit) {
            indexHistory.removeFirst();
            indexHistory.add(data);
        } else {
            indexHistory.add(data);
        }
    }

}
