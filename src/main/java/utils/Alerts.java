package utils;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

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
}
