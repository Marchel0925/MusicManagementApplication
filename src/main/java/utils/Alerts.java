package utils;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerts {

    Alert a = new Alert(Alert.AlertType.NONE);

    public void handleError(ActionEvent event, String message){
        a.setAlertType(Alert.AlertType.ERROR);
        a.setContentText(message);
        a.show();
    }

    public void handleError(String message){
        a.setAlertType(Alert.AlertType.ERROR);
        a.setContentText(message);
        a.show();
    }

    public void handleWarning(ActionEvent event, String message){
        a.setAlertType(Alert.AlertType.WARNING);
        a.setContentText(message);
        a.show();
    }

    public void handleWarning(String message){
        a.setAlertType(Alert.AlertType.WARNING);
        a.setContentText(message);
        a.show();
    }
}
