package sample;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public interface ICalculator {
    void calculate(Double math_expectation, Double variance);

    ObservableList<XYChart.Data> getDistribution_density_series();

    ObservableList<XYChart.Data> getDistribution_function_series();
}
