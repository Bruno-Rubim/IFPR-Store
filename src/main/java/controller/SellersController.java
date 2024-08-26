package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Seller;
import repositories.SellerRepository;

import java.io.IOException;
import java.util.List;

@WebServlet("/sellers")
public class SellersController extends HttpServlet{
    private SellerRepository repository;

    public SellersController() {
        repository = new SellerRepository();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Seller> sellers = repository.getSellers();

        RequestDispatcher dispatcher = request.getRequestDispatcher("/sellers.jsp");
        request.setAttribute("sellers", sellers);

        dispatcher.forward(request, response);
    }

}
