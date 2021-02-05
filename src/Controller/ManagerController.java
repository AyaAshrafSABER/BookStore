package Controller;

import Model.Report;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerController {
    PromoteUserController promoteUser;


    public void addBook(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../View/AddBookView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void placeOrder(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../View/PlaceOrderView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void editBook(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../View/EditBookView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void confirmOrder(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../View/ConfirmOrder.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void promote(ActionEvent event) throws IOException {

    }

    public void promoteUser(ActionEvent actionEvent) {
        PromoteUserController.display();
    }

    public void goToHome(ActionEvent actionEvent) throws IOException {

        Navigate.goToPage("../View/HomePageView.fxml", actionEvent, getClass());
    }

    public void logOut(ActionEvent actionEvent) throws IOException {

        User.getUser().logOut();
        Navigate.goToPage("../View/HomePageView.fxml", actionEvent, getClass());


    }

    public void goToShoppingCart(ActionEvent actionEvent) throws IOException {

        Navigate.goToPage("../View/ShoppingCart.fxml", actionEvent, getClass());


    }

    public void getReports(ActionEvent actionEvent) {

        Report report = new Report();
        report.getTop10Books();
        report.getTop5Customer();
        report.totalSales();

    }
}
