package com.lanut.ProcessorSchedulingSimulation.servlet.Page;

import com.lanut.ProcessorSchedulingSimulation.Entity;
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

@WebServlet("/run")
public class RunServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String isRR = req.getParameter("isRR");
        String isDebug = req.getParameter("isDebug");
        boolean isRRBool = false;
        int timeSlice = Integer.parseInt(req.getParameter("timeSlice"));
        int processListLength = Integer.parseInt(req.getParameter("queueSize"));

        if (isRR == null || "off".equals(isRR)) {
            isRRBool = false;
        } else {
            isRRBool = true;
        }
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
        SchedulingSystem schedulingSystem = new SchedulingSystem(Entity.getProcesses(), isRRBool, processListLength);
        if ("on".equals(isDebug)) {
            // 把调度系统放入session
            HttpSession session = req.getSession();
            session.setAttribute("schedulingSystem", schedulingSystem);
            if (isRRBool) {
                resp.sendRedirect("debug?isRR=on" + "&timeSlice=" + timeSlice + "&algorithm=" + algorithm);
            } else {
                resp.sendRedirect("debug?isRR=off" + "&timeSlice=" + timeSlice + "&algorithm=" + algorithm);
            }
            return;
        }
        // 运行
        if (isRRBool) { // 如果是时间片轮转
            schedulingSystem.runAllRR(algorithmIndex, timeSlice);
        } else {
            schedulingSystem.runAll(algorithmIndex);
        }

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

        processTemplate("run", req, resp);
    }
}

