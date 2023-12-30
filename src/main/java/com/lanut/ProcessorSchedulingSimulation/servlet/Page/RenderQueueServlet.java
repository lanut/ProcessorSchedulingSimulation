package com.lanut.ProcessorSchedulingSimulation.servlet.Page;

import com.lanut.ProcessorSchedulingSimulation.Entity;
import com.lanut.ProcessorSchedulingSimulation.PSS.Process;
import com.lanut.ProcessorSchedulingSimulation.PSS.SchedulingSystem;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/renderQueue")
public class RenderQueueServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SchedulingSystem schedulingSystem = (SchedulingSystem) req.getSession().getAttribute("schedulingSystem");
        ArrayList<Process> ready = schedulingSystem.getReady();
        ArrayList<Process> running = schedulingSystem.getRunning();
        req.setAttribute("ready", ready);
        req.setAttribute("running", running);
        processTemplate("queue", req, resp);
    }
}
