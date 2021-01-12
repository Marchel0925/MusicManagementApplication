package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthorInformationController implements Initializable {

    @FXML private ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image(getClass().getResourceAsStream("/author_info/Mugshot.jpg"));
        imageView.setImage(image);
    }


    @FXML
    public void back(ActionEvent event) throws Exception {
        Parent loader = FXMLLoader.load(getClass().getResource("/ui/main.fxml"));
        Scene scene = new Scene(loader);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        app_stage.setX((primScreenBounds.getWidth() - app_stage.getWidth()) / 2);
        app_stage.setY((primScreenBounds.getHeight() - app_stage.getHeight()) / 2);
        app_stage.show();
    }
}
