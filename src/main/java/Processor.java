public class Processor {
    private int maxquesize,requests,queuesize;
    private Random.IRandom random;

    public Processor(Random.IRandom random) {
        this.random = random;
        this.maxquesize = this.requests = this.queuesize = 0;
    }

    public int getMaxquesize() {
        return maxquesize;
    }

    public int getRequests() {
        return requests;
    }

    public int getQueuesize() {
        return queuesize;
    }

    Double getNextProcPeriod(){
        return this.random.getNext();
    }

    public void recieve(){
        this.queuesize++;
        this.requests++;
        if(this.queuesize > this.maxquesize){
            maxquesize++;
        }
    }

    public void process(Double back){
        if(this.queuesize > 0){
            queuesize--;
            double rnd = (new Random.UniformDistribution(0.,1.)).getNext();
            if(rnd <= back)
                this.recieve();
        }
    }
}
