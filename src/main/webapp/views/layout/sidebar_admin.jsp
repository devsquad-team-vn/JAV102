<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="navPath" value="${not empty requestScope['jakarta.servlet.forward.servlet_path'] ? requestScope['jakarta.servlet.forward.servlet_path'] : pageContext.request.requestURI}"/>
<c:set var="curRole" value="${sessionScope.user != null ? sessionScope.user.vaiTro : ''}"/>
<aside class="admin-sidebar" id="adminSidebar">
    <div class="admin-sidebar-brand">
        <div class="rounded-3 d-flex align-items-center justify-content-center"
             style="width:38px;height:38px;background:var(--pc-caramel);color:#fff;">
            <i class="bi bi-cup-hot-fill fs-5"></i>
        </div>
        <div>
            <div class="fw-bold fs-5 lh-1 text-white">PolyCoffee</div>
            <div class="small" style="color:#D7C4B6;font-size:0.72rem;">Assignment 1 — RBAC &amp; CRUD</div>
        </div>
    </div>

    <div class="flex-grow-1 pb-4">
        <div class="admin-sidebar-group">Quản trị thực đơn (ADMIN)</div>
        <a href="${ctx}/manager/categories"
           class="admin-nav-link ${navPath.contains('/categories') ? 'active' : ''}">
            <i class="bi bi-tags"></i> <span>Danh mục Đồ uống</span>
            <c:if test="${curRole ne 'ADMIN'}">
                <span class="badge bg-secondary ms-auto" style="font-size:0.65rem;">403</span>
            </c:if>
        </a>
        <a href="${ctx}/manager/toppings"
           class="admin-nav-link ${navPath.contains('/toppings') ? 'active' : ''}">
            <i class="bi bi-plus-circle"></i> <span>Quản lý Topping</span>
            <c:if test="${curRole ne 'ADMIN'}">
                <span class="badge bg-secondary ms-auto" style="font-size:0.65rem;">403</span>
            </c:if>
        </a>

        <div class="admin-sidebar-group">Vận hành Nhân viên (STAFF &amp; ADMIN)</div>
        <a href="${ctx}/employee/workspace"
           class="admin-nav-link ${navPath eq '/employee/workspace' ? 'active' : ''}">
            <i class="bi bi-shield-check"></i> <span>Trung tâm RBAC</span>
        </a>
        <a href="${ctx}/employee/pos"
           class="admin-nav-link ${navPath eq '/employee/pos' ? 'active' : ''}">
            <i class="bi bi-calculator"></i> <span>Quầy POS Bán hàng</span>
        </a>
        <a href="${ctx}/employee/pos/online-orders"
           class="admin-nav-link ${navPath eq '/employee/pos/online-orders' ? 'active' : ''}">
            <i class="bi bi-bell"></i> <span>Xử lý Đơn Online</span>
        </a>
        <a href="${ctx}/employee/shift"
           class="admin-nav-link ${navPath eq '/employee/shift' ? 'active' : ''}">
            <i class="bi bi-clock-history"></i> <span>Phiên ca POS</span>
        </a>
        <a href="${ctx}/employee/my-bills"
           class="admin-nav-link ${navPath eq '/employee/my-bills' ? 'active' : ''}">
            <i class="bi bi-receipt"></i> <span>Đơn cá nhân</span>
        </a>

        <div class="admin-sidebar-group">Cổng Web Khách</div>
        <a href="${ctx}/trang-chu" class="admin-nav-link">
            <i class="bi bi-globe"></i> <span>Xem Trang chủ Khách</span>
        </a>
    </div>
</aside>
