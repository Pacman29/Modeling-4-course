import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnthropyCriterea implements ICreterea{
    @Override
    public double calculate(List<Integer> sequence) {
        int count = sequence.size();
        if ( count == 0 )
            return 0;

        Map<Integer,Integer> ht = new HashMap();

        sequence.forEach((el) -> ht.merge(el,1,(a,b) -> a+b));

        Double entr = 0.;
        for( Integer value : ht.values())
        {
            double p = value.doubleValue() / count;
            entr -= p * Math.log(p)/Math.log(count);
        }

        return entr;

    }

    @Override
    public void setRange(Integer a, Integer b) {

    }

    @Override
    public String name() {
        return "Энт.";
    }
}
