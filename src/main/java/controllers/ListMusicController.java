package controllers;

import controllers.view.ViewLoader;
import entities.Artist;
import entities.Music;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import repository.MusicRepository;
import utils.*;
import java.awt.*;
import java.net.*;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ListMusicController implements Initializable {

    private final MusicRepository musicRepository = new MusicRepository();
    private final Alerts alerts = new Alerts();


    @FXML private TableView<Music> table;
    @FXML private ChoiceBox<String> orderModeChBox;
    @FXML private ChoiceBox<String> orderByChBox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTable();
        populateTable();
        populateChoiceBoxes();
    }

    @FXML
    public void back(ActionEvent event) throws Exception {
        Parent loader = FXMLLoader.load(getClass().getResource("/ui/main.fxml"));
        Scene scene = new Scene(loader);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        app_stage.setX((primScreenBounds.getWidth() - app_stage.getWidth()) / 2);
        app_stage.setY((primScreenBounds.getHeight() - app_stage.getHeight()) / 2);
        app_stage.show();
    }

    @FXML
    public void showArtistTable(ActionEvent event) throws Exception {
        Parent loader = FXMLLoader.load(getClass().getResource("/ui/list_artists.fxml"));
        Scene scene = new Scene(loader);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        app_stage.setX((primScreenBounds.getWidth() - app_stage.getWidth()) / 2);
        app_stage.setY((primScreenBounds.getHeight() - app_stage.getHeight()) / 2);
        app_stage.show();
    }

    @FXML
    public void addSongWindow(ActionEvent event) throws Exception {
        AddSongController controller = (AddSongController) ViewLoader
                .load(getClass().getResource("/ui/add_song.fxml"), "Add Song");
        controller.addPostOperationCallback(this::populateTable);
    }

    @FXML
    public void openSong(ActionEvent event) throws Exception {
        if(table.getItems().isEmpty()){
            alerts.handleError("Nothing to Play.");
        } else {
            if (table.getSelectionModel().getSelectedItem() == null) {
                alerts.handleWarning(event, "You need to select a song.");
            }
            Music music = table.getSelectionModel().getSelectedItem();
            try {
                HttpURLConnection httpUrlConn = (HttpURLConnection) new URL(music.getSongURL())
                        .openConnection();
                httpUrlConn.setRequestMethod("HEAD");
                httpUrlConn.setConnectTimeout(30000);
                httpUrlConn.setReadTimeout(30000);
                if (httpUrlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Desktop desktop = java.awt.Desktop.getDesktop();
                    URI url = new URI(music.getSongURL());
                    desktop.browse(url);
                }
            } catch (Exception e) {
                alerts.handleError("No such site as: ' " + music.getSongURL() + " '");
            }
        }
    }

    @FXML
    public void checkArtist(ActionEvent event){
        if(table.getItems().isEmpty()){
            alerts.handleError("Nothing to check.");
        } else {
            if (table.getSelectionModel().getSelectedItem() == null) {
                alerts.handleWarning(event, "You need to select a song.");
            } else {
                Music music = table.getSelectionModel().getSelectedItem();
                Artist artist = music.getArtist();
                ViewArtistController controller = (ViewArtistController) ViewLoader
                        .load(getClass().getResource("/ui/view_artist.fxml"), "Artist");
                controller.setData(artist.getStageName(), artist.getFirstName(), artist.getLastName(), artist.getId());
            }
        }
    }

    @FXML
    public void orderTable(){
        if(table.getItems().isEmpty()){
            alerts.handleError("Nothing to order.");
        } else {
            if (orderByChBox.getValue().equals("Genre") && orderModeChBox.getValue().equals("Descending")) {
                Comparator<Music> comparator = Comparator.comparing(Music::getGenre);
                comparator = comparator.reversed();
                FXCollections.sort(table.getItems(), comparator);
                table.refresh();
            } else if (orderByChBox.getValue().equals("Genre") && orderModeChBox.getValue().equals("Ascending")) {
                Comparator<Music> comparator = Comparator.comparing(Music::getGenre);
                FXCollections.sort(table.getItems(), comparator);
                table.refresh();
            } else if (orderByChBox.getValue().equals("Title") && orderModeChBox.getValue().equals("Ascending")) {
                Comparator<Music> comparator = Comparator.comparing(Music::getTitle);
                FXCollections.sort(table.getItems(), comparator);
                table.refresh();
            } else if (orderByChBox.getValue().equals("Title") && orderModeChBox.getValue().equals("Descending")) {
                Comparator<Music> comparator = Comparator.comparing(Music::getTitle);
                comparator = comparator.reversed();
                FXCollections.sort(table.getItems(), comparator);
                table.refresh();
            } else if (orderByChBox.getValue().equals("ID") && orderModeChBox.getValue().equals("Ascending")) {
                Comparator<Music> comparator = Comparator.comparing(Music::getId);
                FXCollections.sort(table.getItems(), comparator);
                table.refresh();
            } else if (orderByChBox.getValue().equals("ID") && orderModeChBox.getValue().equals("Descending")) {
                Comparator<Music> comparator = Comparator.comparing(Music::getId);
                comparator = comparator.reversed();
                FXCollections.sort(table.getItems(), comparator);
                table.refresh();
            }
        }
    }

    @FXML
    private void editSong(ActionEvent event) {
        if(table.getItems().isEmpty()){
            alerts.handleError("Nothing to edit.");
        } else {
            if (table.getSelectionModel().getSelectedItem() == null) {
                alerts.handleWarning(event, "You need to select a song.");
            } else {
                Music music = table.getSelectionModel().getSelectedItem();
                AddSongController controller = (AddSongController) ViewLoader.load(getClass()
                        .getResource("/ui/add_song.fxml"), "Edit song");
                controller.setEditable(music);
                controller.addPostOperationCallback(this::populateTable);
            }
        }
    }

    @FXML
    private void clearData(ActionEvent event) {
        if(table.getItems().isEmpty()){
            alerts.handleError("Nothing to clear.");
        } else {
            for (Music song : table.getItems()) {
                musicRepository.delete(song);
            }
            populateTable();
        }
    }

    @FXML
    public void deleteSong(ActionEvent event) throws Exception {
        if(table.getItems().isEmpty()){
            alerts.handleError("Nothing to delete.");
        } else {
            if (table.getSelectionModel().getSelectedItem() == null) {
                alerts.handleWarning(event, "You need to select a song.");
            }
            Music music = table.getSelectionModel().getSelectedItem();
            musicRepository.delete(music);
            table.refresh();
        }
    }

    @FXML
    public void exitWindow(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    private void configureTable() {
        TableColumn<Music, Integer> column1 = new TableColumn<>("Id");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column1.prefWidthProperty().bind(table.widthProperty().multiply(0.05));
        column1.setResizable(false);

        TableColumn<Music, String> column2 = new TableColumn<>("Genre");
        column2.setCellValueFactory(new PropertyValueFactory<>("genre"));
        column2.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
        column2.setResizable(false);

        TableColumn<Music, String> column3 = new TableColumn<>("Title");
        column3.setCellValueFactory(new PropertyValueFactory<>("title"));
        column3.prefWidthProperty().bind(table.widthProperty().multiply(0.35));
        column3.setResizable(false);

        TableColumn<Music, String> column4 = new TableColumn<>("Song URL");
        column4.setCellValueFactory(new PropertyValueFactory<>("songURL"));
        column4.prefWidthProperty().bind(table.widthProperty().multiply(0.5));
        column4.setResizable(false);

        table.getColumns().add(column1);
        table.getColumns().add(column2);
        table.getColumns().add(column3);
        table.getColumns().add(column4);
    }

    private void populateTable() {
        ObservableList<Music> list = FXCollections.observableArrayList();
        list.addAll(musicRepository.findAll());
        table.setItems(list);
    }

    private void populateChoiceBoxes(){
        ObservableList<String> orderBy = FXCollections.observableArrayList();
        orderBy.add("ID");
        orderBy.add("Genre");
        orderBy.add("Title");
        orderByChBox.setItems(orderBy);
        ObservableList<String> orderMode = FXCollections.observableArrayList();
        orderMode.add("Ascending");
        orderMode.add("Descending");
        orderModeChBox.setItems(orderMode);
    }
}
