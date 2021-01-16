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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import repository.ArtistRepository;
import utils.Alerts;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class ListArtistController implements Initializable {

    private final ArtistRepository artistRepository = new ArtistRepository();
    private final Alerts alerts = new Alerts();

    @FXML private TableView<Artist> table;
    @FXML private ChoiceBox<String> orderModeChBox;
    @FXML private ChoiceBox<String> orderByChBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTable();
        populateTable();
        populateChoiceBoxes();
    }

    @FXML
    public void addArtistWindow(ActionEvent event) throws Exception {
        AddArtistController controller = (AddArtistController) ViewLoader
                .load(getClass().getResource("/ui/add_artist.fxml"), "Add Artist");
        controller.addPostOperationCallback(this::populateTable);
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
    private void editArtist(ActionEvent event) {
        if(table.getSelectionModel().getSelectedItem() == null) {
            alerts.handleWarning(event, "You need to select a artist.");
        } else {
            Artist artist = table.getSelectionModel().getSelectedItem();
            AddArtistController controller = (AddArtistController) ViewLoader.load(getClass()
                    .getResource("/ui/add_artist.fxml"), "Edit artist");
            controller.setEditable(artist);
            controller.addPostOperationCallback(this::populateTable);
        }
    }

    @FXML
    private void checkSongs(ActionEvent event) {
        if(table.getSelectionModel().getSelectedItem() == null) {
            alerts.handleWarning(event, "You need to select a artist.");
        } else {
            Artist artist = table.getSelectionModel().getSelectedItem();
            List<Music> list = new ArrayList<>(artist.getMusicSet());
            ViewSongsController controller = (ViewSongsController) ViewLoader.load(getClass()
                    .getResource("/ui/view_songs.fxml"), "All songs");
            controller.setData(artist.getStageName(), artist.getFirstName(), artist.getLastName());
            controller.setSongList(list);
        }
    }

    @FXML
    public void back(ActionEvent event) throws Exception {
        Parent loader = FXMLLoader.load(getClass().getResource("/ui/list_music.fxml"));
        Scene scene = new Scene(loader);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        app_stage.setX((primScreenBounds.getWidth() - app_stage.getWidth()) / 2);
        app_stage.setY((primScreenBounds.getHeight() - app_stage.getHeight()) / 2);
        app_stage.show();
    }

    @FXML
    public void orderTable(){
        if(orderByChBox.getValue().equals("Stage Name") && orderModeChBox.getValue().equals("Descending")) {
            Comparator<Artist> comparator = Comparator.comparing(Artist::getStageName);
            comparator = comparator.reversed();
            FXCollections.sort(table.getItems(), comparator);
            table.refresh();
        } else if(orderByChBox.getValue().equals("Stage Name") && orderModeChBox.getValue().equals("Ascending")) {
            Comparator<Artist> comparator = Comparator.comparing(Artist::getStageName);
            FXCollections.sort(table.getItems(), comparator);
            table.refresh();
        } else if(orderByChBox.getValue().equals("First Name") && orderModeChBox.getValue().equals("Ascending")) {
            Comparator<Artist> comparator = Comparator.comparing(Artist::getFirstName);
            FXCollections.sort(table.getItems(), comparator);
            table.refresh();
        } else if(orderByChBox.getValue().equals("First Name") && orderModeChBox.getValue().equals("Descending")) {
            Comparator<Artist> comparator = Comparator.comparing(Artist::getFirstName);
            comparator = comparator.reversed();
            FXCollections.sort(table.getItems(), comparator);
            table.refresh();
        }else if(orderByChBox.getValue().equals("Last Name") && orderModeChBox.getValue().equals("Ascending")) {
            Comparator<Artist> comparator = Comparator.comparing(Artist::getLastName);
            FXCollections.sort(table.getItems(), comparator);
            table.refresh();
        } else if(orderByChBox.getValue().equals("Last Name") && orderModeChBox.getValue().equals("Descending")) {
            Comparator<Artist> comparator = Comparator.comparing(Artist::getLastName);
            comparator = comparator.reversed();
            FXCollections.sort(table.getItems(), comparator);
            table.refresh();
        } else if(orderByChBox.getValue().equals("ID") && orderModeChBox.getValue().equals("Ascending")) {
            Comparator<Artist> comparator = Comparator.comparing(Artist::getId);
            FXCollections.sort(table.getItems(), comparator);
            table.refresh();
        } else if(orderByChBox.getValue().equals("ID") && orderModeChBox.getValue().equals("Descending")) {
            Comparator<Artist> comparator = Comparator.comparing(Artist::getId);
            comparator = comparator.reversed();
            FXCollections.sort(table.getItems(), comparator);
            table.refresh();
        }
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
    private void populateChoiceBoxes(){
        ObservableList<String> orderBy = FXCollections.observableArrayList();
        orderBy.add("ID");
        orderBy.add("Stage Name");
        orderBy.add("First Name");
        orderBy.add("Last Name");
        orderByChBox.setItems(orderBy);
        ObservableList<String> orderMode = FXCollections.observableArrayList();
        orderMode.add("Ascending");
        orderMode.add("Descending");
        orderModeChBox.setItems(orderMode);
    }
}
