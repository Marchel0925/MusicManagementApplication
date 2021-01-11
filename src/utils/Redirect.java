package utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Redirect {

    public void to(ActionEvent event, String toWhere) throws Exception{
        Parent loader = FXMLLoader.load(getClass().getResource(toWhere));
        Scene scene = new Scene(loader);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        app_stage.setX((primScreenBounds.getWidth() - app_stage.getWidth()) / 2);
        app_stage.setY((primScreenBounds.getHeight() - app_stage.getHeight()) / 2);
        app_stage.show();
    }

    public void exit(ActionEvent event){
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
