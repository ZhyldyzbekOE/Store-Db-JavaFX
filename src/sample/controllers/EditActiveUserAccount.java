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
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.models.Accounts;
import sample.services.database.DatabaseConnection;

public class EditActiveUserAccount {

    private Accounts accounts;

    private Stage stage;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField accountNameEdit;

    @FXML
    private CheckBox activeCheckEdit;

    @FXML
    private Button saveEditAccount;

    @FXML
    private Button exitEdit;

    @FXML
    private Label name;

    @FXML
    void initialize() {

    }

    public void initDataToEditCategory(Stage stage, Accounts accounts) {
        this.stage = stage;
        this.accounts = accounts;
        accountNameEdit.setText(accounts.getLogin());
        int isActive = accounts.getActive();
        boolean isAct = isActive != 0;
        activeCheckEdit.setSelected(isAct);
        String name1 = accounts.getName();
        name.setText(" "+name1);

        saveEditAccount.setOnAction(actionEvent -> {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            databaseConnection.databaseConnection();
            String newLogin = accountNameEdit.getText().trim();
            if (newLogin.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Логин не может быть пустым!");
                alert.show();
                return;
            }
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
                String query = "SELECT id FROM users WHERE users.name = '"+name1+"'";
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
            Accounts accounts1 = new Accounts(newLogin, newActiveAccount, id);
            System.out.println("update acc "+accounts1);
            Statement statement1 = null;
            try {
                statement1  = DatabaseConnection.connection.createStatement();
                String query = "UPDATE accounts SET login = '"+accounts1.getLogin()+"', active = '"+accounts1.getActive()+"' WHERE accounts.user_id = '"+id+"'";
                statement1.executeUpdate(query);
                Alert alert = new Alert(Alert.AlertType.WARNING, "Аккаунт успешно обновлён!");
                alert.show();
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
            Parent root = loader.getRoot();
            Stage stageq = new Stage();
            stageq.setScene(new Scene(root));
            stageq.show();
        });
    }
}

