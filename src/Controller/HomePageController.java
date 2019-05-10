package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;

public class HomePageController {

    @FXML
    private ChoiceBox<?> categories;

    @FXML
    private TextField isbn;

    @FXML
    private TextField publisherName;

    @FXML
    private TextField publicationYear;

    @FXML
    private TextField price;

    @FXML
    private TextField authorName;

    @FXML
    private ComboBox<?> authorsList;

    @FXML
    void addAuthor(ActionEvent event) {

    }

    @FXML
    void deleteAuthor(ActionEvent event) {

    }

}
