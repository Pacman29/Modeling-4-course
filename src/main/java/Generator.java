public class Generator {
    Random.IRandom random;

    public Generator(Random.IRandom random) {
        this.random = random;
    }

    public Double getNextPeriod(){
        return this.random.getNext();
    }
}
