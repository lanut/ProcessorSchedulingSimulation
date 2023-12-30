package com.lanut.ProcessorSchedulingSimulation.PSS;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static java.lang.Math.min;

public class SchedulingSystem {
    public static final int FCFS = 0; // 先来先服务
    public static final int SJF = 1; // 短作业优先
    public static final int PRIORITY = 2; // 优先级调度
    public static final int SRT = 3; // 最短剩余时间
    public static final int HRRN = 4; // 高响应比优先
    private static final int MAX_TIME = 25565; // 最大时间
    boolean isRR = false; // 是否为时间片轮转算法
    ArrayList<Process> readyBackup; // 就绪队列备份
    ArrayList<Process> ready; // 就绪队列
    ArrayList<Process> running = new ArrayList<Process>(); // 运行队列
    ArrayList<RunRecord> RunRecords = new ArrayList<RunRecord>(); // 记录队列
    private ArrayList<RunRecord> finishedRecord = new ArrayList<RunRecord>(); // 完成队列
    private final int runningLength; // 运行队列长度
    private int currentTime = 0; // 当前时间
    private boolean isDebug; // 是否为调试模式

    // 克隆进程队列
    public static ArrayList<Process> cloneProcesses(ArrayList<Process> processes) {
        ArrayList<Process> cloneProcesses = new ArrayList<Process>();
        for (Process process : processes) {
            cloneProcesses.add(process.clone());
        }
        return cloneProcesses;
    }

    public SchedulingSystem(@NotNull ArrayList<Process> processes, boolean isRR, int runningLength) {
        this.readyBackup = processes;
        ready = cloneProcesses(processes);
        this.isRR = isRR;
        if (runningLength <= 0) {
            runningLength = processes.size(); // 如果运行队列长度小于等于0，则运行队列长度为进程队列长度
        }
        this.runningLength = runningLength;
    }

    public SchedulingSystem(@NotNull ArrayList<Process> processes, boolean isRR) {
        this(processes, isRR, processes.size());
    }

    // 从就绪队列插入到运行队列
    private void insertToRunning() {
        if (this.running.size() < this.runningLength && !this.ready.isEmpty() && currentTime >= this.ready.get(0).getArrivalTime()) {
            // 如果运行队列未满且就绪队列不为空且当前时间大于等于就绪队列中的第一个进程的到达时间
            Process process = this.ready.get(0);
            this.ready.remove(0);
            this.running.add(process);
            process.setStatus(Process.RUNNING);
        }
    }

    // 从运行队列插入到就绪队列
    private void insertToReady() {
        insertToReady(0);
    }

    // 从运行队列插入到就绪队列
    private void insertToReady(int index) {
        if (this.running.isEmpty() || this.ready.size() >= this.runningLength) {
            return;
        }
        Process process = this.running.get(index);
        this.running.remove(index);
        this.ready.add(process);
        process.setStatus(Process.READY);
        // 排序就绪队列
        this.ready.sort((o1, o2) -> {
            if (o1.getArrivalTime() == o2.getArrivalTime()) {
                return o1.getPriority() - o2.getPriority();
            } else {
                return o1.getArrivalTime() - o2.getArrivalTime();
            }
        });
    }

    // 根据调度算法调整进程队列顺序(0: 先来先服务, 1: 短作业优先, 2: 优先级调度, 3: 最短剩余时间)
    private void adjust(int choice) {
        switch (choice) {
            case FCFS:
                Scheduler.FCFS(this.running);
                break;
            case SJF:
                Scheduler.SJF(this.running);
                break;
            case PRIORITY:
                Scheduler.Priority(this.running);
                break;
            case SRT:
                Scheduler.SRT(this.running);
                break;
            case HRRN:
                Scheduler.HRRN(this.running);
                break;
            default:
                break;
        }

    }

    // 系统初始化
    public void init() {
        // 将就绪队列按照到达时间排序
        this.ready.sort((o1, o2) -> {
            if (o1.getArrivalTime() == o2.getArrivalTime()) {
                return o1.getPriority() - o2.getPriority();
            } else {
                return o1.getArrivalTime() - o2.getArrivalTime(); // 按照到达时间排序，先到达的在前面
            }
        });
        // 将就绪队列中的进程插入到运行队列 todo
        this.insertToRunning();

        this.RunRecords.clear(); // 清空运行记录
    }

    // 从就绪队列插入已经到达的进程
    @Deprecated // 已弃用
    public void insertArrivedProcess() {
        for (int i = 0; i < this.readyBackup.size(); i++) {
            Process process = this.readyBackup.get(i);
            if (process.getArrivalTime() <= this.currentTime) {
                this.ready.add(process);
                this.readyBackup.remove(i);
                i--;
            }
        }
        this.ready.sort((o1, o2) -> {
            if (o1.getArrivalTime() == o2.getArrivalTime()) {
                return o1.getPriority() - o2.getPriority();
            } else {
                return o1.getArrivalTime() - o2.getArrivalTime();
            }
        });
    }

    // 按照指定的时间片运行
    @Deprecated // 已弃用
    public void run(int time) {
        // 如果时间片小于等于0或者运行队列为空则返回
        if (time <= 0 || this.running.isEmpty()) {
            return;
        }
        Process process = this.running.get(0); // 获取运行队列中的第一个进程
        int remainingTime = process.getBurstTime() - process.getRemainingTime(); // 还需要运行的时间
        if (remainingTime - time <= 0) { // 如果还需要运行的时间小于时间片
            process.runProcess(remainingTime); // 运行剩余时间
            this.RunRecords.add(new RunRecord(process ,this.currentTime, this.currentTime + remainingTime, process.getId(), process.getName(), process.getBurstTime(), process.isFinished())); // 记录运行时间
            currentTime += remainingTime; // 更新当前时间
            process.finishProcess(this.currentTime); // 完成进程
            this.insertToRunning(); // 将就绪队列中的进程插入到运行队列
            if (!isRR) { // 如果不是时间片轮转算法
                run(time - remainingTime); // 继续运行
            }
        } else { // 如果还需要运行的时间大于时间片
            process.runProcess(time);
            this.RunRecords.add(new RunRecord(process ,this.currentTime, this.currentTime + time, process.getId(), process.getName(), process.getBurstTime(), process.isFinished()));
            currentTime += time;
        }
    }

    // (核心算法)按照指定的时间片运行，如果进程运行完时间未结束，则返回多余的秒数
    public int run(int time, int runningIndex) {
        // 如果时间片小于等于0或者运行队列为空则返回
        if (time <= 0 || this.running.isEmpty()) {
            return time; // 返回多余的时间
        }
        Process process = this.running.get(runningIndex); // 获取运行队列中的进程
        if (!process.isStarted) { // 如果进程未开始运行
            process.isStarted = true; // 设置进程已经开始运行
            process.setStartTime(this.currentTime); // 设置进程开始时间
        }
        int remainingTime = process.getBurstTime() - process.getRemainingTime(); // 还需要运行的时间
        int realityRuntime; // 实际运行时间
        int returnTime; // 需要返回的时间
        if (time >= remainingTime) { // 如果时间片大于等于剩余时间(进程运行完)
            realityRuntime = remainingTime; // 实际运行时间为剩余时间
            returnTime = time - remainingTime; // 返回多余的时间
            process.finishProcess(this.currentTime + realityRuntime); // 完成进程
            this.finishedRecord.add(new RunRecord(process ,process.getStartTime(), this.currentTime + realityRuntime, process.getId(), process.getName(), process.getBurstTime(), process.isFinished())); // 将进程插入到完成队列
            this.running.remove(runningIndex); // 将运行队列中的进程移除

        } else {
            realityRuntime = time;
            returnTime = 0;
        }
        process.runProcess(realityRuntime);
        this.RunRecords.add(new RunRecord(process ,this.currentTime, this.currentTime + realityRuntime, process.getId(), process.getName(), process.getBurstTime(), process.isFinished()));
        currentTime += realityRuntime;
        return returnTime;// 返回多余的时间

    }

    // 插入程序到就绪队列
    public void insertToReady(Program program) {
        Process process = new Process(program, this.currentTime);
        this.ready.add(process);
        this.ready.sort((o1, o2) -> {
            if (o1.getArrivalTime() == o2.getArrivalTime()) {
                return o1.getPriority() - o2.getPriority();
            } else {
                return o1.getArrivalTime() - o2.getArrivalTime();
            }
        });
        this.insertToRunning();
    }

    // 非时间片轮转算法运行完所有进程
    public void runAll(int choice) {
        if (this.isRR) {
            return;
        }
        this.adjust(choice);
        while (!this.running.isEmpty() || !this.ready.isEmpty()) {
            int lastTime = this.run(1, 0);
            insertToRunning(); // 将就绪队列中的进程插入到运行队列
            if (lastTime > 0 && this.running.isEmpty()) {// 如果运行完一个进程后，运行队列为空，则将当前时间加上剩余时间
                if (this.ready.isEmpty()) {
                    this.currentTime += lastTime;
                } else {
                    this.currentTime += min(lastTime, this.ready.get(0).getArrivalTime() - this.currentTime);
                }
            }
            insertToRunning();
            this.adjust(choice);
        }
    }

    // 非时间片轮转算法运行指定时间的队列
    public void runOnce(int choice, int inputTime) {
        if (this.isRR) {
            return;
        }
        this.adjust(choice);
        while (inputTime > 0 && (!this.running.isEmpty() || !this.ready.isEmpty())) { // 运行完一个进程后，运行队列不为空，则继续运行
            int lastTime = this.run(1, 0);
            insertToRunning(); // 将就绪队列中的进程插入到运行队列
            // todo 此处为屎山代码 我已经把lastTime恒等于1或者0了，但是不想改了，上面的函数一样。
            if (lastTime > 0 && this.running.isEmpty()) {// 如果运行完一个进程后，运行队列为空，则将当前时间加上剩余时间
                if (this.ready.isEmpty()) {
                    this.currentTime += lastTime;
                } else {
                    this.currentTime += min(lastTime, this.ready.get(0).getArrivalTime() - this.currentTime);
                }
            }
            insertToRunning();
            this.adjust(choice);
            inputTime -= 1;
        }

        this.insertToRunning();
        this.adjust(choice);
    }


    // 运行完一轮运行队列(时间片轮转算法)
    public void runOnceRR(int choice, int timeSlice) {
        this.insertToRunning();
        if (!this.isRR) {
            return;
        }
        if (this.running.isEmpty()) { // 如果运行队列为空，则将当前时间加上预备队列中的第一个进程的到达时间
            if (this.ready.isEmpty()) {// 如果就绪队列为空，则将当前时间加上时间片
                this.currentTime += timeSlice;
                return;
            }
            this.currentTime += this.ready.get(0).getArrivalTime() - this.currentTime;
        }
        this.adjust(choice);
        // int lastTime; 不需要剩余时间是因为在running函数已经计算好当前的时间了
        for (int i = 0; i < this.running.size(); i++) {
            this.run(timeSlice, i);
        }
        this.insertToRunning();
        this.adjust(choice);
    }


    // 时间片轮转算法运行完所有进程
    public void runAllRR(int choice, int timeSlice) {
        if (!this.isRR) {
            return;
        }
        while (!this.running.isEmpty() || !this.ready.isEmpty()) {
            this.runOnceRR(choice, timeSlice);
            this.insertToRunning();
        }
    }

    public void runOnceRR(int choice, int timeSlice, int inputTime) {
        if (!this.isRR) {
            return;
        }
        this.adjust(choice);
        if (this.running.isEmpty()) {
            this.currentTime += this.ready.get(0).getArrivalTime() - this.currentTime;
            this.insertToRunning(); // 将就绪队列中的进程插入到运行队列
            return;
        }
        while (inputTime != 0) {
            inputTime = this.run(inputTime, 0);// 运行完一个进程后，运行队列不为空，则继续运行
        }

        this.insertToRunning();
        this.adjust(choice);

    }

    // 获取运行记录
    public ArrayList<RunRecord> getRunRecords() {
        return RunRecords;
    }

    // 获取当前时间
    public int getCurrentTime() {
        return currentTime;
    }

    // 获取就绪队列
    public ArrayList<Process> getReady() {
        return ready;
    }

    // 获取运行队列
    public ArrayList<Process> getRunning() {
        return running;
    }

    // 获取备份队列
    public ArrayList<Process> getReadyBackup() {
        return readyBackup;
    }

    // 打印运行记录
    public void printRunRecords() {
        for (RunRecord runRecord : this.RunRecords) {
            System.out.println(runRecord);
        }
    }

    // 打印完成记录
    public void printFinishedRecords() {
        for (RunRecord runRecord : this.finishedRecord) {
            System.out.println(runRecord);
        }
    }

    public ArrayList<RunRecord> getFinishedRecords() {
        return finishedRecord;
    }

}

