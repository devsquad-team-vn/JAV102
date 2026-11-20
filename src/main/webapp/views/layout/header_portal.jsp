<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="navPath" value="${not empty requestScope['jakarta.servlet.forward.servlet_path'] ? requestScope['jakarta.servlet.forward.servlet_path'] : pageContext.request.requestURI}"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PolyCoffee — Hệ thống Quản lý Quán Cà Phê</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${ctx}/assets/css/global.css" rel="stylesheet">
    <link href="${ctx}/assets/css/portal.css" rel="stylesheet">
</head>
<body class="portal-body">
<nav class="navbar navbar-expand-lg portal-navbar sticky-top">
    <div class="container py-1">
        <a class="navbar-brand portal-brand d-flex align-items-center gap-2" href="${ctx}/trang-chu">
            <span class="rounded-3 d-inline-flex align-items-center justify-content-center"
                  style="width:34px;height:34px;background:var(--pc-espresso);color:#F7C97E;">
                <i class="bi bi-cup-hot-fill"></i>
            </span>
            <span>PolyCoffee</span>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#portalNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="portalNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 gap-1 ms-lg-3">
                <li class="nav-item">
                    <a class="nav-link portal-nav-link ${navPath.contains('/trang-chu') or navPath.contains('/home') ? 'active' : ''}"
                       href="${ctx}/trang-chu"><i class="bi bi-house-door me-1"></i>Trang chủ</a>
                </li>
            </ul>
            <div class="d-flex flex-wrap align-items-center gap-2">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <span class="btn btn-sm bg-caramel-soft border border-warning-subtle text-espresso fw-semibold rounded-pill px-3">
                            <i class="bi bi-person-badge text-caramel me-1"></i>
                            <c:out value="${sessionScope.user.hoTen}"/>
                            <span class="badge text-bg-dark ms-1"><c:out value="${sessionScope.user.vaiTro}"/></span>
                        </span>
                        <c:if test="${sessionScope.user.vaiTro eq 'ADMIN'}">
                            <a href="${ctx}/manager/categories" class="btn btn-sm btn-dark rounded-pill px-3">
                                <i class="bi bi-speedometer2 me-1"></i>Quản trị
                            </a>
                        </c:if>
                        <a href="${ctx}/dang-xuat" class="btn btn-sm btn-outline-danger rounded-pill px-3">
                            <i class="bi bi-box-arrow-right me-1"></i>Đăng xuất
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${ctx}/dang-nhap" class="btn btn-sm btn-coffee rounded-pill px-3">
                            <i class="bi bi-shield-lock me-1"></i>Đăng nhập Nhân viên
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>
<main class="container py-4 flex-grow-1">
