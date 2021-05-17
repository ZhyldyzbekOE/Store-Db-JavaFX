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

        outPut();
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
//        deactUser.setOnAction(actionEvent -> {
//            Accounts accounts = table.getSelectionModel().getSelectedItem();
//            if (accounts == null){
//                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Чтобы деактивировать, выберете пользователя!");
//                alert.show();
//                return;
//            }
//            table.getScene().getWindow().hide();
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/sample/views/deactUser.fxml"));
//            try {
//                loader.load();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//            DeactUserController editActiveUserAccountD = loader.getController();
//            Parent parent = loader.getRoot();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(parent));
//            editActiveUserAccountD.initDataToEditCategory(stage, accounts);
//            stage.show();
//        });

        deactUser.setOnAction(actionEvent -> {
            Accounts accounts = table.getSelectionModel().getSelectedItem();
            if (accounts == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Чтобы Де/Активировать, выберете пользователя!");
                alert.show();
                return;
            }
            DatabaseConnection databaseConnection = new DatabaseConnection();
            databaseConnection.databaseConnection();
            int changeActive;
            String userName = accounts.getName();
            System.out.println(userName);
            String userLogin = accounts.getLogin();
            System.out.println(userLogin);
            int activeUser = accounts.getActive();
            System.out.println(activeUser);
            if (activeUser == 1){
                changeActive = 0;
                System.out.println("изменил "+changeActive);
                int id = selectIdUser(userName);
                Accounts accounts1 = new Accounts(userLogin, changeActive, id);
                System.out.println("update acc "+accounts1);
                if (updateActive(accounts1)){
                    if (accounts1.getActive() == 0 ){
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Аккаунт успешно деактивирован!");
                        alert.show();
                        alert.setOnCloseRequest(windowEvent -> {
                            outPut();
                            table.refresh();
                        });
                    }
                }else {
                    System.out.println("update вернул false");
                }
            }
            if (activeUser == 0){
                changeActive = 1;
                System.out.println("изменил "+changeActive);
                int id = selectIdUser(userName);
                Accounts accounts1 = new Accounts(userLogin, changeActive, id);
                System.out.println("update accAct "+accounts1);
                if (updateActive(accounts1)){
                    if (accounts1.getActive() == 1){
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Аккаунт успешно активирован!");
                        alert.show();
                        alert.setOnCloseRequest(windowEvent -> {
                            outPut();
                            table.refresh();
                        });
                    }
                }
                else {
                    System.out.println("update вернул false");
                }
            }
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

    }
    public void outPut(){
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

    public int selectIdUser(String userName){
        Statement statement = null;
        ResultSet resultSet = null;
        int id = 0;
        try {
            statement = DatabaseConnection.connection.createStatement();
            String query = "SELECT id FROM users WHERE users.name = '"+userName+"'";
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
        return id;
    }

    public boolean updateActive(Accounts accounts){
        System.out.println(accounts);
        Statement statement1 = null;
        try {
            statement1  = DatabaseConnection.connection.createStatement();
            String query = "UPDATE accounts SET active = '"+accounts.getActive()+"' WHERE accounts.login = '"+accounts.getLogin()+"'";
            statement1.executeUpdate(query);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                statement1.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("update не прошел");
            }
        }
        return false;
    }
}

