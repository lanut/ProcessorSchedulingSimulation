package com.lanut.ProcessorSchedulingSimulation.servlet.Page;

import com.lanut.ProcessorSchedulingSimulation.PSS.ProcessStat;
import com.lanut.ProcessorSchedulingSimulation.PSS.RunRecord;
import com.lanut.ProcessorSchedulingSimulation.PSS.SchedulingSystem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/debug")
public class DebugServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        SchedulingSystem schedulingSystem = (SchedulingSystem) session.getAttribute("schedulingSystem");
        if (schedulingSystem == null) {
            resp.sendRedirect("/");
        }
        String isRR = req.getParameter("isRR");
        int timeSlice = Integer.parseInt(req.getParameter("timeSlice"));
        req.setAttribute("timeSlice", timeSlice);
        String algorithm = req.getParameter("algorithm");
        int algorithmIndex = SchedulingSystem.FCFS;
        switch (algorithm) {// 选择算法
            case "FCFS":
                algorithmIndex = SchedulingSystem.FCFS;
                break;
            case "SJF":
                algorithmIndex = SchedulingSystem.SJF;
                break;
            case "Priority":
                algorithmIndex = SchedulingSystem.PRIORITY;
                break;
            case "SRT":
                algorithmIndex = SchedulingSystem.SRT;
                break;
            case "HRRN":
                algorithmIndex = SchedulingSystem.HRRN;
                break;
        }

        if ("on".equals(isRR)) {
            runRR(schedulingSystem, timeSlice, algorithmIndex);
            req.setAttribute("isRR", true);
        } else {
            run(schedulingSystem, timeSlice, algorithmIndex);
            req.setAttribute("isRR", false);
        }
        req.setAttribute("runningQueue", schedulingSystem.getRunning());
        req.setAttribute("readyQueue", schedulingSystem.getReady());
        req.setAttribute("currentTime", schedulingSystem.getCurrentTime());
        req.setAttribute("algorithm", algorithm);


        // 生成进程统计
        ArrayList<ProcessStat> processStats = new ArrayList<>();
        double averageTurnAroundTime = 0; // 平均周转时间
        double averageWeightedTurnAroundTime = 0; // 平均带权周转时间
        for (RunRecord finishedRecord : schedulingSystem.getFinishedRecords()) { // 生成进程统计
            processStats.add(new ProcessStat(finishedRecord.getProcessId(), finishedRecord.getProcessName(), finishedRecord.getProcess().getArrivalTime(), finishedRecord.getStartTime(), finishedRecord.getEndTime(), finishedRecord.getBurstTime()));
            averageTurnAroundTime += processStats.get(processStats.size() - 1).getTurnAroundTime();
            averageWeightedTurnAroundTime += processStats.get(processStats.size() - 1).getWeightedTurnAroundTime();
        }
        averageTurnAroundTime /= schedulingSystem.getFinishedRecords().size(); // 计算平均周转时间
        averageWeightedTurnAroundTime /= schedulingSystem.getFinishedRecords().size(); // 计算平均带权周转时间
        req.setAttribute("processStats", processStats);
        req.setAttribute("averageTurnAroundTime", String.format("%.2f", averageTurnAroundTime));
        req.setAttribute("averageWeightedTurnAroundTime", String.format("%.2f", averageWeightedTurnAroundTime));


        // 生成运行记录
        StringBuffer RunRecords = new StringBuffer();
        for (RunRecord runRecord : schedulingSystem.getRunRecords()) {
            RunRecords.append(runRecord);
            RunRecords.append("\n");
        }

        // 生成完成记录
        StringBuffer FinishedRecords = new StringBuffer();
        for (RunRecord runRecord : schedulingSystem.getFinishedRecords()) {
            FinishedRecords.append(runRecord);
            FinishedRecords.append("\n");
        }
        req.setAttribute("RunRecords", RunRecords);
        req.setAttribute("FinishedRecords", FinishedRecords);

        processTemplate("debug", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void runRR(SchedulingSystem schedulingSystem, int timeSlice, int algorithmIndex) {
        schedulingSystem.runOnceRR(algorithmIndex, timeSlice);
    }

    public void run(SchedulingSystem schedulingSystem, int timeSlice, int algorithmIndex) {
        schedulingSystem.runOnce(algorithmIndex, timeSlice);
    }
}
