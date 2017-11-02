import java.util.ArrayList;
import java.util.List;

public class RandomGeneratorBase {
    private IRandom random;
    private Integer bottom;
    private Integer top;
    private Integer size;
    private List<Integer> randomArray;
    private ICreterea cretereas[] = new ICreterea[] {new CorrelationCriterea(),new DifferenceEnthropyCriterea(),
                                                        new EnthropyCriterea(), new Hi2Criteria()};

    String logTest = "";

    public RandomGeneratorBase(IRandom random, Integer bottom, Integer top) {
        this.random = random;
        this.bottom = bottom;
        this.top = top;

        this.random.setRange(bottom,top);
        for(ICreterea creterea : cretereas){
            creterea.setRange(bottom,top);
        }
    }

    public List<Integer> generateRandomArray(Integer size){
        this.randomArray = new ArrayList<>();
        for(int i = 0; i<size; ++i)
            this.randomArray.add(this.random.getNext());
        this.calculateCriterea(this.randomArray);
        return this.randomArray;
    }

    public List<Integer> getRandomArray() {
        return randomArray;
    }

    public String getTestLog(){
        return this.logTest;
    }

    private String calculateCriterea(List<Integer> sequence){
        this.logTest = "";
        for (ICreterea creterea: cretereas){
            this.logTest += "<br>"+creterea.name() + ": "+ String.format("%.3f",creterea.calculate(sequence))+"</br>";
        }
        return this.logTest;
    }
}
