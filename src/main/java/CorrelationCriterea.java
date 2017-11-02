import java.util.*;

public class CorrelationCriterea implements ICreterea {

    private Double getMult(List<Double> array, int offset){
        int len = array.size() - offset;
        Double mult = 0.;
        for(int j = 0; j < len; ++j)
            mult += array.get(offset + j)*array.get(j);

        return mult / array.size();
    }

    @Override
    public double calculate(List<Integer> sequence) {
        int count = sequence.size();
        if(count < 3)
            return 0.;

        List<Double> correlation = new ArrayList(count);

        Integer sum = 0;
        for(Integer i : sequence)
            sum += i;

        Double mediana = Double.valueOf(sum)/sequence.size();

        List<Double> normalized = new ArrayList<>();
        for(Integer i : sequence)
            normalized.add(Double.valueOf(i)-mediana);

        Double max = getMult(normalized,0);
        if(max == 0)
            return 0;

        correlation.add(1.);
        for (int i = 1; i<count; ++i)
            correlation.add(getMult(normalized,i)/max);

        return 1 - Math.abs(Collections.max(correlation.subList(1,correlation.size()-1)));
    }

    @Override
    public void setRange(Integer a, Integer b) {

    }

    @Override
    public String name() {
        return "Кор.";
    }
}
