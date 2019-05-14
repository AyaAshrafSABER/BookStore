package Controller;

import Model.User;
import Model.bookOrder;
import Model.shoppingCartItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ShoppingCartController {

    private static int orderNumber = 1546;
    @FXML
    private TableView cartTable;
    @FXML
    private Label userName;

    @FXML
    private Label totalPrice;

    @FXML
    private TableColumn<bookOrder, String> itemCol;

    @FXML
    private TableColumn<bookOrder, String> priceCol;

    @FXML
    private TableColumn<bookOrder, String> quantityCol;


    @FXML
    private TableColumn<bookOrder, String> totalPriceCol;

    @FXML
    private ObservableList<shoppingCartItem> cartItems;

    @FXML
    public void initialize() {
        userName.setText(User.getUser().getUserName());
        totalPrice.setText(String.valueOf(User.getUser().getTotalPriceCart()));
        List<shoppingCartItem> shoppingCart = User.getUser().getShoppingCart();
        cartItems = FXCollections.observableArrayList(shoppingCart);
        itemCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        cartTable.getColumns().setAll(itemCol, priceCol, quantityCol, totalPriceCol);
        cartTable.setItems(cartItems);

    }

    public void backToHome(ActionEvent actionEvent) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../View/HomePageView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //This line gets the Stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();

    }

    public void CheckOut(ActionEvent actionEvent) {


        DbConnect connect = DbConnect.getInstance();
        String queryCustomer = "{CALL insertCustomerOrder(?,?)}";
        String query = "{CALL addToCart(?,?,?,?)}";
        CallableStatement statement = null;
        try {

            connect.getConnection().setAutoCommit(false);

            statement = connect.getConnection().prepareCall(queryCustomer);
            statement.setInt(1, User.getUserId());
            statement.setInt(2, User.getUser().getTotalPriceCart());
            ResultSet set = statement.executeQuery();
            while (set.next()) orderNumber = set.getInt(1);

            statement = connect.getConnection().prepareCall(query);
            for (shoppingCartItem item : cartItems) {

                statement.setInt(1, orderNumber);
                statement.setInt(2, item.getBookId());
                statement.setInt(3, item.getQuantity());
                statement.setInt(4, item.getPrice());
                statement.executeQuery();

            }

            connect.getConnection().commit();
            User.getUser().emptyShoppingCart();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/CheckoutView.fxml"));
            Parent tableViewParent = loader.load();

            CheckOutController controller = loader.getController();

            controller.initData(User.getUser().getTotalPriceCart(), orderNumber);
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (SQLException e) {
            try {
                connect.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                connect.getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void remoteSelectedItem(ActionEvent actionEvent) {

        int index = cartTable.getSelectionModel().getSelectedIndex();
        User.getUser().getShoppingCart().remove(index);
        cartItems.remove(index);
        cartTable.setItems(cartItems);

    }
}
