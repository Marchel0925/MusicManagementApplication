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
import utils.Redirect;
import utils.RedirectEnums;

public class MainController implements Initializable {

    @FXML private CheckBox checkBox;

    private final Redirect redirect = new Redirect();

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
        Parent loader = FXMLLoader.load(getClass().getResource("/ui/main.fxml"));
        Scene scene = new Scene(loader);
        if (checkBox.isSelected()) {
            scene.getStylesheets().add("css/dark_mode.css");
        } else {
            scene.getStylesheets().remove("css/dark_mode.css");
        }
    }

    @FXML
    public void exitWindow(ActionEvent event) {
        redirect.exit(event);
    }

}
