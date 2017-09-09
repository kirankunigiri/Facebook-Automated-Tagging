package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.prefs.Preferences;


/**
 * Created by Kiran Kunigiri on 9/7/2017.
 * The Main class for the Facebook
 * Automated Tagger app.
 */
public class Main extends Application {

    Stage window;
    TableView<Group> table;
    TextField nameInput;
    ObservableList<Group> groups = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Create the stage
        window = primaryStage;
        window.setTitle("Facebook Automated Tagger");
        window.setMinWidth(200);
        window.setMinHeight(300);
        window.setWidth(340);
        window.setHeight(500);

        // Table
        table = new TableView<>();

        // Double click feature - tag a group after double-clicking its row
        table.setRowFactory( tv -> {
            TableRow<Group> row = new TableRow<Group>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Tagger.tagGroup(row.getItem());
                }
            });
            return row ;
        });

        // Name column
        TableColumn<Group, String> nameColumn = new TableColumn<>("Groups");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        nameColumn.prefWidthProperty().bind(table.widthProperty().subtract(2));

        // Load group data into tables
        loadGroups();
        table.setItems(groups);
        table.getColumns().add(nameColumn);

        // Name input text field
        nameInput = new TextField();
        nameInput.setPromptText("Name");
        nameInput.prefWidthProperty().bind(table.widthProperty().subtract(130));

        // Buttons - add/delete
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addButtonClicked());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClicked());

        // HBox - contains text field, add/delete buttons
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(nameInput, addButton, deleteButton);

        // VBox - table and HBox
        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox);
        table.prefHeightProperty().bind(vBox.heightProperty().subtract(hBox.getHeight()));

        // Create and load scene
        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();

        // Add shortcut keys 1 - 9 and 0
        // The user can press any of the number keys to trigger an auto tag
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
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


    // Add button clicked - add a group item
    public void addButtonClicked() {
        // Create a new group
        Group group = new Group();
        group.setContent(nameInput.getText());
        table.getItems().add(group);
        nameInput.clear();
        saveGroups();
    }

    // Delete button clicked - remove a group item
    public void deleteButtonClicked() {
        // Get the selected group
        ObservableList<Group> groupSelected, allGroups;
        allGroups = table.getItems();
        groupSelected = table.getSelectionModel().getSelectedItems();

        // Remove the selected group
        groupSelected.forEach(allGroups::remove);
        saveGroups();
    }

    // Save the groups to the preferences
    public void saveGroups() {
        // Get the preference object
        Preferences prefs = Preferences.userNodeForPackage(sample.Main.class);

        // Save all of the existing groups
        for (int i = 0; i < groups.size(); i++) {
            // Remove the previous preference value
            final String PREF_NAME = i + "";
            prefs.remove(PREF_NAME);

            // Get the group and add it to the preferences
            Group group = table.getItems().get(i);
            prefs.put(PREF_NAME, group.getContent());
        }

    }

    // Load all of the group data from the preferences
    public void loadGroups() {
        // Get the preference object
        Preferences prefs = Preferences.userNodeForPackage(sample.Main.class);

        // Add all the existing preference data as groups
        for (int i = 0; i < 10; i++) {
            // The default value is returned if a preference does not exist
            String defaultValue = "";
            // The group content value
            String propertyValue = prefs.get(i + "", defaultValue);
            if (!propertyValue.isEmpty()) {
                // Add the group to the list
                Group group= new Group(propertyValue);
                groups.add(group);
            }
        }

    }

}
