package controllers;

import entities.Music;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import repository.MusicRepository;
import utils.Alerts;
import utils.Redirect;
import utils.RedirectEnums;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class ListMusicController implements Initializable {

    private final MusicRepository musicRepository = new MusicRepository();
    private final Redirect redirect = new Redirect();
    private final Alerts alerts = new Alerts();

    @FXML private TableView<Music> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTable();
        populateTable();
    }

    @FXML
    public void back(ActionEvent event) throws Exception {
        redirect.to(event, RedirectEnums.TO_MAIN.getPath());
    }

    @FXML
    public void showArtistTable(ActionEvent event) throws Exception {
        redirect.to(event, RedirectEnums.TO_ARTIST_TABLE.getPath());
    }

    @FXML
    public void addSongWindow(ActionEvent event) throws Exception {
        redirect.to(event, RedirectEnums.TO_ADD_SONG_WINDOW.getPath());
    }

    @FXML
    public void openSong(ActionEvent event) throws Exception {
        if(table.getSelectionModel().getSelectedItem() == null){
            alerts.handleWarning(event, "You need to select a song.");
        }
        Desktop desktop = java.awt.Desktop.getDesktop();
        Music music = table.getSelectionModel().getSelectedItem();
        URI url = new URI(music.getSongURL());
        desktop.browse(url);
    }

    @FXML
    public void deleteSong(ActionEvent event) throws Exception {
        if(table.getSelectionModel().getSelectedItem() == null){
            alerts.handleWarning(event, "You need to select a song.");
        }
        Music music = table.getSelectionModel().getSelectedItem();
        musicRepository.delete(music);
        populateTable();
    }

    @FXML
    public void exitWindow(ActionEvent event) {
        redirect.exit(event);
    }

    private void configureTable() {
        TableColumn<Music, Integer> column1 = new TableColumn<>("Id");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column1.prefWidthProperty().bind(table.widthProperty().multiply(0.05));
        column1.setResizable(false);

        TableColumn<Music, String> column2 = new TableColumn<>("Genre");
        column2.setCellValueFactory(new PropertyValueFactory<>("genre"));
        column2.prefWidthProperty().bind(table.widthProperty().multiply(0.10));
        column2.setResizable(false);

        TableColumn<Music, String> column3 = new TableColumn<>("Title");
        column3.setCellValueFactory(new PropertyValueFactory<>("title"));
        column3.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        column3.setResizable(false);

        TableColumn<Music, String> column4 = new TableColumn<>("Song URL");
        column4.setCellValueFactory(new PropertyValueFactory<>("songURL"));
        column4.prefWidthProperty().bind(table.widthProperty().multiply(0.5));
        column4.setResizable(false);

        /*
        TableColumn<Music, String> column5 = new TableColumn<>("Artist ID");
        column5.setCellValueFactory(new PropertyValueFactory<>(" "));
        column5.prefWidthProperty().bind(table.widthProperty().multiply(0.10));
        column5.setResizable(false);
         */


        table.getColumns().add(column1);
        table.getColumns().add(column2);
        table.getColumns().add(column3);
        table.getColumns().add(column4);
    }

    private void populateTable() {
        ObservableList<Music> list = FXCollections.observableArrayList();
        list.addAll( musicRepository.findAll());
        table.setItems(list);
    }

}
