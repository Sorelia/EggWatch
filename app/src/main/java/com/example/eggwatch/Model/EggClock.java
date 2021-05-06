package com.example.eggwatch.Model;

public class EggClock implements IEggClock {

    private long time;
    private long delay;
    private String name;

    public EggClock(long time, long delay, String name){
        this.time = time;
        this.delay = delay;
        this.name = name;
    }

    /// Name says it all, Get's Time and Delay from initiated Egg Clock
    @Override
    public long GetTime() {
        return time;
    }

    @Override
    public long GetDelay() {
        return delay;
    }

    @Override
    public String GetName() { return name; }

}
