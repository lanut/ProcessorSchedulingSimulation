package com.lanut.ProcessorSchedulingSimulation.PSS;

import java.util.ArrayList;

public class TuiInput {
    public static void main(String[] args) {
        Program program1 = new Program("A应用", 3, 8);
        Program program2 = new Program("B应用", 2, 7);
        Program program3 = new Program("C应用", 2, 6);
        Program program4 = new Program("D应用", 4, 5);
        Program program5 = new Program("E应用", 5, 4);
        Program program6 = new Program("F应用", 6, 3);
        Program program7 = new Program("G应用", 7, 2);
        Program program8 = new Program("H应用", 8, 1);
        Program program9 = new Program("I应用", 9, 0);
        ArrayList<Process> processes = new ArrayList<Process>();
        processes.add(new Process(program1, 0));
        processes.add(new Process(program2, 1));
        processes.add(new Process(program3, 2));
        processes.add(new Process(program4, 3));
        processes.add(new Process(program5, 4));
        processes.add(new Process(program6, 5));
        processes.add(new Process(program7, 6));
        processes.add(new Process(program8, 7));
        processes.add(new Process(program9, 1000));
        SchedulingSystem s1 = new SchedulingSystem(processes, false);
        s1.init();
        s1.runOnce(SchedulingSystem.FCFS, 6);
        System.out.println("s1 run records");
        s1.printRunRecords();
        System.out.println("s1 finished records");
        s1.printFinishedRecords();
        System.out.println("\n--------------------------------------------------\n");
        SchedulingSystem s2 = new SchedulingSystem(processes, true);
        s2.init();
        s2.runAllRR(SchedulingSystem.PRIORITY, 2);
        System.out.println("s2 run records");
        s2.printRunRecords();
        System.out.println("s2 finished records");
        s2.printFinishedRecords();
        System.out.println("\n--------------------------------------------------\n");
        SchedulingSystem s3 = new SchedulingSystem(processes, false, 3);
        s3.init();
        s3.runAll(SchedulingSystem.PRIORITY);
        System.out.println("s3 run records");
        s3.printRunRecords();
        System.out.println("s3 finished records");
        s3.printFinishedRecords();
        System.out.println("\n--------------------------------------------------\n");
    }
}
