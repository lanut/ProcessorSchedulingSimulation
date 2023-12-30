package com.lanut.ProcessorSchedulingSimulation.PSS;

import java.util.List;


// 通过获得List<Process> 来进行对List排序实现调度
public abstract class Scheduler {
    // 先来先服务
    public static void FCFS(List<Process> processes) {
        processes.sort((o1, o2) -> {
            if (o1.getArrivalTime() == o2.getArrivalTime()) {
                return o1.getPriority() - o2.getPriority(); // 优先级高的先运行
            } else {
                return o1.getArrivalTime() - o2.getArrivalTime(); // 到达时间早的先运行
            }
        });
    }

    // 短作业优先
    public static void SJF(List<Process> processes) {
        processes.sort((o1, o2) -> {
            if (o1.getBurstTime() == o2.getBurstTime()) {
                return o1.getPriority() - o2.getPriority(); // 优先级高的先运行
            } else {
                return o1.getBurstTime() - o2.getBurstTime(); // 运行时间短的先运行
            }
        });
    }

    // 优先级调度
    public static void Priority(List<Process> processes) {
        processes.sort((o1, o2) -> {
            if (o1.getPriority() == o2.getPriority()) {
                return o1.getArrivalTime() - o2.getArrivalTime(); // 到达时间早的先运行
            } else {
                return o1.getPriority() - o2.getPriority(); // 优先级高的先运行
            }
        });
    }

    // 最短剩余时间
    public static void SRT(List<Process> processes) {
        processes.sort((o1, o2) -> {
            if (o1.getRemainingTime() == o2.getRemainingTime()) {
                return o1.getPriority() - o2.getPriority(); // 优先级高的先运行
            } else {
                int o1RemainingTime = o1.getBurstTime() - o1.getRemainingTime();
                int o2RemainingTime = o2.getBurstTime() - o2.getRemainingTime();
                return o1RemainingTime - o2RemainingTime; // 剩余时间短的先运行
            }
        });
    }

    // 高响应比优先
    public static void HRRN(List<Process> processes) {
        processes.sort((o1, o2) -> {
            double o1ResponseRatio = (double) (o1.getWaitingTime() + o1.getBurstTime()) / o1.getBurstTime();
            double o2ResponseRatio = (double) (o2.getWaitingTime() + o2.getBurstTime()) / o2.getBurstTime();
            if (o1ResponseRatio == o2ResponseRatio) {
                return o1.getPriority() - o2.getPriority(); // 优先级高的先运行
            } else {
                return (int) (o1ResponseRatio - o2ResponseRatio); // 响应比高的先运行
            }
        });
    }

}

