package com.polycoffee.servlet;

import com.polycoffee.entity.NhanVien;
import com.polycoffee.entity.Topping;
import com.polycoffee.service.ServiceResult;
import com.polycoffee.service.ToppingService;
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
 * Servlet dieu phoi CRUD Topping (chuan PRG qua ToppingService, khong goi DAO truc tiep).
 *
 * @author Quý (Nhóm PolyCoffee - JAV102)
 */
@WebServlet({"/manager/toppings", "/manager/toppings/add", "/manager/toppings/edit", "/manager/toppings/delete"})
public class ToppingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String FLASH_MSG = "TOPPING_FLASH_MSG";
    private static final String FLASH_ERR = "TOPPING_FLASH_ERR";
    private static final String FLASH_FORM = "TOPPING_FLASH_FORM";

    private final ToppingService toppingService;

    public ToppingServlet() {
        this(new ToppingService());
    }

    public ToppingServlet(ToppingService toppingService) {
        this.toppingService = toppingService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Topping flashForm = null;
        if (session != null) {
            Object msg = session.getAttribute(FLASH_MSG);
            if (msg != null) {
                request.setAttribute("message", msg);
                session.removeAttribute(FLASH_MSG);
            }
            Object err = session.getAttribute(FLASH_ERR);
            if (err != null) {
                request.setAttribute("error", err);
                session.removeAttribute(FLASH_ERR);
            }
            Object form = session.getAttribute(FLASH_FORM);
            if (form instanceof Topping tp) {
                flashForm = tp;
                session.removeAttribute(FLASH_FORM);
            }
        }

        if (flashForm != null) {
            request.setAttribute("topping", flashForm);
        } else {
            int id = ParamUtil.getInt(request, "id");
            if (id > 0) {
                request.setAttribute("topping", toppingService.findById(id));
            }
        }
        request.setAttribute("list", toppingService.findAll());
        request.getRequestDispatcher("/views/topping/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        NhanVien actor = AuthUtil.getUser(request);
        String clientIp = request.getRemoteAddr();
        String redirectPath = "/manager/toppings";

        if (uri.contains("/add")) {
            ServiceResult<Topping> result = toppingService.create(
                    ParamUtil.getString(request, "maTopping"),
                    ParamUtil.getString(request, "tenTopping"),
                    request.getParameter("giaTopping"),
                    ParamUtil.getString(request, "trangThai"),
                    actor,
                    clientIp
            );
            applyFlash(request, result);
        } else if (uri.contains("/edit")) {
            int id = ParamUtil.getInt(request, "idTopping");
            ServiceResult<Topping> result = toppingService.update(
                    id,
                    ParamUtil.getString(request, "maTopping"),
                    ParamUtil.getString(request, "tenTopping"),
                    request.getParameter("giaTopping"),
                    ParamUtil.getString(request, "trangThai"),
                    actor,
                    clientIp
            );
            applyFlash(request, result);
            if (id > 0 && !"NOT_FOUND".equals(result.getCode())) {
                redirectPath = "/manager/toppings?id=" + id;
            }
        } else if (uri.contains("/delete")) {
            int id = ParamUtil.getInt(request, "idTopping");
            if (id <= 0) {
                id = ParamUtil.getInt(request, "id");
            }
            ServiceResult<Void> result = toppingService.deleteOrDeactivate(id, actor, clientIp);
            applyFlash(request, result);
        }

        String ctx = request.getContextPath() != null ? request.getContextPath() : "";
        response.sendRedirect(ctx + redirectPath);
    }

    private <T> void applyFlash(HttpServletRequest request, ServiceResult<T> result) {
        HttpSession session = request.getSession(true);
        if (result.isSuccess()) {
            session.setAttribute(FLASH_MSG, result.getMessage());
        } else {
            session.setAttribute(FLASH_ERR, result.getMessage());
            if (result.getData() instanceof Topping draft) {
                session.setAttribute(FLASH_FORM, draft);
            }
        }
    }
}
