package Controller;

import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class PlaceOrderController {

    @FXML
    private TextField ISBN;
    @FXML
    private TextField NoOfCopies;

    @FXML
    private Label userName;

    public void initialize() {
        userName.setText(User.getUser().getUserName());
    }


    public void submitOrder(ActionEvent actionEvent) {
        DbConnect connect = DbConnect.getInstance();
        String query = "{CALL orderBook(?,?)}";
        try {
            connect.getConnection().setAutoCommit(false);
            CallableStatement statement = connect.getConnection().prepareCall(query);
            statement.setString(1, ISBN.getText());
            statement.setInt(2, Integer.parseInt(NoOfCopies.getText()));
            statement.executeQuery();
            connect.getConnection().commit();
            Navigate.goToPage("../View/ConfirmOrder.fxml", actionEvent, getClass());


        } catch (SQLException e) {

            e.printStackTrace();
            try {
                connect.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                connect.getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void goToShoppingCart(ActionEvent actionEvent) throws IOException {
        Navigate.goToPage("../View/ShoppingCart.fxml", actionEvent, getClass());


    }

    public void goToHome(ActionEvent actionEvent) throws IOException {
        Navigate.goToPage("../View/HomePageView.fxml", actionEvent, getClass());

    }
}
