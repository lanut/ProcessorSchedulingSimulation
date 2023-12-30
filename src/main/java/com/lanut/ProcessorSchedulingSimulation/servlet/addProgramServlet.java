package com.lanut.ProcessorSchedulingSimulation.servlet;

import com.lanut.ProcessorSchedulingSimulation.Entity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addProgram")
public class addProgramServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        int burstTime = Integer.parseInt(req.getParameter("burstTime"));
        int priority = Integer.parseInt(req.getParameter("priority"));
        Entity.addProgram(name, burstTime, priority);
        resp.sendRedirect("./");
    }
}
