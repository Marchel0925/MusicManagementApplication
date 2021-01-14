package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewArtistController implements Initializable {

    @FXML private TextField stageName;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField artistId;
    @FXML private Button closeButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(String artistStageName, String artistFirstName, String artistLastName, Integer id){
        artistId.setText(id.toString());
        stageName.setText(artistStageName);
        firstName.setText(artistFirstName);
        lastName.setText(artistLastName);
    }

    @FXML
    public void okButton() throws IOException {
        Stage stage = (Stage)closeButton.getScene().getWindow(); stage.close();
    }





}
