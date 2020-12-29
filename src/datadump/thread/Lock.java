package datadump.thread;

public class Lock {
    private volatile boolean run = false;

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }
}
