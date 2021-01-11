package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import utils.Alerts;
import utils.Redirect;
import utils.RedirectEnums;

public class MainController implements Initializable {

    private final Redirect redirect = new Redirect();
    private final Alerts alerts = new Alerts();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Main controller initialized!");
    }

    @FXML
    public void switchPanel(ActionEvent event) throws Exception {
        redirect.to(event, RedirectEnums.TO_MUSIC_TABLE.getPath());
    }

    @FXML
    public void authorInformation(ActionEvent event) throws Exception {
        redirect.to(event, RedirectEnums.TO_AUTHOR_INFORMATION.getPath());
    }

    @FXML
    public void darkMode(ActionEvent event) throws Exception {
        alerts.handleWarning(event, "This feature does not work yet.");
    }

    @FXML
    public void exitWindow(ActionEvent event) {
        redirect.exit(event);
    }

}
