package controllers;

import entities.Music;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewSongsController implements Initializable {

    @FXML private TextField stageName;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private Button closeButton;

    @FXML private TableView<Music> table;

    private final ObservableList<Music> songList = FXCollections.observableArrayList();

    public void setSongList(List<Music> list) {
        this.songList.addAll(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureTable();
        populateTable();

    }

    public void setData(String artistStageName, String artistFirstName, String artistLastName){
        stageName.setText(artistStageName);
        firstName.setText(artistFirstName);
        lastName.setText(artistLastName);
    }

    @FXML
    public void okButton() throws IOException {
        Stage stage = (Stage)closeButton.getScene().getWindow(); stage.close();
    }

    private void configureTable() {
        TableColumn<Music, Integer> column1 = new TableColumn<>("Id");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column1.prefWidthProperty().bind(table.widthProperty().multiply(0.05));
        column1.setResizable(false);

        TableColumn<Music, String> column2 = new TableColumn<>("Genre");
        column2.setCellValueFactory(new PropertyValueFactory<>("genre"));
        column2.prefWidthProperty().bind(table.widthProperty().multiply(0.35));
        column2.setResizable(false);

        TableColumn<Music, String> column3 = new TableColumn<>("Title");
        column3.setCellValueFactory(new PropertyValueFactory<>("title"));
        column3.prefWidthProperty().bind(table.widthProperty().multiply(0.6));
        column3.setResizable(false);

        table.getColumns().add(column1);
        table.getColumns().add(column2);
        table.getColumns().add(column3);
    }

    private void populateTable() {
        table.setItems(songList);
    }

}
