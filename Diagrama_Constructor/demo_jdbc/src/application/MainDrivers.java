package application;

import demo_jdbc.models.DriverResult;
import demo_jdbc.respositories.DriverResultRepository;
import demo_jdbc.respositories.SeasonRepository;
import demo_jdbc.models.Season;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainDrivers extends Application {

    private DriverResultRepository driverResultRepository = new DriverResultRepository();
    private SeasonRepository seasonRepository = new SeasonRepository();
    private TableView<DriverResult> table = new TableView<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tabla de Driver ");

        ComboBox<Integer> comboBox = new ComboBox<>();
        comboBox.setItems(getSeasons());
        comboBox.setOnAction(event -> {
            Integer selectedYear = comboBox.getValue();
            if (selectedYear != null) {
                table.setItems(getDriverResultsByYear(selectedYear));
            }
        });

        TableColumn<DriverResult, String> nameColumn = new TableColumn<>("Driver Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("driverName"));

        TableColumn<DriverResult, Integer> winsColumn = new TableColumn<>("Wins");
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));

        TableColumn<DriverResult, Integer> pointsColumn = new TableColumn<>("Total Points");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("totalPoints"));

        TableColumn<DriverResult, Integer> rankColumn = new TableColumn<>("Rank");
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

        table.getColumns().add(nameColumn);
        table.getColumns().add(winsColumn);
        table.getColumns().add(pointsColumn);
        table.getColumns().add(rankColumn);

        VBox vbox = new VBox(comboBox, table);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ObservableList<Integer> getSeasons() {
        ObservableList<Integer> seasons = FXCollections.observableArrayList();
        for (Season season : seasonRepository.getSeasons()) {
            seasons.add(season.getYear());
        }
        return seasons;
    }

    private ObservableList<DriverResult> getDriverResultsByYear(int year) {
        ObservableList<DriverResult> results = FXCollections.observableArrayList();
        results.addAll(driverResultRepository.getResultByYear(year));
        return results;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
