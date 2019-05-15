package Controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.CallableStatement;
import java.sql.SQLException;

public class addController  {


    private TextField name = new TextField();
    private TextField address = new TextField();
    private TextField phone = new TextField();



        public void display(String title, String message) {
            Stage window = new Stage();

            //Block events to other windows
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(title);
            window.setMinWidth(250);

            Label label = new Label();
            label.setText("Choose The user You want to promote");
            GridPane gridPane = new GridPane();
            gridPane.addRow(0,new Label("Name"),name);
            gridPane.addRow(1,new Label("Name"),address);
            gridPane.addRow(2,new Label("Name"),phone);
            Button closeButton = new Button("add New");
            closeButton.setOnAction(e -> {
                String query = "{CALL insert(?)}";
                try {
                    CallableStatement statement1 = DbConnect.getInstance().getConnection().prepareCall(query);

                    statement1.executeQuery();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                window.close();

            });
            VBox layout = new VBox(10);
            layout.getChildren().addAll(label, closeButton);

            //Display window and wait for it to be closed before returning
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        }



}