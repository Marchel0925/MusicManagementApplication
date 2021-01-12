import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.HibernateSessionBuilder;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ui/main.fxml"));
        primaryStage.setTitle("Music Management");
        primaryStage.setScene(new Scene(root, 210, 310));
        primaryStage.show();
    }


    public static void main(String[] args) {
        HibernateSessionBuilder.getInstance();
        launch(args);
        HibernateSessionBuilder.shutdown();
    }
}
