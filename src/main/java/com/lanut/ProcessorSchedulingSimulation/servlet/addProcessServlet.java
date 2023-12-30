package com.lanut.ProcessorSchedulingSimulation.servlet;

import com.lanut.ProcessorSchedulingSimulation.Entity;
import com.lanut.ProcessorSchedulingSimulation.PSS.Program;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet ("/addProcess")
public class addProcessServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int programIndex = Integer.parseInt(req.getParameter("programIndex"));
		int arrivalTime = Integer.parseInt(req.getParameter("arrivalTime"));
	    Program program = Entity.getPrograms().get(programIndex);
		Entity.addProcess(program, arrivalTime);
		resp.sendRedirect("./");
    }
}
