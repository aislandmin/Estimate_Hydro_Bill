package com.example.xiaomin_final;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseHelper {
    private static DatabaseHelper _dbHelper;
    private Connection dbConnection;
    private final String TABLE_NAME = "Xiaomin_hydro";
    private PreparedStatement insertStatement;
    public ObservableList<Bill> billList;

    public static DatabaseHelper getInstance() {
        if (_dbHelper == null){
            _dbHelper = new DatabaseHelper();
        }

        return _dbHelper;
    }

    private DatabaseHelper() {
        this.connectToDB();
    }

    public void connectToDB() {
        //host for campus connections
//        String host = "jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD";
        //host for non-campus connections (home)
        String host = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";

        String username = "COMP228_F24_jig_26";
        String password = "password";

        try {
            //1. register the JDBC driver for oracle
            Class.forName("oracle.jdbc.OracleDriver");

            //2. obtain the connection object using driver and connection info
            this.dbConnection = DriverManager.getConnection(host, username, password);
            if (this.dbConnection == null) {
                System.out.println("cannot establish database connection");
            } else {
                System.out.println("Database connection established successfully");
                //create necessary tables
                this.createTable();
                //create any prepared statements
                String insertQuery = "INSERT INTO " + TABLE_NAME + " VALUES(?, ?, ?, ?)";
                System.out.println("Insert Query : " + insertQuery);
                this.insertStatement = this.dbConnection.prepareStatement(insertQuery);
                this.getAllRecords();
            }
        }catch(SQLException ex) {
            System.out.println("SQL Exception while connecting to database : " + ex);
        }catch(Exception ex) {
            System.out.println("Something went wrong while connecting to database : " + ex);
        }
    }

    private void createTable() {
        try {
            //DatabaseMetaData provides information about database such as tables, structures, etc.
            DatabaseMetaData databaseMetaData = this.dbConnection.getMetaData();
            //get the tables from database
            ResultSet resultSet =
                    databaseMetaData.getTables(null, null, TABLE_NAME, null);
            //check if table already exists in database
            if (resultSet.next()) {
                System.out.println("Database already has table with given name. "
                        + "cannot create duplicate table");
                this.getAllRecords();
            } else {
                System.out.println("creating a table " + TABLE_NAME);

                String accountNumber = "";
                int unitsOfHydroConsumption;
                String season;
                double estimatedAmount = 0;

                String createQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                        "AccountNumber VARCHAR2(20), " +
                        "HydroConsumptionUnits NUMBER(5), " +
                        "Season VARCHAR2(20)," +
                        "EstimateAmount NUMBER(5, 2)" +
                        ")";
                System.out.println("Create table sql : " + createQuery);

                if (!this.dbConnection.isClosed()) {
                    //obtain instance of statement to execute SQL statement
                    Statement statement = this.dbConnection.createStatement();
                    //use appropriate execute() function to run the query
                    int n = statement.executeUpdate(createQuery);
                    //n will be 0 for 1 table created
                    System.out.println("Table created : " + n);

                    if (n < 0) {
                        System.out.println("Could not create table " + TABLE_NAME);
                    } else {
                        System.out.println("Table created : " + TABLE_NAME);
                    }

                    if (!statement.isClosed()) {
                        statement.close();
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Cannot create database table : " + ex);
        }
    }

    public boolean insertToDB(Bill billToInsert) {
        try {
            if (!this.dbConnection.isClosed()) {
                //save the amount and number of persons in the database table
                //set the values to prepared statement
                this.insertStatement.setString(1, billToInsert.getAccountNumber());
                this.insertStatement.setInt(2, billToInsert.getUnitsOfHydroConsumption());
                this.insertStatement.setString(3, billToInsert.getSeason());
                this.insertStatement.setDouble(4, billToInsert.getEstimatedAmount());

                //execute the statement
                int n = insertStatement.executeUpdate();
                if (n > 0) {
                    System.out.println(n + " records inserted to " + TABLE_NAME);
                    this.getAllRecords();
                    return true;
                } else {
                    System.out.println("No records inserted to table");
                }
            } else {
                System.out.println("Cannot insert. Database connection is closed.");
            }
        } catch (SQLException ex) {
            System.out.println("Cannot insert into database : " + ex);
        }
        return false;
    }
    public void getAllRecords() {
        try {
            if (!this.dbConnection.isClosed()) {
                Statement statement = this.dbConnection.createStatement();
                String selectQuery = "SELECT * FROM " + TABLE_NAME;
                ResultSet resultSet = statement.executeQuery(selectQuery);
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columnCount = resultSetMetaData.getColumnCount();
                this.billList = FXCollections.observableArrayList();

                //process all the records; 1 at a time
                while (resultSet.next()) {
                    String output = "";
                    for (int i = 1; i <= columnCount; i++) {
                        output += resultSetMetaData.getColumnName(i) + " : "
                                + resultSet.getString(i) + ", ";
                    }

                    System.out.println("getAllRecords output : " + output);

                    //only the amount and number of persons are saved in the database table
                    Bill bill = new Bill(
                            resultSet.getString(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getDouble(4)
                    );

                    this.billList.add(bill);
                }

                System.out.println("splitList : " + this.billList);

                if (!statement.isClosed()) {
                    statement.close();
                }
            }else {
                System.out.println("Database connection is closed. Cannot retrieve records");
            }
        }catch(SQLException ex){
            System.out.println("Could not retrieve records " + ex);
        }
    }

    public void closeConnections() {
        try {
            if (this.insertStatement != null && !this.insertStatement.isClosed()) {
                this.insertStatement.close();
            }

            if (!this.dbConnection.isClosed()) {
                this.dbConnection.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
