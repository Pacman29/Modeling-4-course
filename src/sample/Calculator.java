package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.apache.commons.math3.special.*;

public class Calculator {
    private ObservableList<XYChart.Data> distribution_density_series = FXCollections.observableArrayList();
    private ObservableList<XYChart.Data> distribution_function_series = FXCollections.observableArrayList();

    public void calculate(Double math_expectation,Double variance){
        for(Double x = math_expectation-10.; x < math_expectation+10.01; x+= 0.2){
            distribution_density_series.add(new XYChart.Data(x, normDensityFunc(math_expectation,variance,x)));
            distribution_function_series.add(new XYChart.Data(x, normFunc(math_expectation,variance,x)));
        }

    }

    public ObservableList<XYChart.Data> getDistribution_density_series() {
        return distribution_density_series;
    }

    public ObservableList<XYChart.Data> getDistribution_function_series() {
        return distribution_function_series;
    }

    private Double normDensityFunc(Double math_expectation, Double variance, Double x){
        return (Math.pow(Math.E, -( (x-math_expectation)*(x-math_expectation)/(2*variance) ))) /(Math.sqrt(2*variance*Math.PI));
    }

    private Double normFunc(Double math_expectation, Double variance, Double x){
        return 0.5 * (1-Erf.erf((math_expectation-x)/Math.sqrt(2*variance)));
    }
}
