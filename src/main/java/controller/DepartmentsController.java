package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Department;
import repositories.DepartmentRepository;

import java.io.IOException;
import java.util.List;

@WebServlet("/departments")
public class DepartmentsController extends HttpServlet{
    private DepartmentRepository repository;

    public DepartmentsController() {
            repository = new DepartmentRepository();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Department> departments = repository.getDepartments();

        RequestDispatcher dispatcher = request.getRequestDispatcher("/departments.jsp");
        request.setAttribute("departments", departments);

        dispatcher.forward(request, response);
    }

}
