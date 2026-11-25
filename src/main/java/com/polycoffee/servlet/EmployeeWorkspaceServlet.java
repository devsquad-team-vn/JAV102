package com.polycoffee.servlet;

import com.polycoffee.entity.NhanVien;
import com.polycoffee.util.AuthUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet khong gian lam viec theo Role cho Assignment 1:
 * Phuc vu minh hoa truc quan phan quyen 2 Role ADMIN / STAFF khi demo.
 *
 * @author Khoa (Nhóm PolyCoffee - JAV102)
 */
@WebServlet({
        "/employee/workspace",
        "/employee/pos",
        "/employee/pos/online-orders",
        "/employee/shift",
        "/employee/my-bills"
})
public class EmployeeWorkspaceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NhanVien user = AuthUtil.getUser(request);
        String activePath = request.getServletPath() != null ? request.getServletPath() : "/employee/workspace";
        request.setAttribute("currentUser", user);
        request.setAttribute("activePath", activePath);
        request.getRequestDispatcher("/views/employee/workspace.jsp").forward(request, response);
    }
}