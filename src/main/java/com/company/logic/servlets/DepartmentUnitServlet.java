package com.company.logic.servlets;

import com.company.logic.models.DAOInterface;
import com.company.logic.models.DepartmentUnit;
import com.company.logic.models.StaffDepartmentDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(
        name = "DepartmentUnitServlet",
        urlPatterns = {"/unit"}
)
public class DepartmentUnitServlet extends HttpServlet {

    DAOInterface daoInterface = new StaffDepartmentDAO();

    public DepartmentUnitServlet() throws Exception {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("searchAction");
        if (action!=null){
            switch (action) {
                case "searchById":
                    searchUnitById(req, resp);
                    break;
                case "searchByName":
                    searchUnitByName(req, resp);
                    break;
            }
        }else{
            List<DepartmentUnit> result = daoInterface.getDepartmentUnits();
            forwardListUnits(req, resp, result);
        }
    }

    private void searchUnitById(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int idUnit = Integer.parseInt(req.getParameter("idUnit"));
        String operationType = req.getParameter("operation");
        DepartmentUnit unit = null;
        try {
            unit = daoInterface.findDepartmentUnit(idUnit);
        } catch (Exception ex) {
            Logger.getLogger(com.company.logic.servlets.DepartmentUnitServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        req.setAttribute("unit", unit);
        req.setAttribute("action", "add");
        String nextJSP = "/jsp/unit-jsp/new-unit.jsp";

        if(Objects.equals(operationType, "edit")) {
            nextJSP = "/jsp/unit-jsp/edit-unit.jsp";
            req.setAttribute("action", "edit");
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(req, resp);
    }

    private void searchUnitByName(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String unitName = req.getParameter("unitName");

        DepartmentUnit unit = null;
        try {
            unit = daoInterface.findDepartmentUnit(unitName);
        } catch (Exception ex) {
            Logger.getLogger(com.company.logic.servlets.DepartmentUnitServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        req.setAttribute("unit", unit);
        req.setAttribute("action", "edit");
        String nextJSP = "/jsp/unit-jsp/new-unit.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(req, resp);
    }

    private void forwardListUnits(HttpServletRequest req, HttpServletResponse resp, List unitList)
            throws ServletException, IOException {
        String nextJSP = "/jsp/unit-jsp/list-units.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("unitList", unitList);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "add":
                addUnitAction(req, resp);
                break;
            case "edit":
                editUnitAction(req, resp);
                break;
            case "remove":
                removeUnitById(req, resp);
                break;
        }

    }

    private void addUnitAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        daoInterface.addDepartmentUnit(name);

        List<DepartmentUnit> unitList = daoInterface.getDepartmentUnits();
        req.setAttribute("name", name);
        String message = "The new unit has been successfully created.";
        req.setAttribute("message", message);
        forwardListUnits(req, resp, unitList);
    }

    private void editUnitAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("idUnit"));
        String newName = req.getParameter("newName");

        boolean success = daoInterface.setDepartmentUnit(id, newName);

        String message = null;
        if (success) {
            message = "The unit has been successfully updated.";
        }
        List<DepartmentUnit> unitList = daoInterface.getDepartmentUnits();
        req.setAttribute("message", message);
        forwardListUnits(req, resp, unitList);
    }

    private void removeUnitById(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int idUnit = Integer.parseInt(req.getParameter("idUnit"));
        boolean confirm = daoInterface.deleteDepartmentUnit(idUnit);

        if (confirm){
            String message = "The unit has been successfully removed.";
            req.setAttribute("message", message);
        }

        List<DepartmentUnit> unitList = daoInterface.getDepartmentUnits();
        forwardListUnits(req, resp, unitList);
    }

}