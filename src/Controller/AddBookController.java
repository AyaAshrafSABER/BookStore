
package Controller;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.*;

import Model.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddBookController {

    DbConnect connect = DbConnect.getInstance();

    @FXML
    private Label userName;
    @FXML
    private TextField ISBN;
    @FXML
    private TextField Title;
    @FXML
    private TextField Price;
    @FXML
    private TextField threshold;
    @FXML
    private TextField copies;
    @FXML
    private ComboBox<String> category;
    @FXML
    private TextField publicationYear;
    @FXML
    private TextField publisherName;
    @FXML
    private ComboBox<String> allPublishers;
    @FXML
    private ComboBox<String> allAuthors;
    @FXML
    private TextField publisherAdress;
    @FXML
    private TextField publisherPhone;
    @FXML
    private ListView<String> authors;

    private Map<String, Integer> authorsMap;
    private Map<String, Integer> publisherMap;
    private List<String> authorsList;
    private List<String> publisherList;

    @FXML
    public void initialize() {


        getAuthors();
        getPublishers();
        userName.setText(User.getUser().getUserName());
        category.setItems(FXCollections.observableArrayList("Science", "Art", "Geography", "History", "Religion"));

    }

    private void getPublishers() {

        String query = "{CALL getPublishers()}";
        publisherList = new ArrayList<>();
        publisherMap = new HashMap<>();
        try {
            ResultSet set = connect.getDataProcedure(connect.getConnection().prepareCall(query));
            while (set.next()) {
                publisherMap.put(set.getString(2), set.getInt(1));
                publisherList.add(set.getString(2));
            }
            allPublishers.setItems(FXCollections.observableArrayList(publisherList));

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void getAuthors() {
        String query = "{CALL getAuthors()}";
        authorsList = new ArrayList<>();
        authorsMap = new HashMap<>();
        try {
            ResultSet set = connect.getDataProcedure(connect.getConnection().prepareCall(query));
            while (set.next()) {
                authorsMap.put(set.getString(2), set.getInt(1));
                authorsList.add(set.getString(2));
            }
            allAuthors.setItems(FXCollections.observableArrayList(authorsList));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void add(ActionEvent actionEvent) {
        DbConnect connect = DbConnect.getInstance();
        String query = "{CALL insertToBook(?,?,?,?,?,?,?)}";
        String authorquery = "{CALL insertToAuthors(?,?)}";
        try {
            connect.getConnection().setAutoCommit(false);
//            CallableStatement statement3 = connect.getConnection().prepareCall(publisherquery);
//            statement3.setString(1, publisherName.getText());
//            statement3.setString(2, publisherAdress.getText());
//            statement3.setString(3, publisherPhone.getText());
//            statement3.executeQuery();


            CallableStatement statement = connect.getConnection().prepareCall(query);
            statement.setString(1, Title.getText());
            int publisherId = publisherMap.get(allPublishers.getSelectionModel().getSelectedItem());
            statement.setInt(2, publisherId);
            statement.setInt(3, Integer.parseInt(publicationYear.getText()));
            statement.setInt(4, Integer.parseInt(Price.getText()));
            statement.setString(5, category.getSelectionModel().getSelectedItem());
            statement.setInt(6, Integer.parseInt(threshold.getText()));
            statement.setInt(7, Integer.parseInt(copies.getText()));
            ResultSet set = statement.executeQuery();
            int bookId = -1;
            while (set.next()) bookId = set.getInt(1);

            if (bookId != -1) {
                CallableStatement statement2 = connect.getConnection().prepareCall(authorquery);
                for (String s : allAuthors.getItems()) {
                    statement2.setInt(1, bookId);
                    int authorId = authorsMap.get(s);
                    statement2.setInt(2, authorId);
                    ResultSet set2 = statement2.executeQuery();
                }
            }

            connect.getConnection().commit();
            Navigate.goToPage("../View/HomePageView.fxml", actionEvent, getClass());

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connect.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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

    public void addAuthor(ActionEvent actionEvent) {

        String string = allAuthors.getSelectionModel().getSelectedItem();
        if (!authors.getItems().contains(string))
            authors.getItems().add(string);

    }

    public void deleteAuthor(ActionEvent actionEvent) {

        String string = allAuthors.getSelectionModel().getSelectedItem();
        if (authors.getItems().contains(string))
            authors.getItems().remove(string);
    }


    public void goToShoppingCart(ActionEvent actionEvent) throws IOException {

        Navigate.goToPage("../View/ShoppingCart.fxml", actionEvent, getClass());

    }

    public void goHome(ActionEvent actionEvent) throws IOException {

        Navigate.goToPage("../View/HomePageView.fxml", actionEvent, getClass());

    }

    public void addNewPubliher(ActionEvent actionEvent) throws IOException {

        addPublisherController controller = new addPublisherController();
        controller.display();
        getPublishers();
    }

    public void addNewAuthor(ActionEvent actionEvent) {

        addAuthorsController controller = new addAuthorsController();
        controller.display();
        getAuthors();

    }

}
