package ClientSide;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.*;
import Model.Report;



public class OnlineBookApp extends Application {
    private static final String HOME_PAGE = "../View/HomePageView.fxml";
    private static final String EDIT_PROFILE = "../View/EditProfileView.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
        //      Parent root = FXMLLoader.load(getClass().getResource(""));
        primaryStage.setTitle("HOME_PAGE");
        //     primaryStage.setScene(new Scene(root, 400, 600));

        Parent edit = FXMLLoader.load(getClass().getResource(HOME_PAGE));
        Scene scene = new Scene(edit);
        primaryStage.setScene(scene);
        // scene.getStylesheets().add(getClass().getResource("View/DarkTheme.css").toExternalForm());
        primaryStage.show();

    }


    public static void main(String[] args) {
        Report re = new Report();
        ((Report) re).getTop5Customer();
        DbConnect connect = DbConnect.getInstance();

     //   launch(args);
    }
}