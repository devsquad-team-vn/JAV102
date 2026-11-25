<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%-- @author Quý (Nhóm PolyCoffee - JAV102) - Quan ly Topping (Assignment 1) --%>
<jsp:include page="/views/layout/header.jsp"/>

<div class="row g-4">
    <div class="col-12">
        <h4 class="fw-bold text-dark mb-1"><i class="bi bi-cup-straw text-warning"></i> Quản lý Topping</h4>
        <p class="text-muted small mb-0">Thêm mới, cập nhật giá và trạng thái còn hàng/tạm hết của các loại Topping.</p>
    </div>

    <c:if test="${not empty message}">
        <div class="col-12"><div class="alert alert-success mb-0"><c:out value="${message}"/></div></div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="col-12"><div class="alert alert-danger mb-0"><c:out value="${error}"/></div></div>
    </c:if>

    <div class="col-lg-4">
        <div class="card card-poly p-3 bg-white">
            <h5 class="fw-bold mb-3">Thông tin Topping</h5>
            <form method="post">
                <input type="hidden" name="idTopping" value="${topping.idTopping}">
                <div class="mb-3">
                    <label class="form-label fw-semibold">Mã Topping <span class="badge bg-light text-muted border fw-normal">Tự sinh</span></label>
                    <input type="text" class="form-control" name="maTopping" placeholder="Tự sinh tự động khi lưu (VD: TP12345)"
                           value="<c:out value='${topping.maTopping}'/>" readonly>
                </div>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Tên Topping <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" name="tenTopping" required
                           value="<c:out value='${topping.tenTopping}'/>">
                </div>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Giá bán thêm (VNĐ) <span class="text-danger">*</span></label>
                    <input type="number" min="0" step="1000" class="form-control" name="giaTopping" required
                           value="${empty topping ? 10000 : topping.giaTopping}">
                </div>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Trạng thái</label>
                    <select class="form-select" name="trangThai">
                        <option value="AVAILABLE" ${topping.trangThai eq 'AVAILABLE' or empty topping ? 'selected' : ''}>AVAILABLE</option>
                        <option value="OUT_OF_STOCK" ${topping.trangThai eq 'OUT_OF_STOCK' ? 'selected' : ''}>OUT_OF_STOCK</option>
                        <option value="INACTIVE" ${topping.trangThai eq 'INACTIVE' ? 'selected' : ''}>INACTIVE</option>
                    </select>
                </div>
                <div class="d-flex flex-wrap gap-2">
                    <button type="submit" formaction="${pageContext.request.contextPath}/manager/toppings/add"
                            class="btn btn-success btn-sm" ${not empty topping ? 'disabled' : ''}>Thêm mới</button>
                    <button type="submit" formaction="${pageContext.request.contextPath}/manager/toppings/edit"
                            class="btn btn-primary btn-sm" ${empty topping ? 'disabled' : ''}>Cập nhật</button>
                    <button type="submit" formaction="${pageContext.request.contextPath}/manager/toppings/delete"
                            class="btn btn-danger btn-sm" ${empty topping ? 'disabled' : ''}
                            onclick="return confirm('Xác nhận xóa Topping này?');">Xóa</button>
                    <a href="${pageContext.request.contextPath}/manager/toppings" class="btn btn-secondary btn-sm">Làm mới</a>
                </div>
            </form>
        </div>
    </div>

    <div class="col-lg-8">
        <div class="card card-poly p-3 bg-white">
            <h5 class="fw-bold mb-3">Danh sách Topping</h5>
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead>
                    <tr>
                        <th>STT</th>
                        <th>Mã</th>
                        <th>Tên Topping</th>
                        <th>Giá (VNĐ)</th>
                        <th>Trạng thái</th>
                        <th class="text-end">Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="tp" varStatus="st">
                        <tr>
                            <td>${st.index + 1}</td>
                            <td><code><c:out value="${tp.maTopping}"/></code></td>
                            <td class="fw-semibold"><c:out value="${tp.tenTopping}"/></td>
                            <td><fmt:formatNumber value="${tp.giaTopping}" pattern="#,##0"/>đ</td>
                            <td>
                                <span class="badge ${tp.trangThai eq 'AVAILABLE' ? 'bg-success' : (tp.trangThai eq 'OUT_OF_STOCK' ? 'bg-warning text-dark' : 'bg-secondary')}">
                                    <c:out value="${tp.trangThai}"/>
                                </span>
                            </td>
                            <td class="text-end">
                                <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/manager/toppings?id=${tp.idTopping}">Chỉnh sửa</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/views/layout/footer.jsp"/>
