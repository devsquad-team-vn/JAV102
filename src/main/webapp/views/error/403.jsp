<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>403 — Không có quyền truy cập | PolyCoffee</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${ctx}/assets/css/global.css" rel="stylesheet">
    <style>
        body {
            min-height: 100vh;
            background: linear-gradient(135deg, #2B1A17 0%, #4E342E 45%, #FAF6F0 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 24px;
        }
        .err-card {
            max-width: 560px;
            width: 100%;
            background: #FFFFFF;
            border-radius: 22px;
            padding: 40px 34px;
            box-shadow: 0 22px 60px rgba(24, 12, 8, 0.35);
            border: 1px solid #E8DCD0;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="err-card">
    <div class="mx-auto mb-3 rounded-circle d-inline-flex align-items-center justify-content-center bg-danger-subtle text-danger"
         style="width:72px;height:72px;">
        <i class="bi bi-shield-lock-fill fs-1"></i>
    </div>
    <div class="text-uppercase small fw-bold text-danger mb-1">HTTP 403 — Forbidden</div>
    <h3 class="fw-bold text-espresso mb-2">Không có quyền truy cập</h3>
    <p class="text-muted mb-3">
        <c:choose>
            <c:when test="${not empty errorMessage}">
                <c:out value="${errorMessage}"/>
            </c:when>
            <c:otherwise>
                Tài khoản hiện tại không đủ quyền hạn để truy cập chức năng này.
            </c:otherwise>
        </c:choose>
    </p>
    <c:if test="${not empty sessionScope.user}">
        <div class="mb-4">
            <span class="badge text-bg-light border text-espresso px-3 py-2">
                <i class="bi bi-person-badge me-1"></i>
                <c:out value="${sessionScope.user.hoTen}"/> (<c:out value="${sessionScope.user.vaiTro}"/>)
            </span>
        </div>
    </c:if>
    <div class="d-flex flex-wrap justify-content-center gap-2">
        <c:if test="${not empty sessionScope.user}">
            <a href="${ctx}/employee/workspace" class="btn btn-coffee px-4">
                <i class="bi bi-shield-check me-1"></i> Về Workspace
            </a>
        </c:if>
        <a href="${ctx}/trang-chu" class="btn btn-outline-secondary px-4">
            <i class="bi bi-house-door me-1"></i> Trang chủ
        </a>
        <a href="${ctx}/dang-nhap" class="btn btn-outline-dark px-4">
            <i class="bi bi-box-arrow-in-right me-1"></i> Đổi tài khoản
        </a>
    </div>
</div>
</body>
</html>
