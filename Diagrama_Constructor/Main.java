package application;

import demo_jdbc.models.ConstructorResult;
import demo_jdbc.respositories.ConstructorResultRepository;
import demo_jdbc.respositories.SeasonRepository;
import demo_jdbc.models.Season;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private ConstructorResultRepository constructorResultRepository = new ConstructorResultRepository();
    private SeasonRepository seasonRepository = new SeasonRepository();
    private TableView<ConstructorResult> table = new TableView<>();
    private BarChart<String, Number> barChart;
    private Label statusLabel = new Label();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Constructor de Resultados Y Diagrama de Barra segun el Raking");

        ComboBox<Integer> comboBox = new ComboBox<>();
        comboBox.setItems(getSeasons());
        comboBox.setOnAction(event -> {
            Integer selectedYear = comboBox.getValue();
            if (selectedYear != null) {
                statusLabel.setText("Loading...");
                ObservableList<ConstructorResult> results = getConstructorResultsByYear(selectedYear);
                table.setItems(results);
                updateBarChart(results);
                statusLabel.setText("");
            }
        });

        TableColumn<ConstructorResult, String> nameColumn = new TableColumn<>("Constructor Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("constructorName"));

        TableColumn<ConstructorResult, Integer> winsColumn = new TableColumn<>("Wins");
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));

        TableColumn<ConstructorResult, Integer> pointsColumn = new TableColumn<>("Total Points");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("totalPoints"));

        TableColumn<ConstructorResult, Integer> rankColumn = new TableColumn<>("Rank");
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

        table.getColumns().add(nameColumn);
        table.getColumns().add(winsColumn);
        table.getColumns().add(pointsColumn);
        table.getColumns().add(rankColumn);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Constructor");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Season Rank");

        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Season Rank by Constructor");

        VBox vbox = new VBox(comboBox, table, barChart, statusLabel);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 800, 600);
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

    private ObservableList<ConstructorResult> getConstructorResultsByYear(int year) {
        ObservableList<ConstructorResult> results = FXCollections.observableArrayList();
        results.addAll(constructorResultRepository.getResultByYear(year));
        return results;
    }

    private void updateBarChart(ObservableList<ConstructorResult> results) {
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (ConstructorResult result : results) {
            series.getData().add(new XYChart.Data<>(result.getConstructorName(), result.getRank()));
        }
        barChart.getData().add(series);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

