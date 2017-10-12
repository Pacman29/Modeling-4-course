package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.apache.commons.math3.special.Erf;

public class RCalculator implements ICalculator {
    private ObservableList<XYChart.Data> distribution_density_series = FXCollections.observableArrayList();
    private ObservableList<XYChart.Data> distribution_function_series = FXCollections.observableArrayList();


    @Override
    public void calculate(Double math_expectation, Double variance) {
        for(Double x = math_expectation; x < variance+0.001; x+= (variance - math_expectation)/1000){
            distribution_density_series.add(new XYChart.Data(x, rDensityFunc(math_expectation,variance,x)));
            distribution_function_series.add(new XYChart.Data(x, rFunc(math_expectation,variance,x)));
        }
    }

    @Override
    public ObservableList<XYChart.Data> getDistribution_density_series() {
        return distribution_density_series;
    }

    @Override
    public ObservableList<XYChart.Data> getDistribution_function_series() {
        return distribution_function_series;
    }

    private Double rDensityFunc(Double math_expectation, Double variance, Double x) {
        Double a = math_expectation;
        Double b = variance;
        if(x < a || x > b){
            return 0.;
        }

        return 1/(b-a);
    }

    private Double rFunc(Double math_expectation, Double variance, Double x){
        Double a = math_expectation;
        Double b = variance;
        if(x < a){
            return 0.;
        }
        if(x >= b ){
            return 1.;
        }

        return (x-a)/(b-a);
    }
}
