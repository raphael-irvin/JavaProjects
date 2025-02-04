package com.example.stockmarketsimulator;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Random;

public class MainController {
    private final Random random = new Random();

    private ArrayList<XYChart.Data<String, Number>> indexHistory = new ArrayList<>();

    private int currentDay = 1;
    private int currentPrice = 0;
    private String currentMarketSentiment;
    private double currentModifier = 1;
    private double marketVolatility = 0.1;

    double dailyMovementPercentage;
    double weeklyPriceMovementPercentage;

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

        currentPrice = (int)(random.nextDouble(900,1100));
        for (int i = 0; i<currentTimeFrame; i++) {
            generateMarketChanges(series);
        }

        mainChart.getData().add(series);
        updateMarketData(series);
    }


    public void nextDay() {
        XYChart.Series<String, Number> series = mainChart.getData().getFirst();

        //PRICE MOVEMENT SYSTEM
        generateMarketChanges(series);


        //LABELING
        //Market Data
        updateMarketData(series);


        //CHARTING
    }


    //MAIN MARKET SYSTEM
    public void generateMarketChanges(XYChart.Series<String, Number> series) {
        currentPrice = (int)(currentPrice*random.nextDouble(1-marketVolatility, 1 + marketVolatility)*currentModifier);
        XYChart.Data<String, Number> newData = new XYChart.Data<>("Day " + currentDay++, currentPrice);

        if (series.getData().size() >= currentTimeFrame) {
            series.getData().removeFirst();
        }
        series.getData().add(newData);
        registerData(newData);
    }


    //UPDATE ALL DATA AND LABELS RELATED TO MARKET
    public void updateMarketData(XYChart.Series<String, Number> series) {
        //Update Label
        currentPriceLabel.setText("Current Price: " + currentPrice);

        //Update Price Changes Percentage
        dailyMovementPercentage = (double) Math.round(((currentPrice / series.getData().get(series.getData().size() - 2).getYValue().doubleValue()) * 100 - 100) * 100) /100;
        dailyPriceMovementLabel.setText("Price Movement: " + dailyMovementPercentage + "%");

        weeklyPriceMovementPercentage = (double) Math.round(((currentPrice / series.getData().get(1).getYValue().doubleValue())*100 - 100)*100) / 100;
        weeklyPriceMovementLabel.setText("7-Day Price Movement: " + weeklyPriceMovementPercentage + "%");

        //Update Market Sentiment
        if (weeklyPriceMovementPercentage < -20) {
            currentMarketSentiment = "Selloff";
        } else if (weeklyPriceMovementPercentage <= -10) {
            currentMarketSentiment = "Fear";
        } else if (weeklyPriceMovementPercentage > 20) {
            currentMarketSentiment = "FOMO";
        } else if (weeklyPriceMovementPercentage >= 10) {
            currentMarketSentiment = "Greed";
        } else {
            currentMarketSentiment = "Neutral";
        }
        marketSentimentLabel.setText("Market Sentiment: " + currentMarketSentiment);
    }


    //ADD DATA TO MAXIMUM HISTORY
    public void registerData(XYChart.Data<String, Number> data) {
        if (indexHistory.size() > timeFrameHistoryLimit) {
            indexHistory.removeFirst();
            indexHistory.add(data);
        } else {
            indexHistory.add(data);
        }
    }

}
