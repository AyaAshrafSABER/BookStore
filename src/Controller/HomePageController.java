package Controller;

import Model.User;
import Model.book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.resultsetmapper.ReflectionResultSetMapper;
import net.sf.resultsetmapper.ResultSetMapper;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomePageController {
    private static int maxValue = 1000000;
    private static int index = 0;
    private DbConnect connect = DbConnect.getInstance();
    private CallableStatement statement;

    @FXML
    private ChoiceBox<String> categories;

    @FXML
    private TextField isbn;

    @FXML
    private TextField title;

    @FXML
    private TextField publisherName;

    @FXML
    private TextField publicationYear;

    @FXML
    private TextField minPrice;

    @FXML
    private TextField maxPrice;

    @FXML
    private TextField authorName;

    @FXML
    private ListView<String> authorsList;

    @FXML
    private Button shoppingCartButton;

    @FXML
    private TableView<book> bookTable;

    @FXML
    private Button manage;
    @FXML
    private Button profile;
    @FXML
    private Label userName;

    @FXML
    private ImageView imageLog;
    @FXML
    private TableColumn titleCol;
    @FXML
    private TableColumn categoryCol;
    @FXML
    private TableColumn priceCol;
    @FXML
    private TableColumn isbnCol;

    private ObservableList<book> books = FXCollections.observableArrayList();
    private ObservableList<String> authors = FXCollections.observableArrayList();

    @FXML
    void addAuthor(ActionEvent event) {

        if (authorName.getText().length() > 0) {

            authors.add(authorName.getText());
            authorsList.setItems(authors);
        }

    }

    @FXML
    void deleteAuthor(ActionEvent event) {

        authors.remove(authorsList.getSelectionModel().getSelectedIndex());
        authorsList.setItems(authors);

    }

    @FXML
    public void initialize() throws IOException {


        categories.setItems(FXCollections.observableArrayList("Science", "Art", "Geography", "History", "Religion"));
        if (User.getUser() == null) {
            profile.setDisable(true);
            profile.setVisible(false);
            manage.setDisable(true);
            manage.setVisible(false);
            Image image = new Image(getClass().getResourceAsStream("../res/login.png"));
            imageLog.setImage(image);
            userName.setVisible(false);
            shoppingCartButton.setDisable(true);
            shoppingCartButton.setVisible(false);
        } else {
            userName.setText(User.getUser().getUserName());
            User user = User.getUser();
            if (!User.getUser().isPrivilege()) {
                manage.setDisable(true);
                manage.setVisible(false);
            }
        }

    }

    public void searchBook(ActionEvent actionEvent) throws SQLException {
        index = 0;
        String lPrice = minPrice.getText();
        String hPrice = maxPrice.getText();
        String year = publicationYear.getText();
        String isbnString = isbn.getText();
        String pName = publisherName.getText();
        String bookTitle = title.getText();
        String query = "{CALL searchForBook(?,?,?,?,?,?,?,?,?)}";
        statement = connect.getConnection().prepareCall(query);
        if (!isbnString.isEmpty())
            statement.setInt(1, Integer.parseInt(isbnString));
        else
            statement.setString(1, null);
        if (!bookTitle.isEmpty())
            statement.setString(2, bookTitle);
        else
            statement.setString(2, null);
        if (!pName.isEmpty())
            statement.setString(3, pName);
        else
            statement.setString(3, null);
        if (!year.isEmpty())
            statement.setString(4, year);
        else
            statement.setString(4, null);
        if (!lPrice.isEmpty())
            statement.setInt(5, Integer.parseInt(lPrice));
        else
            statement.setInt(5, 0);
        if (!hPrice.isEmpty())
            statement.setInt(6, Integer.parseInt(hPrice));
        else
            statement.setInt(6, maxValue);
        statement.setString(7, categories.getSelectionModel().getSelectedItem());
        statement.setString(8, authorsList.getSelectionModel().getSelectedItem());
        statement.setInt(9, index);
        bookTable.getItems().clear();
        mapResultSet();


    }

    private void mapResultSet() throws SQLException {
        statement.executeQuery();
        bookTable.getItems().clear();
        isbnCol = new TableColumn("bookId");
        titleCol = new TableColumn("title");
        priceCol = new TableColumn("price");
        categoryCol = new TableColumn("category");
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        bookTable.getColumns().setAll(isbnCol, titleCol, categoryCol, priceCol);
        ResultSet set = connect.getDataProcedure(statement);
        ResultSetMapper<book> resultSetMapper = new ReflectionResultSetMapper<book>(book.class);
        index++;
        while (set.next()) {
            book book = resultSetMapper.mapRow(set);
            book.setBookId(set.getInt(1));
            books.add(book);
        }
        bookTable.setItems(books);
    }


    public void Login(javafx.event.ActionEvent event) throws IOException {

        if (User.getUser() == null)
            Navigate.goToPage("../View/LogINView.fxml", event, getClass());
        else {
            User.getUser().logOut();
            Navigate.goToPage("../View/HomePageView.fxml", event, getClass());

        }
    }

    public void loadMore(ActionEvent actionEvent) throws SQLException {
        index += 10;
        statement.setInt(9, index);
        mapResultSet();

    }

    public void goToShoppingCart(ActionEvent actionEvent) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../View/ShoppingCart.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //This line gets the Stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

    }

    public void getBookDetails(MouseEvent mouseEvent) throws IOException {

        if (mouseEvent.getClickCount() > 1) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/BookView.fxml"));
            Parent tableViewParent = loader.load();

            BookController bookController = loader.getController();

            bookController.initBookData(bookTable.getSelectionModel().getSelectedItem());
            Scene tableViewScene = new Scene(tableViewParent);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }

    }

    public void goToManage(ActionEvent actionEvent) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../View/ManagerView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //This line gets the Stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void getProfile(ActionEvent actionEvent) throws IOException {

        Navigate.goToPage("../View/ProfileView.fxml", actionEvent, getClass());

    }
}
