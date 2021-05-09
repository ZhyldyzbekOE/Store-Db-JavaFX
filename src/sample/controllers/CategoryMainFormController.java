package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.models.Categories;
import sample.services.database.DatabaseConnection;

public class CategoryMainFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem exitMainForm;

    @FXML
    private MenuItem addCategoryButton;

    @FXML
    private MenuItem deleteCategoryButton;

    @FXML
    private Menu menuButton;

    @FXML
    private MenuItem editCategoryButton;

    @FXML
    private TableView<Categories> table;

    @FXML
    private TableColumn<Categories, Integer> id;

    @FXML
    private TableColumn<Categories, String> name;

    @FXML
    private TableColumn<Categories, Integer> active;

    // вывод категорий на экран в таблицу
    @FXML
    void initialize() {

        ObservableList<Categories> list1 = FXCollections.observableArrayList();

        int idDB, activeDB;
        String nameDB;
        try {
            Statement statement;
            DatabaseConnection databaseConnection = new DatabaseConnection();
            databaseConnection.databaseConnection();
            //statement = DatabaseConnection.connection.createStatement();
            statement = DatabaseConnection.connection.createStatement();
            String query = "SELECT id, name, active FROM categories";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                idDB = rs.getInt("id");
                nameDB = rs.getString("name");
                activeDB = rs.getInt("active");
                Categories categories = new Categories(idDB, nameDB, activeDB);
                list1.addAll(categories);

                // вывод категории в таблицу
                id.setCellValueFactory(new PropertyValueFactory<Categories, Integer>("id"));
                name.setCellValueFactory(new PropertyValueFactory<Categories, String>("name"));
                active.setCellValueFactory(new PropertyValueFactory<Categories, Integer>("active"));
                table.setItems(list1);
            }
//            DatabaseConnection.connection.close();
            databaseConnection.databaseClose();
        }catch (Exception e){
            e.printStackTrace();
        }

        // переход на окно добавления новой категории
        addCategoryButton.setOnAction(actionEvent -> {
            table.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/views/categoryAddForm.fxml"));
            try {
                loader.load();
            }catch (IOException e){
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            try {
                DatabaseConnection.connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        editCategoryButton.setOnAction(actionEvent -> {

        });

        exitMainForm.setOnAction(actionEvent -> {
            table.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/views/mainForm.fxml"));
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

