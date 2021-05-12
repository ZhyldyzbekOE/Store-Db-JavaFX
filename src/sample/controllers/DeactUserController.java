package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.models.Accounts;
import sample.services.database.DatabaseConnection;

public class DeactUserController {

    private Accounts accounts;

    private Stage stage;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox activeCheckEdit;

    @FXML
    private Button saveEditAccountDeact;

    @FXML
    private Button exitEdit;

    @FXML
    private Label loginDeact;

    @FXML
    private Label nameDeact;

    @FXML
    void initialize() {


    }

    public void initDataToEditCategory(Stage stage, Accounts accounts) {
        this.stage = stage;
        this.accounts = accounts;
        String logDeactAcc = accounts.getLogin();
        loginDeact.setText(" "+logDeactAcc);
        String nameUserDeact = accounts.getName();
        nameDeact.setText("  "+nameUserDeact);
        int isActive = accounts.getActive();
        boolean isAct = isActive != 0;
        activeCheckEdit.setSelected(isAct);

        saveEditAccountDeact.setOnAction(actionEvent -> {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            databaseConnection.databaseConnection();
            int newActiveAccount;
            if (activeCheckEdit.isSelected()) {
                newActiveAccount = 1;
            } else {
                newActiveAccount = 0;
            }
            int id = 0;
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                statement = DatabaseConnection.connection.createStatement();
                String query = "SELECT id FROM users WHERE users.name = '"+nameUserDeact+"'";
                resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    id = resultSet.getInt("id");
                }
                System.out.println(id + "для редактирования");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
//                System.out.println("Во туту");
//                dBservice.databaseClose();
//                    resultSet.close();
//                    statement.close();
                }catch (Exception e){
                    System.out.println("Поиск id не прошел");
                }
            }
            Accounts accounts1 = new Accounts(logDeactAcc, newActiveAccount, id);
            System.out.println("update acc "+accounts1);
            Statement statement1 = null;
            try {
                statement1  = DatabaseConnection.connection.createStatement();
                String query = "UPDATE accounts SET active = '"+accounts1.getActive()+"' WHERE accounts.login = '"+logDeactAcc+"'";
                statement1.executeUpdate(query);
                if (newActiveAccount == 1){
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Аккаунт успешно активирован!");
                    alert.show();
                }
                if (newActiveAccount == 0 ){
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Аккаунт успешно деактивирован!");
                    alert.show();
                }
                return;
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    statement1.close();
                    databaseConnection.databaseClose();
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println("update не прошел");
                }
            }
        });

        exitEdit.setOnAction(actionEvent -> {
            exitEdit.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/views/outputUserAndLogin.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent parent = loader.getRoot();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(parent));
            stage1.show();
        });
    }
}

