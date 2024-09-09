package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Department;
import repositories.DepartmentRepository;

import java.io.IOException;

@WebServlet("/departments/update")
public class DepartmentUpdateController extends HttpServlet {
    DepartmentRepository repository = new DepartmentRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Integer id = Integer.valueOf(request.getParameter("id"));
        Department department = repository.getById(id);
        request.setAttribute("department", department);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/departments/update.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Integer id = Integer.valueOf(request.getParameter("field_id"));
        String name = request.getParameter("field_name");

        Department department = new Department();
        department.setId(id);
        department.setName(name);

        repository.update(department);

        response.sendRedirect(request.getContextPath() + "/departments");
    }
}
