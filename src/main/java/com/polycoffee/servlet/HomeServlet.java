package com.polycoffee.servlet;

import com.polycoffee.service.HomeService;
import com.polycoffee.util.AuthUtil;
import com.polycoffee.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet Trang chu PolyCoffee (goi HomeService, khong goi DAO truc tiep).
 *
 * @author Kiệt (Nhóm PolyCoffee - JAV102)
 */
@WebServlet({"/trang-chu", "/home"})
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final HomeService homeService;

    public HomeServlet() {
        this(new HomeService());
    }

    public HomeServlet(HomeService homeService) {
        this.homeService = homeService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("AUTH_ERROR") != null) {
            request.setAttribute("authError", session.getAttribute("AUTH_ERROR"));
            session.removeAttribute("AUTH_ERROR");
        }

        int categoryId = ParamUtil.getInt(request, "categoryId");
        HomeService.HomeViewData data = homeService.loadHomeData(categoryId);

        request.setAttribute("categories", data.getCategories());
        request.setAttribute("drinks", data.getDrinks());
        request.setAttribute("bestSellers", data.getBestSellers());
        request.setAttribute("newDrinks", data.getNewDrinks());
        request.setAttribute("activeVouchers", data.getActiveVouchers());
        request.setAttribute("sizeMap", data.getSizeMap());
        request.setAttribute("toppingsCount", data.getToppingsCount());
        request.setAttribute("selectedCategoryId", categoryId);
        request.setAttribute("isLoggedIn", AuthUtil.isAuthenticated(request));
        request.setAttribute("isManager", AuthUtil.isManager(request));
        request.getRequestDispatcher("/views/home/index.jsp").forward(request, response);
    }
}
