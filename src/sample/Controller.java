package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;


public class Controller {
    @FXML private Label label2;
    @FXML private Label label;
    @FXML private ToggleGroup calculate_variance;
    @FXML private TextField series_edit;
    @FXML private TextField variance_edit;
    @FXML private TextField math_expectation_edit;
    @FXML private RadioButton series_rbtn;
    @FXML private RadioButton math_expectation_variance_rbtn;
    @FXML private Button calculate_btn;
    @FXML private LineChart distribution_density_chart;
    @FXML private LineChart distribution_function_chart;

    @FXML
    public void initialize(){
        distribution_function_chart.getStylesheets().add(Main.class.getResource("chart.css").toExternalForm());
        distribution_density_chart.getStylesheets().add(Main.class.getResource("chart.css").toExternalForm());
        series_edit.setDisable(true);
        calculate_variance.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(math_expectation_variance_rbtn.isSelected()){
                    math_expectation_edit.setDisable(false);
                    variance_edit.setDisable(false);
                    series_edit.clear();
                    series_edit.setDisable(true);
                    return;
                }
                if(series_rbtn.isSelected()){
                    series_edit.setDisable(false);
                    math_expectation_edit.clear();
                    variance_edit.clear();
                    variance_edit.setDisable(true);
                    math_expectation_edit.setDisable(true);
                    return;
                }
            }
        });

        variance_edit.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                if (!newValue.matches("(\\d+(\\.\\d{0,})?)"))
                    variance_edit.setText( oldValue);
            }
        });
        variance_edit.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue)
                if (variance_edit.getText().matches("(\\-$)"))
                    variance_edit.clear();
        });

        math_expectation_edit.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                if (!newValue.matches("(\\-$)|(\\-?\\d+(\\.\\d{0,})?)"))
                    math_expectation_edit.setText( oldValue);
            }
        });
        math_expectation_edit.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue)
                if (math_expectation_edit.getText().matches("(\\-$)"))
                    math_expectation_edit.clear();
        });

        series_edit.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                if (!newValue.matches("(\\d|\\.|\\s|\\-)*"))
                    series_edit.setText( oldValue);
            }
        });
        series_edit.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue)
                try{
                    String[] values = series_edit.getText().split(" ");
                    for(int i = 0; i != values.length; ++i)
                        Double.parseDouble(values[i]);
                } catch (NumberFormatException e) {
                    series_edit.clear();
                }
        });

        series_rbtn.setVisible(false);
        math_expectation_variance_rbtn.setVisible(false);
        label.setVisible(false);
        series_edit.setVisible(false);
        label2.setVisible(false);

        ContextMenu contextMenu_distribution_function_chart = new ContextMenu();
        ContextMenu contextMenu_distribution_density_chart = new ContextMenu();

        MenuItem clearItem_distribution_density_chart = new MenuItem("Очистить");
        clearItem_distribution_density_chart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                distribution_density_chart.getData().clear();
            }
        });

        MenuItem clearItem_distribution_function_chart = new MenuItem("Очистить");
        clearItem_distribution_function_chart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                distribution_function_chart.getData().clear();
            }
        });

        contextMenu_distribution_density_chart.getItems().add(clearItem_distribution_density_chart);
        contextMenu_distribution_function_chart.getItems().add(clearItem_distribution_function_chart);

        distribution_density_chart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (MouseButton.SECONDARY.equals(event.getButton())) {
                    contextMenu_distribution_density_chart.show(distribution_density_chart,event.getScreenX(),event.getScreenY());
                }
            }
        });

        distribution_function_chart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (MouseButton.SECONDARY.equals(event.getButton())) {
                    contextMenu_distribution_function_chart.show(distribution_function_chart,event.getScreenX(),event.getScreenY());
                }
            }
        });


    }

    @FXML
    private void onCalculate(){
        if(math_expectation_variance_rbtn.isSelected()){
            Calculator calculator = new Calculator();
            Double m = Double.parseDouble(math_expectation_edit.getText());
            Double d = Double.parseDouble(variance_edit.getText());
            calculator.calculate(m,d);

            XYChart.Series series_density = new XYChart.Series();
            String name = String.format("{M: %.2f ; D: %.2f}",m,d);
            series_density.setName(name);
            XYChart.Series series = new XYChart.Series();
            series.setName(name);

            series_density.setData(calculator.getDistribution_density_series());
            series.setData(calculator.getDistribution_function_series());


            distribution_density_chart.getData().add(series_density);
            distribution_function_chart.getData().add(series);

            for (Object s : distribution_function_chart.getData()) {
                for (Object data :((XYChart.Series) s).getData()) {
                    StackPane stackPane = (StackPane) ((XYChart.Data)data).getNode();
                    stackPane.setVisible(false);
                }
            }

            for (Object s : distribution_density_chart.getData()) {
                for (Object data :((XYChart.Series) s).getData()) {
                    StackPane stackPane = (StackPane) ((XYChart.Data)data).getNode();
                    stackPane.setVisible(false);
                }
            }

            return;
        }
        if(series_rbtn.isSelected()){
            return;
        }
    }
}
