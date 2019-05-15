package Controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.CallableStatement;
import java.sql.SQLException;

public class addAuthorsController {


    private TextField name = new TextField();
    private TextField address = new TextField();
    private TextField phone = new TextField();



    public void display() {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add new Author ");
        window.setMinWidth(400);

        Label label = new Label();
        label.setText("Insert The data of the New Author");
        GridPane gridPane = new GridPane();
        gridPane.addRow(0,new Label("Name"),name);
        gridPane.addRow(1,new Label("address"),address);
        Button closeButton = new Button("add New");
        closeButton.setOnAction(e -> {
            String query = "{CALL insertAuthor(?,?)}";
            try {
                CallableStatement statement1 = DbConnect.getInstance().getConnection().prepareCall(query);
                statement1.setString(1,name.getText());
                statement1.setString(2,address.getText());
                statement1.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            window.close();

        });
        VBox layout = new VBox();
        layout.getChildren().addAll(label,gridPane, closeButton);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }



}