# Estimated Hydro Bill 
The JavaFX application allows the user to get the estimated hydro bill as per the following specifications.
![](/screenshots/screenshot2.JPG)

## Input and User Interface:
The user is allowed to enter the following details:
-	Account Number (String) (For example, TH1234)
-	Hydro consumption units (Integer) (For example, 230 kWh) 
-	Season (provide three options to select Summer, Winter or Fall)

## UI Controller:
All the UI related operations such as button click, data gathering and validation must be performed on the MainController class. All data must be validated to be not empty and with appropriate value. For example, Hydro consumption cannot accept any letters or alphabets.

## Code Organization:
A Bill class represents user’s bill information such as Account Number, Hydro Consumption Units, Season, Estimated Amount, etc.
A DatabaseHelper class performs all the database related operations.

## Calculation and Database Save Operation:
When user clicks on “Get Estimate” button, the app will:
-	Calculate hydro bill using the following calculation:
   - If the selected season is Summer, charge 0.50 cents per hydro unit consumed.
   - If the selected season is Winter, charge 0.70 cents per hydro unit consumed.
   - If the selected season is Fall, charge 0.30 cents per hydro unit consumed.
-	Show hydro bill on the screen as output in a Label.
-	Save the account number, hydro units, selected season and hydro bill estimate amount in the database table.
  ![](/screenshots/screenshot1.JPG)

