package com.example.xiaomin_final;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML private TextField tfAccountNumber;
    @FXML private TextField tfHydroUnits;

    @FXML private TextField tfSeason;
    @FXML private Label lblEstimateResult;

    @FXML private RadioButton rdbSummer;
    @FXML private RadioButton rdbWinter;
    @FXML private RadioButton rdbFall;
    //ToggleGroup object to group all the RadioButtons for book category
    @FXML private ToggleGroup groupCategory;

    @FXML private TableColumn<Bill, String> colAccountNumber;
    @FXML private TableColumn<Bill, Integer> colHydroUnits;
    @FXML private TableColumn<Bill, String> colSeason;
    @FXML private TableColumn<Bill, Double> colEstimateResult;
    @FXML private TableView<Bill> billTableView;

    DatabaseHelper dbHelper = DatabaseHelper.getInstance();

    @FXML
    protected void onGetEstimateButtonClick() {

        String accountNumber = "";
        int unitsOfHydroConsumption;
        String season;
        double estimatedAmount = 0;

        String estimateMessage = "";

        accountNumber = tfAccountNumber.getText();

        //1.identify the selected radio button
        RadioButton selectedRadioButton = (RadioButton)this.groupCategory.getSelectedToggle();
        //2.get the text of the selectedRadioButton
        season = selectedRadioButton.getText();
        System.out.println("Season: " + season);


        if (accountNumber.isEmpty() || tfHydroUnits.getText().isEmpty()
                || season.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setContentText("Please enter all information needed for estimation!");
            alert.setHeaderText(null);
            alert.show();
            return;
        }

        try {
            unitsOfHydroConsumption = Integer.parseInt(tfHydroUnits.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setContentText("Hydro consumption should be Integer type.");
            alert.setHeaderText(null);
            alert.show();
            return;
        }

        if (season.equalsIgnoreCase("Summer")) {
            estimatedAmount = 0.5 * unitsOfHydroConsumption;
        } else if (season.equalsIgnoreCase("Winter")) {
            estimatedAmount = 0.7 * unitsOfHydroConsumption;
        } else if (season.equalsIgnoreCase("Fall")) {
            estimatedAmount = 0.3 * unitsOfHydroConsumption;
        }

        estimateMessage = "Hydro bill: " + String.format("%.2f", estimatedAmount);;

        lblEstimateResult.setText(estimateMessage);

        Bill billToInsert = new Bill(accountNumber, unitsOfHydroConsumption, season, estimatedAmount);
        boolean success = this.dbHelper.insertToDB(billToInsert);
        if (success){
            this.billTableView.setItems(this.dbHelper.billList);
            this.billTableView.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Insert - Error");
            alert.setContentText("Record cannot be inserted to database");
            alert.setHeaderText(null);
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //create an instance of ToggleGroup
        this.groupCategory = new ToggleGroup();
        this.rdbSummer.setToggleGroup(this.groupCategory);
        this.rdbWinter.setToggleGroup(this.groupCategory);
        this.rdbFall.setToggleGroup(this.groupCategory);

        this.colAccountNumber = new TableColumn<>("Account Number");
        this.colAccountNumber.setCellValueFactory(new PropertyValueFactory<Bill, String>("accountNumber"));

        this.colHydroUnits = new TableColumn<>("Hydro Units");
        this.colHydroUnits.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("unitsOfHydroConsumption"));

        this.colSeason = new TableColumn<>("Season");
        this.colSeason.setCellValueFactory(new PropertyValueFactory<Bill, String>("season"));

        this.colEstimateResult = new TableColumn<>("Hydro bill estimate ($)");
        this.colEstimateResult.setCellValueFactory(new PropertyValueFactory<Bill, Double>("estimatedAmount"));

        //set columns to table
        this.billTableView.getColumns().addAll(this.colAccountNumber, this.colHydroUnits, this.colSeason, this.colEstimateResult);

        this.billTableView.setItems(this.dbHelper.billList);
        this.billTableView.refresh();
    }
}

