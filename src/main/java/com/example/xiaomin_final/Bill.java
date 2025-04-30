package com.example.xiaomin_final;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Bill {
    public SimpleStringProperty accountNumber = new SimpleStringProperty();
    public SimpleIntegerProperty unitsOfHydroConsumption = new SimpleIntegerProperty();
    public SimpleStringProperty season = new SimpleStringProperty();
    public SimpleDoubleProperty estimatedAmount = new SimpleDoubleProperty();

    public Bill(String accountNumber, int unitsOfHydroConsumption, String season, Double estimatedAmount) {
        this.accountNumber.set(accountNumber);
        this.unitsOfHydroConsumption.set(unitsOfHydroConsumption);
        this.season.set(season);
        this.estimatedAmount.set(estimatedAmount);
    }

    public String getAccountNumber() {
        return accountNumber.get();
    }

    public SimpleStringProperty accountNumberProperty() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber.set(accountNumber);
    }

    public int getUnitsOfHydroConsumption() {
        return unitsOfHydroConsumption.get();
    }

    public SimpleIntegerProperty unitsOfHydroConsumptionProperty() {
        return unitsOfHydroConsumption;
    }

    public void setUnitsOfHydroConsumption(int unitsOfHydroConsumption) {
        this.unitsOfHydroConsumption.set(unitsOfHydroConsumption);
    }

    public String getSeason() {
        return season.get();
    }

    public SimpleStringProperty seasonProperty() {
        return season;
    }

    public void setSeason(String season) {
        this.season.set(season);
    }

    public double getEstimatedAmount() {
        return estimatedAmount.get();
    }

    public SimpleDoubleProperty estimatedAmountProperty() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(double estimatedAmount) {
        this.estimatedAmount.set(estimatedAmount);
    }
}
