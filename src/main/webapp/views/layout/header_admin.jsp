<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PolyCoffee Backoffice — Hệ thống Quản trị</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${ctx}/assets/css/global.css" rel="stylesheet">
    <link href="${ctx}/assets/css/admin.css" rel="stylesheet">
</head>
<body class="admin-body">
<div class="admin-wrapper">
    <jsp:include page="/views/layout/sidebar_admin.jsp"/>
    <div class="admin-main">
        <header class="admin-topbar">
            <div class="d-flex align-items-center gap-3">
                <button class="btn btn-outline-secondary btn-sm d-lg-none" type="button"
                        onclick="document.getElementById('adminSidebar').classList.toggle('show')">
                    <i class="bi bi-list fs-5"></i>
                </button>
                <div>
                    <div class="fw-bold text-espresso">Không gian Quản trị PolyCoffee</div>
                    <div class="small text-muted">Cửa hàng chính • Giờ mở cửa 07:00 – 22:00</div>
                </div>
            </div>
            <div class="d-flex align-items-center gap-2">
                <a href="${ctx}/trang-chu" class="btn btn-caramel btn-sm">
                    <i class="bi bi-house-door me-1"></i> Trang chủ
                </a>
                <c:if test="${not empty sessionScope.user}">
                    <div class="d-none d-md-flex align-items-center gap-2 px-3 py-1 rounded-pill bg-cream border">
                        <i class="bi bi-person-badge text-coffee"></i>
                        <span class="small fw-semibold"><c:out value="${sessionScope.user.hoTen}"/></span>
                        <span class="badge text-bg-dark"><c:out value="${sessionScope.user.vaiTro}"/></span>
                    </div>
                    <a href="${ctx}/dang-xuat" class="btn btn-outline-danger btn-sm">
                        <i class="bi bi-box-arrow-right"></i> <span class="d-none d-sm-inline">Đăng xuất</span>
                    </a>
                </c:if>
            </div>
        </header>
        <main class="admin-content">
