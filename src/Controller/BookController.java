package Controller;

import Model.User;
import Model.book;
import Model.shoppingCartItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

public class BookController {

    @FXML
    private Button shoppingCart;
    @FXML
    private Label isbn;
    @FXML
    private Label userName;
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
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    private book book;


    public void initialize() {
        if (User.getUser() == null) {
            userName.setVisible(false);
            shoppingCart.setVisible(false);
            shoppingCart.setDisable(true);
            addToCart.setDisable(true);
        } else {
            userName.setText(User.getUser().getUserName());
//            if (!User.getUser().isPrivilege()) {
//                edit.setDisable(true);
//                edit.setVisible(false);
//            }
        }

    }


    public void initBookData(book book) {
        this.book = book;
        isbn.setText(String.valueOf(book.getIsbn()));
        title.setText(book.getTitle());
        publisher.setText(book.getpName());
        publicationYear.setText(book.getpYear());
        price.setText(String.valueOf(book.getPrice()));
        category.setText(book.getCategory());
        availableQuantity.setText(String.valueOf(book.getQuantity()));
    }

    public void addToCart() {
        User user = User.getUser();
        user.getShoppingCart().add(new shoppingCartItem(book.getIsbn(), Integer.parseInt(requestedQuantity.getText()), book.getPrice()));

    }


    public void editBook(ActionEvent actionEvent) {


    }

    public void goToShoppingCart(ActionEvent actionEvent) throws IOException {
        Navigate.goToPage("../View/ShoppingCart.fxml", actionEvent, getClass());

    }

    public void goToHome(ActionEvent actionEvent) throws IOException {

        Navigate.goToPage("../View/HomePageView.fxml", actionEvent, getClass());


    }
}


