package Controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class PromoteUserController extends Dialog<Object> {

    private static final String TITLE = "Promote User";
    private static final String SECTION_MESSAGE = "PLease Enter the following information";
    private static final String USER_NAME_EXAMPLE = "Mohamed96";
    private static final String USER_NAME = "User Name";
    private static final String PROMOTE_USER = "Promote";
    private static TextInputDialog dialog;


    public void initializePromoteButtons() {
        dialog = new TextInputDialog(USER_NAME);
        dialog.setTitle(TITLE);
        dialog.setHeaderText(PROMOTE_USER);
        dialog.setContentText(SECTION_MESSAGE);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            DbConnect connect = DbConnect.getInstance();
            String query = "{CALL managerPromote(?)}";
            try {
                CallableStatement statement = connect.getConnection().prepareCall(query);
                statement.setString(1, result.get());
                statement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
