package com.lanut.ProcessorSchedulingSimulation.PSS;

import java.util.Date;

public class Program {
    private String name; // 程序名
    private int burstTime; // 运行时间（秒为单位）
    private int priority; // 优先级 0-10 0为紧急优先级 10为最低优先级
    private long id;

    public Program(String name, int burstTime, int priority) {
        this.name = name;
        this.burstTime = burstTime;
        this.priority = priority;
        id = name.hashCode() + new Date().getTime();
    }

    @Override
    public Program clone() {
        return new Program(this.name, this.burstTime, this.priority);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBurstTime() {
        return burstTime;
    }


    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getId() {
        return id;
    }
}

