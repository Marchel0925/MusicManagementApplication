package controllers;

import entities.Artist;
import entities.Music;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import repository.ArtistRepository;
import utils.Alerts;
import utils.Redirect;
import utils.RedirectEnums;

import java.net.URL;
import java.util.ResourceBundle;

public class ListArtistController implements Initializable {

    private final ArtistRepository artistRepository = new ArtistRepository();
    private final Redirect redirect = new Redirect();
    private final Alerts alerts = new Alerts();

    @FXML
    private TableView<Artist> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTable();
        populateTable();
    }

    @FXML
    public void addArtistWindow(ActionEvent event) throws Exception {
        redirect.to(event, RedirectEnums.TO_ADD_ARTIST_WINDOW.getPath());
    }

    @FXML
    public void deleteArtist(ActionEvent event) throws Exception {
        if(table.getSelectionModel().getSelectedItem() == null){
            alerts.handleWarning(event, "You need to select a artist.");
        }
        Artist artist = table.getSelectionModel().getSelectedItem();
        artistRepository.delete(artist);
        populateTable();
    }

    @FXML
    public void back(ActionEvent event) throws Exception {
        redirect.to(event, RedirectEnums.TO_MUSIC_TABLE.getPath());
    }


    private void configureTable() {
        TableColumn<Artist, Integer> column1 = new TableColumn<>("Id");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column1.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
        column1.setResizable(false);

        TableColumn<Artist, String> column2 = new TableColumn<>("Stage name");
        column2.setCellValueFactory(new PropertyValueFactory<>("stageName"));
        column2.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
        column2.setResizable(false);

        TableColumn<Artist, String> column3 = new TableColumn<>("First name");
        column3.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        column3.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
        column3.setResizable(false);

        TableColumn<Artist, String> column4 = new TableColumn<>("Last name");
        column4.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        column4.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
        column4.setResizable(false);

        table.getColumns().add(column1);
        table.getColumns().add(column2);
        table.getColumns().add(column3);
        table.getColumns().add(column4);
    }

    private void populateTable() {
        ObservableList<Artist> list = FXCollections.observableArrayList();
        list.addAll(artistRepository.findAll());
        table.setItems(list);
    }
}
