package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.Redirect;
import utils.RedirectEnums;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthorInformationController implements Initializable {

    @FXML private ImageView imageView;

    private final Redirect redirect = new Redirect();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image(getClass().getResourceAsStream("/author_info/Mugshot.jpg"));
        imageView.setImage(image);
    }


    @FXML
    public void back(ActionEvent event) throws Exception {
        redirect.to(event, RedirectEnums.TO_MAIN.getPath());
    }
}
