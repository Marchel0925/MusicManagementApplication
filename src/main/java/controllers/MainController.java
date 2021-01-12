package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;
import utils.Alerts;

public class MainController implements Initializable {

    private final Alerts alerts = new Alerts();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Main controller initialized!");
    }

    @FXML
    public void switchPanel(ActionEvent event) throws Exception {
        Parent loader = FXMLLoader.load(getClass().getResource("/ui/list_music.fxml"));
        Scene scene = new Scene(loader);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        app_stage.setX((primScreenBounds.getWidth() - app_stage.getWidth()) / 2);
        app_stage.setY((primScreenBounds.getHeight() - app_stage.getHeight()) / 2);
        app_stage.show();
    }

    @FXML
    public void authorInformation(ActionEvent event) throws Exception {
        Parent loader = FXMLLoader.load(getClass().getResource("/ui/author_information.fxml"));
        Scene scene = new Scene(loader);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        app_stage.setX((primScreenBounds.getWidth() - app_stage.getWidth()) / 2);
        app_stage.setY((primScreenBounds.getHeight() - app_stage.getHeight()) / 2);
        app_stage.show();
    }

    @FXML
    public void darkMode(ActionEvent event) throws Exception {
        alerts.handleWarning(event, "This feature does not work yet.");
    }

    @FXML
    public void exitWindow(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

}
