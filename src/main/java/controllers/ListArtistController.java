package controllers;

import controllers.view.ViewLoader;
import entities.Artist;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import repository.ArtistRepository;
import utils.Alerts;

import java.net.URL;
import java.util.ResourceBundle;

public class ListArtistController implements Initializable {

    private final ArtistRepository artistRepository = new ArtistRepository();
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
