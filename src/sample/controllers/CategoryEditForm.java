package sample.controllers;

import java.io.IOException;
import java.net.URL;
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
import sample.services.database.DBservice;
import sample.services.database.DatabaseConnection;
import sample.services.findCategoryID.CategoryFindID;
import sample.services.findCategoryID.impl.CategoryFindIDImpl;

public class CategoryEditForm {

    private Categories categories;
    private  Stage stage;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField cotegoryNameEdit;

    @FXML
    private CheckBox activeCheckEdit;

    @FXML
    private Button saveEditNewCotegory;

    @FXML
    private Button exitEdit;

    @FXML
    void initialize() {

    }

    public void initDataToEditCategory(Stage stage, Categories categories){
        this.stage = stage;
        this.categories = categories;
        cotegoryNameEdit.setText(categories.getName());
        int act = categories.getActive();
        boolean isAct = act != 0;
        activeCheckEdit.setSelected(isAct);
        DBservice dBservice = new DatabaseConnection();
//        dBservice.databaseConnection();

        saveEditNewCotegory.setOnAction(actionEvent -> {
//            DBservice dBservice = new DatabaseConnection();
//            dBservice.databaseConnection();
            String newNameCategoryEdit = cotegoryNameEdit.getText().trim();
            if (newNameCategoryEdit.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Категория не может быть пустой!");
                alert.show();
                return;
            }
            int newActiveCategory;
            if (activeCheckEdit.isSelected()) {
                   newActiveCategory = 1;
            } else {
                   newActiveCategory = 0;
            }
            CategoryFindID categoryFindID = new CategoryFindIDImpl();
            int idEdit = categoryFindID.findID(newNameCategoryEdit);
            System.out.println("idEdit"+idEdit);
            Categories categories1 = new Categories(idEdit, newNameCategoryEdit, newActiveCategory);
            System.out.println("categories1"+categories1);
            Statement statement = null;
            try {
                statement = DatabaseConnection.connection.createStatement();
                String query  = "UPDATE categories SET name = '"+categories1.getName()+"', active = '"+categories1.getActive()+"' WHERE categories.id = '"+categories1.getId()+"'";
                statement.executeUpdate(query);
                Alert alert = new Alert(Alert.AlertType.WARNING, "Категория успешно обновлена!");
                alert.show();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    dBservice.databaseClose();
//                    statement.close();
                }catch (Exception e){
                    System.out.println("Edit form полетел11");
                }
            }
        });

        exitEdit.setOnAction(actionEvent -> {
            exitEdit.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/views/categoryMainForm.fxml"));
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
