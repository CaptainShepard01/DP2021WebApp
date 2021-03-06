package com.company.logic.servlets;

import com.company.logic.models.Employee;
import com.company.logic.models.DAOInterface;
import com.company.logic.models.StaffDepartmentDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(
        name = "EmployeeServlet",
        urlPatterns = {"/employee"}
)
public class EmployeeServlet extends HttpServlet {

    DAOInterface daoInterface = new StaffDepartmentDAO();

    public EmployeeServlet() throws SQLException, ClassNotFoundException {
        daoInterface.deleteAllEmployees();
        daoInterface.deleteAllUnits();

        daoInterface.addDepartmentUnit("Unit-0");
        daoInterface.addDepartmentUnit("Unit-1");

        daoInterface.addEmployee("Anton", true, 25, "Unit-0");
        daoInterface.addEmployee("Ruslan", true, 25, "Unit-1");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("searchAction");
        if (action!=null){
            switch (action) {
                case "searchById":
                    searchEmployeeById(req, resp);
                    break;
                case "searchByName":
                    searchEmployeeByName(req, resp);
                    break;
            }
        }else{
            List<Employee> result = daoInterface.getEmployees();
            forwardListEmployees(req, resp, result);
        }
    }

    private void searchEmployeeById(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int idEmployee = Integer.parseInt(req.getParameter("idEmployee"));
        String operationType = req.getParameter("operation");
        Employee employee = null;
        try {
            employee = daoInterface.findEmployee(idEmployee);
        } catch (Exception ex) {
            Logger.getLogger(com.company.logic.servlets.EmployeeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        req.setAttribute("employee", employee);
        req.setAttribute("action", "add");
        String nextJSP = "/jsp/employee-jsp/new-employee.jsp";

        if(Objects.equals(operationType, "edit")) {
            nextJSP = "/jsp/employee-jsp/edit-employee.jsp";
            req.setAttribute("action", "edit");
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(req, resp);
    }

    private void searchEmployeeByName(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String employeeName = req.getParameter("employeeName");
        int unitId = Integer.parseInt(req.getParameter("unitId"));

        String unitName = daoInterface.findDepartmentUnit(unitId).getName();
        Employee employee = null;
        try {
            employee = daoInterface.findEmployee(employeeName, unitName);
        } catch (Exception ex) {
            Logger.getLogger(com.company.logic.servlets.EmployeeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        req.setAttribute("employee", employee);
        req.setAttribute("action", "edit");
        String nextJSP = "/jsp/employee-jsp/new-employee.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(req, resp);
    }

    private void forwardListEmployees(HttpServletRequest req, HttpServletResponse resp, List employeeList)
            throws ServletException, IOException {
        String nextJSP = "/jsp/employee-jsp/list-employees.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("employeeList", employeeList);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "add":
                addEmployeeAction(req, resp);
                break;
            case "edit":
                editEmployeeAction(req, resp);
                break;
            case "remove":
                removeEmployeeById(req, resp);
                break;
        }

    }

    private void addEmployeeAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        boolean isDepartmentHead = Boolean.parseBoolean(req.getParameter("isDepartmentHead"));
        int lengthOfEmployment = Integer.parseInt(req.getParameter("lengthOfEmployment"));
        int unitId = Integer.parseInt(req.getParameter("unitId"));

        String unitName = daoInterface.findDepartmentUnit(unitId).getName();

        daoInterface.addEmployee(name, isDepartmentHead, lengthOfEmployment, unitName);
        List<Employee> employeeList = daoInterface.getEmployees();
        req.setAttribute("name", name);
        String message = "The new employee has been successfully created.";
        req.setAttribute("message", message);
        forwardListEmployees(req, resp, employeeList);
    }

    private void editEmployeeAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        int unitId = Integer.parseInt(req.getParameter("unitId"));
        String newName = req.getParameter("newName");

        String unitName = daoInterface.findDepartmentUnit(unitId).getName();
        boolean success = daoInterface.setEmployee(name, unitName, newName, false);

        String message = null;
        if (success) {
            message = "The employee has been successfully updated.";
        }
        List<Employee> employeeList = daoInterface.getEmployees();
        req.setAttribute("message", message);
        forwardListEmployees(req, resp, employeeList);
    }

    private void removeEmployeeById(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int idEmployee = Integer.parseInt(req.getParameter("idEmployee"));
        boolean confirm = daoInterface.deleteEmployee(idEmployee);

        if (confirm){
            String message = "The employee has been successfully removed.";
            req.setAttribute("message", message);
        }

        List<Employee> employeeList = daoInterface.getEmployees();
        forwardListEmployees(req, resp, employeeList);
    }

}