public class Random {
    public interface IRandom {
        double getNext();
    }

    public static class UniformDistribution implements IRandom {
        private final Double min,max;
        private final java.util.Random random;

        public UniformDistribution(Double a, Double b){
            this.min = a;
            this.max = b;
            this.random = new java.util.Random();
        }

        public double getNext() {
            return random.nextDouble()*(max - min )+min;
        }
    }

    public static class NormalDistribution implements IRandom {
        private final java.util.Random random;
        private final Double mean,dev;

        public NormalDistribution(Double mean, Double dev){
            this.mean = mean;
            this.dev = dev;
            this.random = new java.util.Random();
        }

        public double getNext() {
            return random.nextGaussian()*dev + mean;
        }
    }
}
