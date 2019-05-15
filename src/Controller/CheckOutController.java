package Controller;

import Model.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class CheckOutController {

    @FXML
    private Label orderId;
    @FXML
    private Label userName;
    @FXML
    private Label totalPrice;
    @FXML
    private ComboBox<String> addresses;
    @FXML
    private TextField creditCard;
    @FXML
    private TextField cardCode;
    @FXML
    private DatePicker expDate;

    private int order;


    @FXML
    public void initialize() {
        User user = User.getUser();
        userName.setText(user.getUserName());
        addresses.setItems(FXCollections.observableArrayList(user.getShippingAddress()));
    }

    public void initData(int price, int order) {

        orderId.setText(String.valueOf(order));
        totalPrice.setText(String.valueOf(price));
        this.order = Integer.parseInt(orderId.getText());


    }

    public void goToshoppingCart(ActionEvent actionEvent) throws IOException {

        removeItemsFromDataBase(order);
        Navigate.goToPage("../View/ShoppingCart.fxml", actionEvent, getClass());

    }

    private void removeItemsFromDataBase(int order) {

        DbConnect connect = DbConnect.getInstance();
        String query = "{CALL removeFromCart(?)}";
        CallableStatement statement = null;
        try {
            statement = connect.getConnection().prepareCall(query);
            connect.getConnection().setAutoCommit(false);
            statement.setInt(1, order);
            statement.executeQuery();
            connect.getConnection().commit();
        } catch (SQLException e) {
            try {
                connect.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connect.getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void goHome(ActionEvent actionEvent) throws IOException {
        removeItemsFromDataBase(order);
        Navigate.goToPage("../View/HomePageView.fxml", actionEvent, getClass());

    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        removeItemsFromDataBase(order);
        User.getUser().logOut();
        Navigate.goToPage("../View/HomePageView.fxml", actionEvent, getClass());


    }

    public void submitOrder(ActionEvent actionEvent) throws IOException {

        User.getUser().emptyShoppingCart();
        Navigate.goToPage("../View/HomePageView.fxml", actionEvent, getClass());


    }
}
