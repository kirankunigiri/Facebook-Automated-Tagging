package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.io.*;
import java.util.Scanner;
import java.util.prefs.Preferences;

public class Main extends Application {

    Stage window;
    TableView<Group> table;
    TextField nameInput;
    ObservableList<Group> groups = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Facebook Automated Tagging");

        //Name column
        TableColumn<Group, String> nameColumn = new TableColumn<>("Groups");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("content"));

        //Name input
        nameInput = new TextField();
        nameInput.setPromptText("Name");
        nameInput.setMinWidth(100);
        //Button
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addButtonClicked());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClicked());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(nameInput, addButton, deleteButton);

        table = new TableView<>();
        //table.setItems(getGroups());
        loadGroups();
        table.setItems(groups);

        table.getColumns().addAll(nameColumn);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            //final KeyCombination keyComb = new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_ANY);
            final KeyCombination k1 = new KeyCodeCombination(KeyCode.DIGIT1);;
            final KeyCombination k2 = new KeyCodeCombination(KeyCode.DIGIT2);;
            final KeyCombination k3 = new KeyCodeCombination(KeyCode.DIGIT3);;
            final KeyCombination k4 = new KeyCodeCombination(KeyCode.DIGIT4);;
            final KeyCombination k5 = new KeyCodeCombination(KeyCode.DIGIT5);;
            final KeyCombination k6 = new KeyCodeCombination(KeyCode.DIGIT6);;
            final KeyCombination k7 = new KeyCodeCombination(KeyCode.DIGIT7);;
            final KeyCombination k8 = new KeyCodeCombination(KeyCode.DIGIT8);;
            final KeyCombination k9 = new KeyCodeCombination(KeyCode.DIGIT9);;

            public void handle(KeyEvent ke) {
                if (k1.match(ke)) {
                    Tagger.tagGroup(table.getItems().get(0));
                    ke.consume();
                }
                if (k2.match(ke)) {
                    Tagger.tagGroup(table.getItems().get(1));
                    ke.consume();
                }
                if (k3.match(ke)) {
                    Tagger.tagGroup(table.getItems().get(2));
                    ke.consume();
                }
                if (k4.match(ke)) {
                    Tagger.tagGroup(table.getItems().get(3));
                    ke.consume();
                }
                if (k5.match(ke)) {
                    Tagger.tagGroup(table.getItems().get(4));
                    ke.consume();
                }
                if (k6.match(ke)) {
                    Tagger.tagGroup(table.getItems().get(5));
                    ke.consume();
                }
                if (k7.match(ke)) {
                    Tagger.tagGroup(table.getItems().get(6));
                    ke.consume();
                }
                if (k8.match(ke)) {
                    Tagger.tagGroup(table.getItems().get(7));
                    ke.consume();
                }
                if (k9.match(ke)) {
                    Tagger.tagGroup(table.getItems().get(8));
                    ke.consume();
                }
            }
        });
    }


    //Add button clicked
    public void addButtonClicked(){
        // Limit to 10
        if (groups.size() == 10) {
            return;
        }

        Group group = new Group();
        group.setContent(nameInput.getText());
        table.getItems().add(group);
        nameInput.clear();
        saveGroups();
    }

    //Delete button clicked
    public void deleteButtonClicked(){
        ObservableList<Group> productSelected, allProducts;
        allProducts = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();

        productSelected.forEach(allProducts::remove);
        saveGroups();
    }

    //Get all of the products
    public ObservableList<Group> testGroups(){
        groups.add(new Group("Yusuf, Sahil, Kashyap, Amogh, Avaneesh"));
        groups.add(new Group("Mihir, Avaneesh"));
        groups.add(new Group("Prachin, Mihir, Avaneesh"));
        groups.add(new Group("Sriharsha, Mihir"));
        return groups;
    }

    // Save the groups to a local file
    public void saveGroups() {

        Preferences prefs = Preferences.userNodeForPackage(sample.Main.class);

        for (int i = 0; i < 10; i++) {
            // Remove the previous preference value
            final String PREF_NAME = i + "";
            prefs.remove(PREF_NAME);

            if (i < groups.size()) {
                // If a value exists, update it
                Group group = table.getItems().get(i);
                prefs.put(PREF_NAME, group.getContent());
            } else {
                // If a value does not exist, do nothing
            }

        }

    }

    // Read the chemicals from a local file
    public void loadGroups() {

        Preferences prefs = Preferences.userNodeForPackage(sample.Main.class);

        for (int i = 0; i < 10; i++) {
            // Get the value of the preference;
            // default value is returned if the preference does not exist
            String defaultValue = "";
            String propertyValue = prefs.get(i + "", defaultValue); // "a string"
            if (!propertyValue.isEmpty()) {
                System.out.println(propertyValue);
                Group group= new Group(propertyValue);
                groups.add(group);
            }
        }

    }

}
