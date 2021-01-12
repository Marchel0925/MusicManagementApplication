package controllers;

import entities.Artist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import repository.ArtistRepository;
import utils.Alerts;

import java.net.URL;
import java.util.ResourceBundle;

public class AddArtistController implements Initializable {

    private final ArtistRepository artistRepository = new ArtistRepository();
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
        Parent loader = FXMLLoader.load(getClass().getResource("/ui/list_artists.fxml"));
        Scene scene = new Scene(loader);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        app_stage.setX((primScreenBounds.getWidth() - app_stage.getWidth()) / 2);
        app_stage.setY((primScreenBounds.getHeight() - app_stage.getHeight()) / 2);
        app_stage.show();
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
