package controllers;

import entities.Artist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import repository.ArtistRepository;
import utils.Alerts;
import utils.Redirect;
import utils.RedirectEnums;

import java.net.URL;
import java.util.ResourceBundle;

public class AddArtistController implements Initializable {

    private final ArtistRepository artistRepository = new ArtistRepository();
    private final Redirect redirect = new Redirect();
    private final Alerts alerts = new Alerts();

    @FXML private TextField stageName;
    @FXML private TextField firstName;
    @FXML private TextField lastName;

    @FXML private StackPane rootPane;

    private Artist editable;

    private Runnable closeDialogCallback;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void addPostOperationCallback(Runnable callback) {
        this.closeDialogCallback = callback;
    }

    public void setEditable(Artist artist) {
        this.editable = artist;
        this.stageName.setText(artist.getStageName());
        this.firstName.setText(artist.getFirstName());
        this.lastName.setText(artist.getLastName());
    }

    @FXML
    private void addArtist(ActionEvent event) {
        String artistStageName = stageName.getText();
        String artistFirstName = firstName.getText();
        String artistLastName = lastName.getText();

        if (artistStageName.isEmpty() || artistFirstName.isEmpty() || artistLastName.isEmpty()) {
            alerts.handleWarning(event, "All fields must be filled");
            return;
        }

        if (editable == null) {
            artistRepository.save(new Artist(artistStageName, artistFirstName, artistLastName));
        } else {
            Artist artist = artistRepository.findOne(editable.getId());
            artist.setStageName(artistStageName);
            artist.setFirstName(artistFirstName);
            artist.setLastName(artistLastName);
            artistRepository.merge(artist);
        }
        clearEntries();
        closeStage();
        closeDialogCallback.run();
    }

    @FXML
    private void cancel(ActionEvent event) throws Exception {
        redirect.to(event, RedirectEnums.TO_ARTIST_TABLE.getPath());
    }

    private void clearEntries() {
        editable = null;
        stageName.clear();
        firstName.clear();
        lastName.clear();
    }

    private void closeStage() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

}
