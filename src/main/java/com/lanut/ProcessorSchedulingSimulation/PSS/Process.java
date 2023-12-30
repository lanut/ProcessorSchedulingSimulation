package com.lanut.ProcessorSchedulingSimulation.PSS;

public class Process {
    public static final int READY = 0;
    public static final int RUNNING = 1;
    public static final int FINISHED = 2;
    public static final int SUSPENDED = 3;
    // 是否开始
    public boolean isStarted = false;
    // 进程ID
    private int id;
    private Program program;
    // 运行状态
    private int status = READY; // 0: 就绪, 1: 运行, 2: 完成, 3: 挂起
    // 到达时间
    private int arrivalTime;
    // 开始时间
    private int startTime = 0;
    // 运行时间
    private int remainingTime = 0;
    // 完成时间
    private int finishTime = 0;


    // 创建运行进程
    public Process(Program program, int arrivalTime) {
        this.program = program;
        this.id = program.getName().hashCode() + arrivalTime;
        this.arrivalTime = arrivalTime;
    }

    // 运行进程
    public void runProcess(int time) {
        System.out.println("Running process " + this.getName());
        this.setRemainingTime(this.getRemainingTime() + time);
    }

    // 判断进程是否完成
    public boolean isFinished() {
        return this.getStatus() == FINISHED;
    }

    // 完成进程
    public void finishProcess(int finishTime) {
        this.setStatus(FINISHED);
        this.finishTime = finishTime;
    }

    // 实现克隆接口
    @Override
    public Process clone() {
        Process process = new Process(this.program, this.arrivalTime);
        process.status = this.status;
        process.remainingTime = this.remainingTime;
        return process;
    }

    // 重写toString方法
    @Override
    public String toString() {
        return "Process " + this.getName() + " status: " + this.getStatus() + " arrivalTime: " + this.getArrivalTime() + " remainingTime: " + this.getRemainingTime() + "\n";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String getName() {
        return program.getName();
    }

    public void setName(String name) {
        this.program.setName(name);
    }

    public int getBurstTime() {
        return program.getBurstTime();
    }


    public int getPriority() {
        return program.getPriority();
    }

    public void setPriority(int priority) {
        this.program.setPriority(priority);
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public Program getProgram() {
        return program;
    }

    public Double getWaitingTime() {
        return (double) (this.getStartTime() - this.getArrivalTime());
    }
}
