package org.javawebstack.scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Worker extends Work {

    private final List<Work> work = new ArrayList<>();

    public Worker(Work... work) {
        this(Arrays.asList(work));
    }

    public Worker(List<Work> work) {
        addWork(work);
    }

    public Worker addWork(Work... work) {
        return addWork(Arrays.asList(work));
    }

    public Worker addWork(List<Work> work) {
        this.work.addAll(work);
        return this;
    }

    public boolean runOnce() {
        boolean noWork = true;
        for(Work runnable : work) {
            if(runnable.runOnce())
               noWork = false;
        }
        return noWork;
    }

}
