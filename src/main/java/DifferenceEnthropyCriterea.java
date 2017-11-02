import java.util.*;

public class DifferenceEnthropyCriterea implements ICreterea {
    @Override
    public double calculate(List<Integer> sequence) {
        int count = sequence.size();
        if(count < 2)
            return 0.;

        List<Integer> diffs = new ArrayList<>();
        for (int i = 0; i<count-1; ++i)
            diffs.add(sequence.get(i+1) - sequence.get(i));

        Map<Integer,Integer> htd = new HashMap();

        diffs.forEach((dif) -> htd.merge(dif,1,(a,b) -> a+b));

        Double dentr = 0.;

        for(Integer value : htd.values()){
            Double p = value.doubleValue() / diffs.size();
            dentr -= p * (Math.log(p)/Math.log(diffs.size()));
        }
        return dentr;
    }

    @Override
    public void setRange(Integer a, Integer b) {

    }

    @Override
    public String name() {
        return "Разн.";
    }
}
