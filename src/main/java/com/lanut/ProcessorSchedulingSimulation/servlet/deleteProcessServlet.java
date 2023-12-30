package com.lanut.ProcessorSchedulingSimulation.servlet;

import com.lanut.ProcessorSchedulingSimulation.Entity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet ("/deleteProcess")
public class deleteProcessServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int processIndex = Integer.parseInt(req.getParameter("processIndex"));
	    Entity.deleteProcess(processIndex);
		resp.sendRedirect("./");
    }
}
