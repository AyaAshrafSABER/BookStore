package Controller;

import Model.User;
import Model.book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BookController {

    @FXML
    private Label isbn;
    @FXML
    private Label title;
    @FXML
    private Label publisher;
    @FXML
    private Label publicationYear;
    @FXML
    private Label price;
    @FXML
    private Label category;
    @FXML
    private Label availableQuantity;
    @FXML
    private TextField requestedQuantity;
    @FXML
    private Button addToCart;
    @FXML
    private Button edit;

    public void initialize() {
        if (!User.getUser().isPrivilege()) {
            edit.setDisable(true);
            edit.setVisible(false);
        }
    }

    public void initBookData(book book) {
        isbn.setText(book.getBookId());
        title.setText(book.getTitle());
        publisher.setText(book.getpName());
        publicationYear.setText(book.getpYear());
        price.setText(String.valueOf(book.getPrice()));
        category.setText(book.getCategory());
        availableQuantity.setText(String.valueOf(book.getQuantity()));
    }

    public void addToCart() {
        User user = User.getUser();
        user.getShoppingCart().addToCart(isbn.getText(), Integer.parseInt(requestedQuantity.getText()));

    }


    public void editBook(ActionEvent actionEvent) {
    }
}
