package Controller;

import Model.User;
import Model.bookOrder;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConfirmOrdersController {


    @FXML
    private TableView<bookOrder> bookOrders;
    @FXML
    private TableColumn<bookOrder, String> orderIdCol;
    @FXML
    private TableColumn<bookOrder, String> bookIdCol;
    @FXML
    private TableColumn<bookOrder, String> quantityCol;
    @FXML
    private Label userName;

    private DbConnect connect = DbConnect.getInstance();

    private List<bookOrder> list;


    @FXML
    public void initialize() {

        userName.setText(User.getUser().getUserName());
        showOrders();

    }

    private void showOrders() {
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        bookIdCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        bookOrders.getColumns().setAll(orderIdCol, bookIdCol, quantityCol);
        list = new ArrayList<>();
        String query = "{CALL getBookOrders()}";
        try {
            CallableStatement statement = connect.getConnection().prepareCall(query);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                bookOrder bookOrder = new bookOrder(set.getInt("orderId"),
                        set.getInt("ISBN"), set.getInt("numberOfBooks"));
                list.add(bookOrder);
            }
            bookOrders.setItems(FXCollections.observableArrayList(list));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void goToShoppingCart(ActionEvent actionEvent) throws IOException {

        Navigate.goToPage("../View/ShoppingCart.fxml", actionEvent, getClass());
    }

    public void confirmOrder(ActionEvent actionEvent) {

        int index = bookOrders.getSelectionModel().getSelectedIndex();
        int orderId = bookOrders.getSelectionModel().getSelectedItem().getOrderId();
        String query = "{CALL confirmOrder(?)}";
        try {
            CallableStatement statement = DbConnect.getInstance().getConnection().prepareCall(query);
            statement.setInt(1, orderId);
            statement.executeQuery();
            list.remove(index);
            bookOrders.setItems(FXCollections.observableArrayList(list));

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void goHome(ActionEvent actionEvent) throws IOException {

        Navigate.goToPage("../View/HomePageView.fxml", actionEvent, getClass());

    }
}
