package com.lanut.ProcessorSchedulingSimulation.servlet.Page;

import com.lanut.ProcessorSchedulingSimulation.Entity;
import com.lanut.ProcessorSchedulingSimulation.PSS.Program;
import com.lanut.ProcessorSchedulingSimulation.PSS.Process;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/")
public class indexServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("name", "处理机调度的模拟实现");
        req.setAttribute("title", "处理机调度");
        req.setAttribute("start", "项目启动");
        ArrayList<Program> programs = Entity.getPrograms();
        req.setAttribute("programs", programs);
        ArrayList<Process> processes = Entity.getProcesses();
        req.setAttribute("processes", processes);
        processTemplate("index", req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Test
    public void test(){
        System.out.println("test");
    }
}
