package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Categories;
import sample.models.Users;

import sample.services.database.DBservice;
import sample.services.database.DatabaseConnection;

public class CategoryAddController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField cotegoryName;

    @FXML
    private CheckBox activeCheck;

    @FXML
    private Button saveNewCotegory;

    @FXML
    private Button exit;

    // добавление новой категории
    @FXML
    void initialize() throws SQLException {

        saveNewCotegory.setOnAction(actionEvent -> {
            DBservice dBservice = new DatabaseConnection();
            dBservice.databaseConnection();
            String newNameCategory = cotegoryName.getText().trim();
            System.out.println(newNameCategory);
            int newActiveCategory;
            if (activeCheck.isSelected()){
                newActiveCategory = 1;
            }else {
                newActiveCategory = 0;
            }
            System.out.println(newActiveCategory);
            try {
                Categories categories = new Categories(newNameCategory, newActiveCategory);
                System.out.println(categories);
                String query = "insert into categories(name, active) VALUES('"+categories.getName()+"', '"+categories.getActive()+"')";
                Statement statement = DatabaseConnection.connection.createStatement();
                statement.executeUpdate(query);
                Alert alert = new Alert(Alert.AlertType.WARNING,"Категория успешно создана!");
                alert.show();
                dBservice.databaseClose();
                return;
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        exit.setOnAction(actionEvent -> {
            exit.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/views/categoryMainForm.fxml"));
            try {
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
