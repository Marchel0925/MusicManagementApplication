package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import utils.Redirect;
import utils.RedirectEnums;

public class AuthorInformationController {

    private final Redirect redirect = new Redirect();

    @FXML
    public void back(ActionEvent event) throws Exception {
        redirect.to(event, RedirectEnums.TO_MAIN.getPath());
    }
}
