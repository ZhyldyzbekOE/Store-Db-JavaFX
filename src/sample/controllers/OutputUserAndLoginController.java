package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.models.Accounts;
import sample.models.Users;
import sample.services.database.DatabaseConnection;

public class OutputUserAndLoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem exitButton;

    @FXML
    private MenuItem editUser;

    @FXML
    private MenuItem deactUser;

    @FXML
    private TableView<Accounts> table;

    @FXML
    private TableColumn<Accounts, String> name;

    @FXML
    private TableColumn<Accounts, String> login;

    @FXML
    private TableColumn<Accounts, Integer> active;

    @FXML
    void initialize() {

        exitButton.setOnAction(actionEvent -> {
            table.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/views/mainForm.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        });
        deactUser.setOnAction(actionEvent -> {
            Accounts accounts = table.getSelectionModel().getSelectedItem();
            if (accounts == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Чтобы деактивировать, выберете пользователя!");
                alert.show();
                return;
            }
            table.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/views/deactUser.fxml"));
            try {
                loader.load();
            }catch (IOException e){
                e.printStackTrace();
            }
            DeactUserController editActiveUserAccountD = loader.getController();
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            editActiveUserAccountD.initDataToEditCategory(stage, accounts);
            stage.show();
        });
        editUser.setOnAction(actionEvent -> {
            Accounts accounts = table.getSelectionModel().getSelectedItem();
            if (accounts == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Чтобы отредактивировать, выберете пользователя!");
                alert.show();
                return;
            }
            table.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/views/editActiveUserAccount.fxml"));
            try {
                loader.load();
            }catch (IOException e){
                e.printStackTrace();
            }
            EditActiveUserAccount editActiveUserAccount = loader.getController();
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            editActiveUserAccount.initDataToEditCategory(stage, accounts);
            stage.show();
        });
        ObservableList<Accounts> accountsObservableList = FXCollections.observableArrayList();
        Statement statement = null;
        ResultSet rs = null;
        int activeAcc;
        String nameUs, accUs;
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            databaseConnection.databaseConnection();
            statement = DatabaseConnection.connection.createStatement();
            String query = "SELECT name, login, active FROM accounts INNER JOIN users " +
                    "on users.id = accounts.user_id";
            rs = statement.executeQuery(query);
            while (rs.next()){
                activeAcc = rs.getInt("active");
                nameUs = rs.getString("name");
                accUs = rs.getString("login");
                Users users = new Users(nameUs);
                Accounts accounts = new Accounts(accUs, users.getName(), activeAcc);
                accountsObservableList.addAll(accounts);
            }
            name.setCellValueFactory(new PropertyValueFactory<Accounts, String>("name"));
            login.setCellValueFactory(new PropertyValueFactory<Accounts, String>("login"));
            active.setCellValueFactory(new PropertyValueFactory<Accounts, Integer>("active"));
            table.setItems(accountsObservableList);
            table.setRowFactory(t -> new TableRow<>(){
                protected void updateItem(Accounts accounts, boolean e){
                    super.updateItem(accounts, e);
                    if (accounts!= null && accounts.getActive() != 1){
                        setStyle("-fx-background-color: #ff9999;");
                    }else {
                        setStyle("");
                    }
                }
            });
            databaseConnection.databaseClose();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                rs.close();
            }catch (Exception e){
            }
        }
    }
}

