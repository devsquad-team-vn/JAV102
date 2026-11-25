<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="role" value="${currentUser != null ? currentUser.vaiTro : ''}"/>
<jsp:include page="/views/layout/header.jsp"/>

<div class="d-flex flex-wrap justify-content-between align-items-center gap-3 mb-4">
    <div>
        <span class="badge bg-warning text-dark mb-1">Assignment 1 — RBAC Demo Workspace (2 Role)</span>
        <h3 class="fw-bold text-espresso mb-1">Không gian làm việc &amp; Kiểm thử Phân quyền (ADMIN / STAFF)</h3>
        <p class="text-muted small mb-0">
            Endpoint hiện tại: <code><c:out value="${activePath}"/></code> &mdash; Đã qua kiểm tra tại <code>AuthFilter</code>.
        </p>
    </div>
    <div class="card card-poly px-3 py-2 bg-white">
        <div class="small text-muted">Tài khoản đang đăng nhập</div>
        <div class="fw-bold text-espresso">
            <c:out value="${currentUser.hoTen}"/>
            (<span class="text-caramel"><c:out value="${currentUser.maNhanVien}"/></span>)
            &mdash;
            <span class="badge ${role eq 'ADMIN' ? 'bg-danger' : 'bg-primary'}">
                <c:out value="${role}"/>
            </span>
        </div>
    </div>
</div>

<div class="alert alert-info border-0 shadow-sm d-flex align-items-center gap-3 mb-4">
    <i class="bi bi-diagram-3-fill fs-4 text-info"></i>
    <div class="small">
        <strong>Luồng xử lý &amp; Quy tắc Phiên ca PolyCoffee:</strong><br>
        1) Kiến trúc: <code>Client (JSP/Browser) &rarr; Utf8Filter &rarr; AuthFilter (RBAC) &rarr; Servlet &rarr; Service &rarr; DAO &rarr; SQL Server &rarr; Client</code><br>
        2) Phiên ca POS: Toàn bộ nhân viên dùng role <code>STAFF</code>; máy POS chỉ giữ tối đa <strong>01 phiên ca <code>OPEN</code></strong> tại một thời điểm (người mở ca đứng tên két tiền, mọi đơn do bất kỳ <code>STAFF</code> nào lập đều gắn vào ca đang mở và vẫn lưu rõ <code>DON_HANG.id_nhan_vien</code>).
    </div>
</div>

<div class="card card-poly bg-white mb-4">
    <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
        <h5 class="mb-0 fw-bold text-espresso">
            <i class="bi bi-shield-lock text-caramel me-2"></i>Ma trận kiểm thử phân quyền 1-Click cho Role:
            <span class="text-danger"><c:out value="${role}"/></span>
        </h5>
        <a href="${ctx}/dang-xuat" class="btn btn-sm btn-outline-danger">
            <i class="bi bi-arrow-repeat me-1"></i> Đăng xuất để thử Role khác
        </a>
    </div>
    <div class="table-responsive">
        <table class="table table-hover align-middle mb-0">
            <thead class="table-light">
            <tr>
                <th>Đường dẫn Route</th>
                <th>Mô tả chức năng</th>
                <th>ADMIN (Quản lý)</th>
                <th>STAFF (Nhân viên)</th>
                <th class="text-end">Thử trực tiếp với Role hiện tại</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><code>/manager/categories</code></td>
                <td>CRUD Loại đồ uống (DANH_MUC) — PRG + Validate + Soft/Hard Delete</td>
                <td><span class="badge text-bg-success">200 OK</span></td>
                <td><span class="badge text-bg-danger">403 Forbidden</span></td>
                <td class="text-end">
                    <a href="${ctx}/manager/categories"
                       class="btn btn-sm ${role eq 'ADMIN' ? 'btn-success' : 'btn-outline-danger'}">
                        <c:out value="${role eq 'ADMIN' ? 'Truy cập (200)' : 'Test bị chặn (403)'}"/>
                    </a>
                </td>
            </tr>
            <tr>
                <td><code>/manager/toppings</code></td>
                <td>CRUD Topping (TOPPING) — Kiểm tra ràng buộc hóa đơn</td>
                <td><span class="badge text-bg-success">200 OK</span></td>
                <td><span class="badge text-bg-danger">403 Forbidden</span></td>
                <td class="text-end">
                    <a href="${ctx}/manager/toppings"
                       class="btn btn-sm ${role eq 'ADMIN' ? 'btn-success' : 'btn-outline-danger'}">
                        <c:out value="${role eq 'ADMIN' ? 'Truy cập (200)' : 'Test bị chặn (403)'}"/>
                    </a>
                </td>
            </tr>
            <tr>
                <td><code>/employee/pos</code></td>
                <td>Quầy bán hàng POS</td>
                <td><span class="badge text-bg-success">200 OK</span></td>
                <td><span class="badge text-bg-success">200 OK</span></td>
                <td class="text-end">
                    <a href="${ctx}/employee/pos" class="btn btn-sm btn-success">Truy cập (200)</a>
                </td>
            </tr>
            <tr>
                <td><code>/employee/pos/online-orders</code></td>
                <td>Tiếp nhận &amp; xử lý Đơn Online tại quầy</td>
                <td><span class="badge text-bg-success">200 OK</span></td>
                <td><span class="badge text-bg-success">200 OK</span></td>
                <td class="text-end">
                    <a href="${ctx}/employee/pos/online-orders" class="btn btn-sm btn-success">Truy cập (200)</a>
                </td>
            </tr>
            <tr>
                <td><code>/employee/shift</code></td>
                <td>Quản lý Phiên ca POS (tối đa 01 phiên OPEN trên quầy)</td>
                <td><span class="badge text-bg-success">200 OK</span></td>
                <td><span class="badge text-bg-success">200 OK</span></td>
                <td class="text-end">
                    <a href="${ctx}/employee/shift" class="btn btn-sm btn-success">Truy cập (200)</a>
                </td>
            </tr>
            <tr>
                <td><code>/employee/my-bills</code></td>
                <td>Tra cứu đơn cá nhân trong ca</td>
                <td><span class="badge text-bg-success">200 OK</span></td>
                <td><span class="badge text-bg-success">200 OK</span></td>
                <td class="text-end">
                    <a href="${ctx}/employee/my-bills" class="btn btn-sm btn-success">Truy cập (200)</a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/views/layout/footer.jsp"/>
