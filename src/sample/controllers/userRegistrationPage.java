package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.services.createNewAccountForUser.CreateNewAccount;
import sample.services.createNewAccountForUser.impl.CreateNewAccountImpl;
import sample.services.database.DBservice;
import sample.services.database.DatabaseConnection;
import sample.services.forInsertUserName.FindUserIdForAccount;
import sample.services.forInsertUserName.InsertNewUser;
import sample.services.forInsertUserName.impl.FindUserIdForAccountImpl;
import sample.services.forInsertUserName.impl.InsertNewUserImpl;
import sample.services.forUsersRegistration.CheckLengthLogin;
import sample.services.forUsersRegistration.CorrectUserRegistration;
import sample.services.forUsersRegistration.impl.CheckLengthLoginImpl;
import sample.services.forUsersRegistration.impl.LoginUserFieldReg;
import sample.services.forUsersRegistration.impl.NameUserFieldReg;
import sample.services.forUsersRegistration.impl.PasswordUserFieldReg;

public class userRegistrationPage {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField signUpUserName;

    @FXML
    private TextField signUpUserLogin;

    @FXML
    private TextField signUpUserPassword;

    @FXML
    private Button newUserRegistration;

    @FXML
    private Button exitMune;

    // регистрация нового пользователя
    @FXML
    void initialize() {
        DBservice dBservice = new DatabaseConnection();
        dBservice.databaseConnection();
        newUserRegistration.setOnAction(actionEvent -> {


            CorrectUserRegistration nameUserFieldReg = new NameUserFieldReg();
            CorrectUserRegistration loginUserFieldReg = new LoginUserFieldReg();
            CorrectUserRegistration passUserFieldReg = new PasswordUserFieldReg();

            CheckLengthLogin checkLengthLogin = new CheckLengthLoginImpl();

            InsertNewUser insertNewUser = new InsertNewUserImpl();
            FindUserIdForAccount findUserIdForAccount = new FindUserIdForAccountImpl();

            String userLogin = signUpUserLogin.getText().trim();
            boolean userLoginButton = loginUserFieldReg.userRegistration(userLogin);
            boolean lengthLogin = checkLengthLogin.checkLengthUserLogin(userLogin); //проверка на длину логина

            String userPassword = signUpUserPassword.getText().trim();
            boolean userPasswordButton = passUserFieldReg.userRegistration(userPassword);

            String userName = signUpUserName.getText().trim(); // имя с пришло с front'a
            boolean userNameButton = nameUserFieldReg.userRegistration(userName); // проверка на длину имени

            if (userLoginButton && lengthLogin && userPasswordButton && userNameButton){
                insertNewUser.createNewUserAndInsert(userName);
            //if (userNameButton ){ // длина логина > 5 и пароль
               // insertNewUser.createNewUserAndInsert(userName); // вот тут сделать insert,
            //}
            int idUser = findUserIdForAccount.findIdUser(userName); // получили id user'a
            //if (userLoginButton && userPasswordButton){
                CreateNewAccount createNewAccount = new CreateNewAccountImpl();
                if (createNewAccount.createAccount(idUser,userLogin, userPassword)){
                    newUserRegistration.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/sample/views/sample.fxml"));
                    try{
                        loader.load();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                }
//                else {
//                    Alert alert = new Alert(Alert.AlertType.WARNING,"Аккаунт не создан!");
//                    alert.show();
//                }
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING,"Аккаунт не создан!");
                alert.show();
            }
            //}
        });
        exitMune.setOnAction(actionEvent -> {
            exitMune.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/views/sample.fxml"));
            try{
                loader.load();
            }catch (IOException e){
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
    }
}

