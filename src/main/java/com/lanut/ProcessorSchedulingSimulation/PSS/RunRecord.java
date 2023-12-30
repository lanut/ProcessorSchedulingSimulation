package com.lanut.ProcessorSchedulingSimulation.PSS;

// 运行记录
public class RunRecord {
    private Process process;
    private int startTime; // 开始时间
    private int endTime; // 结束时间
    private int processId; // 进程ID
    private String processName; // 进程名
    private int burstTime; // 程序时间
    private boolean isFinished; // 是否完成

    public RunRecord(Process process ,int startTime, int endTime, int processId, String processName ,int burstTime, boolean isFinished) {
        this.process = process;
        this.startTime = startTime;
        this.endTime = endTime;
        this.processId = processId;
        this.processName = processName;
        this.burstTime = burstTime;
        this.isFinished = isFinished;
    }
    @Override
    public String toString() {
        return "RunRecord{" +
                "开始时间=" + startTime +
                ", 结束时间=" + endTime +
                ", 进程ID=" + processId +
                ", 进程名='" + processName + '\'' +
                ", 程序时间=" + burstTime +
                ", 是否完成=" + isFinished +
                '}';
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
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

    public boolean isFinished() {
        return isFinished;
    }

    public Process getProcess() {
        return process;
    }
}
