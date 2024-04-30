package com.example.readstack.client.category;

import com.example.readstack.domain.api.CategoryFullInfo;
import com.example.readstack.domain.api.CategoryService;
import com.example.readstack.domain.api.DiscoveryBasicInfo;
import com.example.readstack.domain.api.DiscoveryService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/category")
public class CategoryController extends HttpServlet {
    private final CategoryService categoryService = new CategoryService();
    private final DiscoveryService discoveryService = new DiscoveryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("id"));
        CategoryFullInfo category = categoryService.findById(categoryId).orElseThrow();
        request.setAttribute("category", category);

        List<DiscoveryBasicInfo> discoveries = discoveryService.findAllByCategory(categoryId);
        request.setAttribute("discoveries", discoveries);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/category.jsp");
        requestDispatcher.forward(request, response);
    }
}
