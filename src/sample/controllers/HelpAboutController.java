package sample.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelpAboutController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label textAbout;

    @FXML
    void initialize() {
        String text = "Для добавления новой катеогрии нажмите:\n *Редактировать -> Добавить категорию.\n\n" +
                "Для редактирование выбранной категории нажмите:\n *Редактировать -> Редактировать категорию.\n\n" +
                "Для выхода на главное меню нажмите:\n *Файл -> Выход";
        textAbout.setText(text);
    }
}
