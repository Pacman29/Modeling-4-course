public class Modeling {
    public static int MethodDT(Generator generator, Processor processor, int count, double back, double dt){
        double gen_next = generator.getNextPeriod();
        double pr_next = gen_next + processor.getNextProcPeriod();
        for(double cur_time = 0; count > 0; cur_time +=dt){
            if(gen_next <= cur_time){
                processor.recieve();
                gen_next += generator.getNextPeriod();
                count--;
            }
            if( cur_time >= pr_next){
                if(processor.getQueuesize() > 0)
                    processor.process(back);
                if(processor.getQueuesize() > 0)
                    pr_next += processor.getNextProcPeriod();
                else
                    pr_next = gen_next + processor.getNextProcPeriod();
            }
        }
        return processor.getMaxquesize();
    }

    public static int MethodEvent(Generator generator, Processor processor, int count, double back, double dt){
        double gen_next = generator.getNextPeriod();
        double pr_next = gen_next + processor.getNextProcPeriod();
        while (count > 0){
            if(gen_next <= pr_next){
                processor.recieve();
                gen_next += generator.getNextPeriod();
                count--;
            }
            if( gen_next >= pr_next){
                if(processor.getQueuesize() > 0)
                    processor.process(back);
                if(processor.getQueuesize() > 0)
                    pr_next += processor.getNextProcPeriod();
                else
                    pr_next = gen_next + processor.getNextProcPeriod();
            }
        }
        return processor.getMaxquesize();
    }
}
