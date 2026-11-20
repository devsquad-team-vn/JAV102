<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="navPath" value="${not empty requestScope['jakarta.servlet.forward.servlet_path'] ? requestScope['jakarta.servlet.forward.servlet_path'] : pageContext.request.requestURI}"/>
<aside class="admin-sidebar" id="adminSidebar">
    <div class="admin-sidebar-brand">
        <div class="rounded-3 d-flex align-items-center justify-content-center"
             style="width:38px;height:38px;background:var(--pc-caramel);color:#fff;">
            <i class="bi bi-cup-hot-fill fs-5"></i>
        </div>
        <div>
            <div class="fw-bold fs-5 lh-1 text-white">PolyCoffee</div>
            <div class="small" style="color:#D7C4B6;font-size:0.72rem;">Backoffice Quản trị</div>
        </div>
    </div>

    <div class="flex-grow-1 pb-4">
        <c:if test="${sessionScope.user.vaiTro eq 'ADMIN'}">
            <div class="admin-sidebar-group">Thực đơn &amp; Danh mục</div>
            <a href="${ctx}/manager/categories"
               class="admin-nav-link ${navPath.contains('/categories') ? 'active' : ''}">
                <i class="bi bi-tags"></i> <span>Danh mục Đồ uống</span>
            </a>
            <a href="${ctx}/manager/toppings"
               class="admin-nav-link ${navPath.contains('/toppings') ? 'active' : ''}">
                <i class="bi bi-plus-circle"></i> <span>Quản lý Topping</span>
            </a>
        </c:if>

        <div class="admin-sidebar-group">Cổng Web Khách</div>
        <a href="${ctx}/trang-chu" class="admin-nav-link">
            <i class="bi bi-globe"></i> <span>Xem Trang chủ Khách</span>
        </a>
    </div>
</aside>
