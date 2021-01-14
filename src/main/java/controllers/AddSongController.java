package controllers;

import entities.Artist;
import entities.Music;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import repository.ArtistRepository;
import repository.MusicRepository;
import utils.Alerts;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSongController implements Initializable {

    private final ArtistRepository artistRepository = new ArtistRepository();
    private final MusicRepository musicRepository = new MusicRepository();
    private final Alerts alerts = new Alerts();

    @FXML private TextField genre;
    @FXML private TextField title;
    @FXML private TextField url;
    @FXML private TextField artistId;

    @FXML private StackPane rootPane;

    private Music editable;

    private Runnable closeDialogCallback;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void addPostOperationCallback(Runnable callback) {
        this.closeDialogCallback = callback;
    }

    public void setEditable(Music music) {
        this.editable = music;
        this.genre.setText(music.getGenre());
        this.title.setText(music.getTitle());
        this.url.setText(music.getSongURL());
        this.artistId.setText(music.getArtist().getId().toString());
    }

    @FXML
    private void addSong(ActionEvent event) {
        String songGenre = genre.getText();
        String songTitle = title.getText();
        String songURL = url.getText();
        String songArtistId = artistId.getText();

        if (songGenre.isEmpty() || songTitle.isEmpty() || songURL.isEmpty() || songArtistId.isEmpty()) {
            alerts.handleWarning(event, "All fields must be filled");
            return;
        }

        Integer artistId = Integer.parseInt(songArtistId);
        Artist artist = artistRepository.findOne(artistId);
        if (artist == null) {
            alerts.handleError(event, "No such artist with that kind of ID");
            return;
        }

        if (editable == null) {
            musicRepository.save(new Music(songGenre, songTitle, songURL, artist));
        } else {
            Music music = musicRepository.findOne(editable.getId());
            music.setGenre(songGenre);
            music.setTitle(songTitle);
            music.setSongURL(songURL);
            music.setArtist(artist);
            musicRepository.merge(music);
        }
        clearEntries();
        closeStage();
        closeDialogCallback.run();
    }

    private void clearEntries() {
        editable = null;
        genre.clear();
        title.clear();
        url.clear();
        artistId.clear();
    }

    @FXML
    private void closeStage() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

}
