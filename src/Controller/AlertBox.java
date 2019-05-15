package Controller;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlertBox {

    public static void display(String title, String message) {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("Choose The user You want to promote");
        ListView<String> users = new ListView<>();
        DbConnect connect = DbConnect.getInstance();
        String queryGet = "{CAll getUsers()}";


        try {
            CallableStatement statement = connect.getConnection().prepareCall(queryGet);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                users.getItems().add(set.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Button closeButton = new Button("promote");
        closeButton.setOnAction(e -> {
            String query = "{CALL managerPromote(?)}";
            try {
                CallableStatement statement1 = connect.getConnection().prepareCall(query);
                statement1.setString(1, users.getSelectionModel().getSelectedItem());
                statement1.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            window.close();

        });
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}