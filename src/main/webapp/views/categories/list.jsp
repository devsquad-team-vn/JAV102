<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- @author Quý (Nhóm PolyCoffee - JAV102) --%>
<jsp:include page="/views/layout/header.jsp"/>

<div class="row g-4">
    <div class="col-12">
        <h4 class="fw-bold text-dark mb-1"><i class="bi bi-tags-fill text-warning"></i> Quản lý Loại đồ uống (Danh mục)</h4>
        <p class="text-muted small mb-0">Thêm mới, cập nhật hoặc ẩn/xóa thông minh loại đồ uống trong hệ thống.</p>
    </div>

    <c:if test="${not empty message}">
        <div class="col-12"><div class="alert alert-success alert-dismissible fade show mb-0"><c:out value="${message}"/><button type="button" class="btn-close" data-bs-dismiss="alert"></button></div></div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="col-12"><div class="alert alert-danger alert-dismissible fade show mb-0"><c:out value="${error}"/><button type="button" class="btn-close" data-bs-dismiss="alert"></button></div></div>
    </c:if>

    <div class="col-lg-4">
        <div class="card card-poly p-3 bg-white">
            <h5 class="fw-bold mb-3">Thông tin loại đồ uống</h5>
            <form method="post">
                <input type="hidden" name="idDanhMuc" value="${category.idDanhMuc}">
                <div class="mb-3">
                    <label class="form-label fw-semibold">Mã danh mục <span class="badge bg-light text-muted border fw-normal">Tự sinh</span></label>
                    <input type="text" class="form-control" name="maDanhMuc" placeholder="Tự sinh tự động khi lưu (VD: DM12345)"
                           value="<c:out value='${category.maDanhMuc}'/>" readonly>
                </div>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Tên loại đồ uống <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" name="tenDanhMuc" required placeholder="Nhập tên loại đồ uống..."
                           value="<c:out value='${category.tenDanhMuc}'/>">
                </div>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Mô tả</label>
                    <textarea class="form-control" name="moTa" rows="2" placeholder="Mô tả ngắn..."><c:out value="${category.moTa}"/></textarea>
                </div>
                <div class="row g-2 mb-3">
                    <div class="col-6">
                        <label class="form-label fw-semibold">Thứ tự hiển thị</label>
                        <input type="number" min="0" class="form-control" name="thuTuHienThi"
                               value="${empty category ? 1 : category.thuTuHienThi}">
                    </div>
                    <div class="col-6">
                        <label class="form-label fw-semibold">Trạng thái</label>
                        <select class="form-select" name="trangThai">
                            <option value="ACTIVE" ${category.trangThai eq 'ACTIVE' or empty category ? 'selected' : ''}>ACTIVE</option>
                            <option value="INACTIVE" ${category.trangThai eq 'INACTIVE' ? 'selected' : ''}>INACTIVE</option>
                        </select>
                    </div>
                </div>
                <div class="d-flex flex-wrap gap-2">
                    <button type="submit" formaction="${pageContext.request.contextPath}/manager/categories/add"
                            class="btn btn-success btn-sm" ${not empty category ? 'disabled' : ''}>
                        <i class="bi bi-plus-circle"></i> Thêm mới
                    </button>
                    <button type="submit" formaction="${pageContext.request.contextPath}/manager/categories/edit"
                            class="btn btn-primary btn-sm" ${empty category ? 'disabled' : ''}>
                        <i class="bi bi-save"></i> Cập nhật
                    </button>
                    <button type="submit" formaction="${pageContext.request.contextPath}/manager/categories/delete"
                            class="btn btn-danger btn-sm" ${empty category ? 'disabled' : ''}
                            onclick="return confirm('Xác nhận xóa hoặc ẩn danh mục này?');">
                        <i class="bi bi-trash"></i> Xóa
                    </button>
                    <a href="${pageContext.request.contextPath}/manager/categories" class="btn btn-secondary btn-sm">
                        <i class="bi bi-arrow-clockwise"></i> Làm mới
                    </a>
                </div>
            </form>
        </div>
    </div>

    <div class="col-lg-8">
        <div class="card card-poly p-3 bg-white">
            <h5 class="fw-bold mb-3">Danh sách loại đồ uống</h5>
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead>
                    <tr>
                        <th>STT</th>
                        <th>Mã</th>
                        <th>Tên loại</th>
                        <th>Số món</th>
                        <th>Thứ tự</th>
                        <th>Trạng thái</th>
                        <th class="text-end">Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="item" varStatus="st">
                        <tr>
                            <td>${st.index + 1}</td>
                            <td><code><c:out value="${item.maDanhMuc}"/></code></td>
                            <td class="fw-semibold"><c:out value="${item.tenDanhMuc}"/></td>
                            <td><span class="badge bg-light text-dark border">${drinkCounts[item.idDanhMuc]}</span></td>
                            <td>${item.thuTuHienThi}</td>
                            <td>
                                <span class="badge ${item.trangThai eq 'ACTIVE' ? 'bg-success' : 'bg-secondary'}">
                                    <c:out value="${item.trangThai}"/>
                                </span>
                            </td>
                            <td class="text-end">
                                <a class="btn btn-sm btn-outline-primary"
                                   href="${pageContext.request.contextPath}/manager/categories?id=${item.idDanhMuc}">
                                    <i class="bi bi-pencil-square"></i> Chỉnh sửa
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty list}">
                        <tr><td colspan="7" class="text-center text-muted py-4">Chưa có danh mục nào.</td></tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/views/layout/footer.jsp"/>
