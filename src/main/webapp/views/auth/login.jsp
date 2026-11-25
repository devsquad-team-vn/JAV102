<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- @author Nam (Nhóm PolyCoffee - JAV102) --%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập Nội bộ — PolyCoffee</title>
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
            padding: 20px;
        }
        .staff-auth-shell {
            width: 100%;
            max-width: 920px;
            background: #fff;
            border-radius: 22px;
            overflow: hidden;
            box-shadow: 0 22px 60px rgba(24, 12, 8, 0.35);
            border: 1px solid #E8DCD0;
        }
        .staff-auth-brand {
            background: linear-gradient(160deg, #2B1A17 0%, #58382E 70%, #8C5A2B 100%);
            color: #fff;
            padding: 42px 36px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
        .role-pill {
            cursor: pointer;
            transition: all 0.15s ease;
        }
        .role-pill:hover {
            background-color: var(--pc-caramel-soft) !important;
            border-color: var(--pc-caramel) !important;
        }
    </style>
</head>
<body>
<div class="staff-auth-shell row g-0">
    <div class="col-lg-5 staff-auth-brand">
        <div>
            <div class="d-flex align-items-center gap-2 mb-4">
                <span class="rounded-3 d-inline-flex align-items-center justify-content-center"
                      style="width:42px;height:42px;background:var(--pc-caramel);color:#fff;">
                    <i class="bi bi-cup-hot-fill fs-5"></i>
                </span>
                <div>
                    <div class="fw-bold fs-4 lh-1">PolyCoffee</div>
                    <div class="small opacity-75">Enterprise Coffee POS</div>
                </div>
            </div>
            <h4 class="fw-bold mb-3">Cổng Vận Hành Nội Bộ</h4>
            <p class="small opacity-75 mb-4">
                Đăng nhập bằng tài khoản Quản lý (<code>ADMIN</code>) hoặc Nhân viên vận hành (<code>STAFF</code>) để truy cập hệ thống quản trị &amp; bán hàng PolyCoffee.
            </p>
            <div class="small d-flex flex-column gap-2 opacity-90">
                <div><i class="bi bi-shield-check text-warning me-2"></i>Tự động khóa 15 phút nếu sai mật khẩu 5 lần</div>
                <div><i class="bi bi-journal-check text-warning me-2"></i>Ghi nhận Audit Log &amp; địa chỉ IP đăng nhập</div>
                <div><i class="bi bi-display text-warning me-2"></i>Phân quyền 2 Role: <code>ADMIN</code> (/manager/*) &amp; <code>STAFF</code> (/employee/*)</div>
            </div>
        </div>
        <div class="pt-4 mt-4 border-top border-white border-opacity-10 small opacity-75">
            Quay lại xem thực đơn?
            <a href="${ctx}/trang-chu" class="link-warning fw-semibold text-decoration-none">Về Trang chủ &rarr;</a>
        </div>
    </div>

    <div class="col-lg-7 p-4 p-md-5">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h4 class="fw-bold text-espresso mb-0">Đăng nhập Nhân viên</h4>
            <a href="${ctx}/trang-chu" class="btn btn-sm btn-outline-secondary rounded-pill">
                <i class="bi bi-house-door"></i> Trang chủ
            </a>
        </div>

        <c:if test="${not empty message}">
            <div class="alert alert-danger py-2 small d-flex align-items-center gap-2">
                <i class="bi bi-exclamation-triangle-fill"></i>
                <span><c:out value="${message}"/></span>
            </div>
        </c:if>

        <form action="${ctx}/dang-nhap" method="post" autocomplete="off">
            <div class="mb-3">
                <label class="form-label fw-semibold small text-espresso">Email hoặc Tên đăng nhập</label>
                <div class="input-group">
                    <span class="input-group-text bg-cream"><i class="bi bi-envelope text-coffee"></i></span>
                    <input type="text" id="staffEmailInput" class="form-control" name="email" required
                           placeholder="an.nguyen@polycoffee.vn" value="<c:out value='${param.email}'/>">
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label fw-semibold small text-espresso">Mật khẩu</label>
                <div class="input-group">
                    <span class="input-group-text bg-cream"><i class="bi bi-lock text-coffee"></i></span>
                    <input type="password" id="staffPassInput" class="form-control" name="password" required
                           placeholder="Nhập mật khẩu nhân viên...">
                    <button class="btn btn-outline-secondary" type="button" onclick="toggleStaffPw()">
                        <i class="bi bi-eye" id="staffPwEye"></i>
                    </button>
                </div>
            </div>

            <button type="submit" class="btn btn-coffee w-100 py-2 fw-bold">
                <i class="bi bi-box-arrow-in-right me-1"></i> Đăng nhập vào Hệ thống
            </button>
        </form>

        <div class="mt-4 pt-3 border-top">
            <div class="small text-muted fw-semibold mb-2">Điền nhanh Tài khoản mẫu (Mật khẩu mặc định: <code>123456</code>):</div>
            <div class="d-flex flex-wrap gap-2">
                <button type="button" class="btn btn-sm btn-light border role-pill"
                        onclick="fillRole('an.nguyen@polycoffee.vn', '123456')">
                    <span class="badge text-bg-danger me-1">ADMIN</span> Quản lý An
                </button>
                <button type="button" class="btn btn-sm btn-light border role-pill"
                        onclick="fillRole('binh.tran@polycoffee.vn', '123456')">
                    <span class="badge text-bg-primary me-1">STAFF</span> Nhân viên Bình
                </button>
                <button type="button" class="btn btn-sm btn-light border role-pill"
                        onclick="fillRole('cuong.le@polycoffee.vn', '123456')">
                    <span class="badge text-bg-primary me-1">STAFF</span> Nhân viên Cường
                </button>
            </div>
        </div>
    </div>
</div>
<script>
function fillRole(email, pw) {
    document.getElementById('staffEmailInput').value = email;
    if (pw) document.getElementById('staffPassInput').value = pw;
    document.getElementById('staffPassInput').focus();
}
function toggleStaffPw() {
    const inp = document.getElementById('staffPassInput');
    const eye = document.getElementById('staffPwEye');
    if (inp.type === 'password') {
        inp.type = 'text';
        eye.className = 'bi bi-eye-slash';
    } else {
        inp.type = 'password';
        eye.className = 'bi bi-eye';
    }
}
</script>
</body>
</html>
