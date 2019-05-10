package ClientSide;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.DbConnect;

public class OnlineBookApp extends Application {
    private static final String PROFILE = "../View/ProfileView.fxml";
    private static final String EDIT_PROFILE = "../View/EditProfileView.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
        //      Parent root = FXMLLoader.load(getClass().getResource(""));
        primaryStage.setTitle("Profile");
        //     primaryStage.setScene(new Scene(root, 400, 600));

        Parent edit = FXMLLoader.load(getClass().getResource(PROFILE));
        Scene scene = new Scene(edit, 400, 600);
        primaryStage.setScene(scene);
        // scene.getStylesheets().add(getClass().getResource("View/DarkTheme.css").toExternalForm());
        primaryStage.show();

    }


    public static void main(String[] args) {
        DbConnect connect = DbConnect.getInstance();
        launch(args);
    }
}