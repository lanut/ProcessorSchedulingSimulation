package com.lanut.ProcessorSchedulingSimulation.servlet;

import com.lanut.ProcessorSchedulingSimulation.Entity;
import com.lanut.ProcessorSchedulingSimulation.PSS.Process;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet ("/deleteProgram")
public class deleteProgramServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int programIndex = Integer.parseInt(req.getParameter("programIndex"));
	    Entity.deleteProgram(programIndex);
        long programId = Long.parseLong(req.getParameter("programId"));
        ArrayList<Process> processes = Entity.getProcesses();
        for (int i = 0; i < processes.size(); i++) {
            if (processes.get(i).getProgram().getId() == programId) {
                Entity.deleteProcess(i);
                i--;
            }
        }


		resp.sendRedirect("./");
    }
}
