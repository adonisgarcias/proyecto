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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class MainPoint extends Application {

    private ConstructorResultRepository constructorResultRepository = new ConstructorResultRepository();
    private SeasonRepository seasonRepository = new SeasonRepository();
    private BarChart<String, Number> barChart;
    private ComboBox<Integer> comboBox;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Diagrama de Barras - Total Point");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Constructores");
        xAxis.setTickLabelRotation(45); // Rota las etiquetas para evitar superposición

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Puntos");

        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Puntos por Constructor");

        comboBox = new ComboBox<>();
        comboBox.setItems(getSeasons());
        comboBox.setOnAction(event -> {
            Integer selectedYear = comboBox.getValue();
            if (selectedYear != null) {
                updateBarChart(selectedYear);
            }
        });

        // Seleccionar el primer año disponible como predeterminado
        if (!comboBox.getItems().isEmpty()) {
            comboBox.setValue(comboBox.getItems().get(0));
            updateBarChart(comboBox.getItems().get(0));
        }

        VBox vbox = new VBox(comboBox, barChart);
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

    private void updateBarChart(int year) {
        List<ConstructorResult> results = constructorResultRepository.getResultByYear(year);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(String.valueOf(year));

        for (ConstructorResult result : results) {
            series.getData().add(new XYChart.Data<>(result.getConstructorName(), result.getTotalPoints()));
        }

        barChart.getData().clear();
        barChart.layout(); // Asegurarse de que el gráfico se reorganice correctamente
        barChart.getData().add(series);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
