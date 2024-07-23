package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("MENU");

        // Crear el texto de instrucciones
        Label instructionsLabel = new Label("Seleccione la opción que desea consultar:");

        // Crear botones
        Button driverPointsChartButton = new Button("TableView Constructor");
        Button driverTableButton = new Button("TableView Drivers");
        Button constructorPointsChartButton = new Button("Puntos Maximo");
        Button constructorTableButton = new Button("Gráfico de Barras - Total de Puntos");
        Button constructorRankChartButton = new Button("Gráfico de Barras - Rank");

        // Configurar acciones para los botones
        driverPointsChartButton.setOnAction(event -> new Main().start(new Stage()));
        driverTableButton.setOnAction(event -> new MainDrivers().start(new Stage()));
        constructorPointsChartButton.setOnAction(event -> new MainMax().start(new Stage()));
        constructorTableButton.setOnAction(event -> new MainPoint().start(new Stage()));
        constructorRankChartButton.setOnAction(event -> new MainDiagramRank().start(new Stage()));

        // Crear el contenedor VBox y agregar elementos
        VBox mainMenu = new VBox(instructionsLabel, driverPointsChartButton, driverTableButton, constructorPointsChartButton, constructorTableButton, constructorRankChartButton);
        mainMenu.setSpacing(10);
        mainMenu.setPadding(new Insets(10));

        // Crear la escena y establecerla en el escenario
        Scene mainScene = new Scene(mainMenu, 400, 300); // Ajusté el tamaño para acomodar el Label
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



