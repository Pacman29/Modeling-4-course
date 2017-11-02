import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.apache.commons.math3.stat.inference.*;

public class Hi2Criteria implements ICreterea{
    private Integer bottom;
    private Integer top;
    private Map<Integer,Integer> intervals = new HashMap<>();
    private double p;

    @Override
    public double calculate(List<Integer> sequence) {
        intervals.forEach((k,v) -> v = 0);

        for(Integer value : sequence)
            intervals.merge(value,1,(a,b) -> a+b);

        double expected[] = new double[intervals.size()];
        long observed[] = new long[intervals.size()];
        for(int i = 0; i<intervals.size(); ++i) {
            expected[i] = this.p * sequence.size();
            observed[i] = intervals.get((Integer) i);
        }

        ChiSquareTest test = new ChiSquareTest();
        return test.chiSquareTest(expected,observed);
    }

    @Override
    public void setRange(Integer a, Integer b) {
        this.bottom = a;
        this.top = b;

        this.intervals.clear();
        for(Integer i = 0; i <= b; i++)
            intervals.put(i,0);

        this.p = 1./this.intervals.size();
    }

    @Override
    public String name() {
        return "Xi^2";
    }
}
