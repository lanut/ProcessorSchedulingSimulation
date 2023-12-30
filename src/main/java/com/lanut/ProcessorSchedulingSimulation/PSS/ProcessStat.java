package com.lanut.ProcessorSchedulingSimulation.PSS;

public class ProcessStat {
    private int processId;
    private String processName;
    private int burstTime;
    private int arrivalTime;
    private int startTime;
    private int endTime;
    private int turnAroundTime;
    private double weightedTurnAroundTime;

    public ProcessStat(int processId, String processName, int arrivalTime, int startTime, int endTime, int burstTime) {
        this.processId = processId; // 进程ID
        this.processName = processName; // 进程名
        this.arrivalTime = arrivalTime; // 到达时间
        this.startTime = startTime; // 开始时间
        this.endTime = endTime; // 结束时间
        this.burstTime = burstTime; // 运行时间
        this.turnAroundTime = endTime - arrivalTime; // 周转时间
        this.weightedTurnAroundTime = (double) turnAroundTime / burstTime; // 带权周转时间
    }

    @Override
    public String toString() {
        return "ProcessStat{" +
                "进程ID" + processId +
                ", 进程名='" + processName + '\'' +
                ", 到达时间=" + arrivalTime +
                ", 开始时间=" + startTime +
                ", 结束时间=" + endTime +
                ", 周转时间=" + turnAroundTime +
                ", 带权周转时间=" + weightedTurnAroundTime +
                '}';
    }

    public int getProcessId() {
        return processId;
    }

    public String getProcessName() {
        return processName;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public double getWeightedTurnAroundTime() {
        return weightedTurnAroundTime;
    }
}
