public class AlgorithmicRandom implements IRandom {
    private int seed = 0;
    private int a = 0;
    private int b = Integer.MAX_VALUE;

    public AlgorithmicRandom(int seed) {
        this.seed = seed;
    }

    public AlgorithmicRandom(){
        this.seed = (int) (System.currentTimeMillis() % 1000);
    }

    public boolean setRange(int a, int b){
        if(a>b)
            return false;
        this.a = a;
        this.b = b;
        return true;
    }

    public int getNext(){
        seed = (seed * 84589 + 45989) % 217728;
        int res = (seed >= 0) ? seed : -seed;
        return res % (b-a) + a;
    }
}
