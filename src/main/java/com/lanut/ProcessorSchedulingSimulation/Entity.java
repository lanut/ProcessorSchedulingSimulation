package com.lanut.ProcessorSchedulingSimulation;

import com.lanut.ProcessorSchedulingSimulation.PSS.Process;
import com.lanut.ProcessorSchedulingSimulation.PSS.Program;

import java.util.ArrayList;

public class Entity {
    private static ArrayList<Program> programs = new ArrayList<Program>();
    private static ArrayList<Process> processes = new ArrayList<Process>();

    static { // 示例数据
        programs.add(new Program("A 程序", 3, 8));
        programs.add(new Program("B 程序", 2, 7));
        programs.add(new Program("C 程序", 2, 6));
        programs.add(new Program("D 程序", 4, 5));
        programs.add(new Program("E 程序", 5, 4));
        programs.add(new Program("F 程序", 6, 3));
        programs.add(new Program("G 程序", 7, 2));
        programs.add(new Program("H 程序", 8, 1));
        programs.add(new Program("I 程序", 9, 0));
        processes.add(new Process(programs.get(0), 0));
        processes.add(new Process(programs.get(1), 1));
        processes.add(new Process(programs.get(2), 2));
        processes.add(new Process(programs.get(3), 3));
        processes.add(new Process(programs.get(4), 4));
        processes.add(new Process(programs.get(5), 5));
        processes.add(new Process(programs.get(6), 6));
        processes.add(new Process(programs.get(7), 7));
        processes.add(new Process(programs.get(8), 1000));
    }


    public static void addProgram(String name, int burstTime, int priority) {
        programs.add(new Program(name, burstTime, priority));
        programs.sort((o1, o2) -> {
            return Math.toIntExact(o1.getId() - o2.getId());
        });
    }

    public static void addProcess(Program program, int arriveTime) {
        processes.add(new Process(program, arriveTime));
        processes.sort((o1, o2) -> {
            if (o1.getArrivalTime() == o2.getArrivalTime()) {
                return o1.getPriority() - o2.getPriority(); // 优先级高的先运行
            } else {
                return o1.getArrivalTime() - o2.getArrivalTime(); // 到达时间早的先运行
            }
        });
    }

    public static void deleteProcesses() {
        processes.clear();
    }

    public static void deletePrograms() {
        programs.clear();
    }

    public static void deleteProcess(int index) {
        if (index == -1 || index >= processes.size()) {
            return;
        }
        processes.remove(index);
    }

    public static void deleteProgram(int index) {
        if (index == -1 || index >= programs.size()) {
            return;
        }
        programs.remove(index);
    }

    public static ArrayList<Program> getPrograms() {
        return programs;
    }

    public static ArrayList<Process> getProcesses() {
        return processes;
    }


    public static ArrayList<Process> cloneProcesses() {
        ArrayList<Process> cloneProcesses = new ArrayList<Process>();
        for (Process process : processes) {
            cloneProcesses.add(process.clone());
        }
        return cloneProcesses;
    }

}
