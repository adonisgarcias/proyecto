package application;

import demo_jdbc.models.DriverMaxPoint;
import demo_jdbc.models.DriverResult;
import demo_jdbc.respositories.DriverResultRepository;
import demo_jdbc.respositories.QueryRepository;
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

public class MainMax extends Application {

    private DriverResultRepository driverResultRepository = new DriverResultRepository();
    private QueryRepository queryRepository = new QueryRepository();
    private TableView<DriverResult> driverResultTable = new TableView<>();
    private TableView<DriverMaxPoint> driverMaxPointTable = new TableView<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Puntos Maximo");

        ComboBox<Integer> comboBox = new ComboBox<>();
        comboBox.setItems(getYears());
        comboBox.setOnAction(event -> {
            Integer selectedYear = comboBox.getValue();
            if (selectedYear != null) {
                driverResultTable.setItems(getDriverResultsByYear(selectedYear));
                driverMaxPointTable.setItems(getDriverMaxPoints());  // Cambiado a getDriverMaxPoints
            }
        });

        setupDriverResultTable();
        setupDriverMaxPointTable();

        VBox vbox = new VBox(comboBox, driverResultTable, driverMaxPointTable);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupDriverResultTable() {
        TableColumn<DriverResult, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("driverName"));

        TableColumn<DriverResult, Integer> winsColumn = new TableColumn<>("Victorias");
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));

        TableColumn<DriverResult, Integer> pointsColumn = new TableColumn<>("Puntos Totales");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("totalPoints"));

        TableColumn<DriverResult, Integer> rankColumn = new TableColumn<>("Ranking");
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

        driverResultTable.getColumns().addAll(nameColumn, winsColumn, pointsColumn, rankColumn);
    }

    private void setupDriverMaxPointTable() {
        TableColumn<DriverMaxPoint, String> nameColumn = new TableColumn<>("Nombre del Piloto");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("driverName"));

        TableColumn<DriverMaxPoint, Float> pointsColumn = new TableColumn<>("Puntos");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        driverMaxPointTable.getColumns().addAll(nameColumn, pointsColumn);
    }

    private ObservableList<Integer> getYears() {
        // Agregar los años que necesitas. Aquí hay un ejemplo simple:
        return FXCollections.observableArrayList(1998, 1999, 2000, 2001, 2002);
    }

    private ObservableList<DriverResult> getDriverResultsByYear(int year) {
        ObservableList<DriverResult> results = FXCollections.observableArrayList();
        results.addAll(driverResultRepository.getResultByYear(year));
        return results;
    }

    private ObservableList<DriverMaxPoint> getDriverMaxPoints() {
        ObservableList<DriverMaxPoint> results = FXCollections.observableArrayList();
        results.addAll(queryRepository.getDriversMaxPoints());
        return results;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
