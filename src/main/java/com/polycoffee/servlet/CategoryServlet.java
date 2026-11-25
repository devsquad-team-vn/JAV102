package com.polycoffee.servlet;

import com.polycoffee.entity.DanhMuc;
import com.polycoffee.entity.NhanVien;
import com.polycoffee.service.CategoryService;
import com.polycoffee.service.ServiceResult;
import com.polycoffee.util.AuthUtil;
import com.polycoffee.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Servlet dieu phoi CRUD Loai do uong DANH_MUC (chuan PRG qua CategoryService, khong goi DAO truc tiep).
 *
 * @author Kiệt (Nhóm PolyCoffee - JAV102)
 */
@WebServlet({"/manager/categories", "/manager/categories/add", "/manager/categories/edit", "/manager/categories/delete"})
public class CategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String FLASH_MSG = "CATEGORY_FLASH_MSG";
    private static final String FLASH_ERR = "CATEGORY_FLASH_ERR";
    private static final String FLASH_FORM = "CATEGORY_FLASH_FORM";

    private final CategoryService categoryService;

    public CategoryServlet() {
        this(new CategoryService());
    }

    public CategoryServlet(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        DanhMuc flashForm = null;
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
            if (form instanceof DanhMuc dm) {
                flashForm = dm;
                session.removeAttribute(FLASH_FORM);
            }
        }

        if (flashForm != null) {
            request.setAttribute("category", flashForm);
        } else {
            int id = ParamUtil.getInt(request, "id");
            if (id > 0) {
                request.setAttribute("category", categoryService.findById(id));
            }
        }

        List<DanhMuc> list = categoryService.findAll();
        request.setAttribute("list", list);
        request.setAttribute("drinkCounts", categoryService.getDrinkCounts(list));
        request.getRequestDispatcher("/views/categories/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uriString = request.getRequestURI();
        NhanVien actor = AuthUtil.getUser(request);
        String clientIp = request.getRemoteAddr();
        String redirectPath = "/manager/categories";

        if (uriString.contains("/add")) {
            ServiceResult<DanhMuc> result = categoryService.create(
                    ParamUtil.getString(request, "maDanhMuc"),
                    ParamUtil.getString(request, "tenDanhMuc"),
                    ParamUtil.getString(request, "moTa"),
                    request.getParameter("thuTuHienThi"),
                    ParamUtil.getString(request, "trangThai"),
                    actor,
                    clientIp
            );
            applyFlash(request, result);
        } else if (uriString.contains("/edit")) {
            int id = ParamUtil.getInt(request, "idDanhMuc");
            ServiceResult<DanhMuc> result = categoryService.update(
                    id,
                    ParamUtil.getString(request, "maDanhMuc"),
                    ParamUtil.getString(request, "tenDanhMuc"),
                    ParamUtil.getString(request, "moTa"),
                    request.getParameter("thuTuHienThi"),
                    ParamUtil.getString(request, "trangThai"),
                    actor,
                    clientIp
            );
            applyFlash(request, result);
            if (id > 0 && !"NOT_FOUND".equals(result.getCode())) {
                redirectPath = "/manager/categories?id=" + id;
            }
        } else if (uriString.contains("/delete")) {
            int id = ParamUtil.getInt(request, "idDanhMuc");
            if (id <= 0) {
                id = ParamUtil.getInt(request, "id");
            }
            ServiceResult<Void> result = categoryService.deleteOrDeactivate(id, actor, clientIp);
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
            if (result.getData() instanceof DanhMuc draft) {
                session.setAttribute(FLASH_FORM, draft);
            }
        }
    }
}
