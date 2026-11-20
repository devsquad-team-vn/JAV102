<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%-- @author Kiệt (Nhóm PolyCoffee - JAV102) --%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="/views/layout/header_portal.jsp"/>

<c:if test="${not empty authError}">
    <div class="alert alert-danger d-flex align-items-center gap-2 shadow-sm">
        <i class="bi bi-shield-lock-fill fs-5"></i>
        <span><c:out value="${authError}"/></span>
    </div>
</c:if>

<!-- HERO ARTISAN BANNER -->
<section class="portal-hero mb-4">
    <div class="row align-items-center g-4 position-relative" style="z-index:2;">
        <div class="col-lg-8">
            <span class="badge bg-warning text-dark fw-bold px-3 py-2 rounded-pill mb-3">
                <i class="bi bi-lightning-charge-fill me-1"></i> PolyCoffee Artisan • Mở cửa 07:00 – 22:00
            </span>
            <h1 class="display-6 fw-bold mb-3">Hương Vị Cà Phê Rang Mộc — Vận Hành Chuyên Nghiệp</h1>
            <p class="lead opacity-90 mb-4" style="font-size:1.05rem;">
                Khám phá thực đơn đa kích cỡ (Size S/M/L), tùy chọn topping phong phú cùng hệ thống quản lý danh mục và phân quyền bảo mật.
            </p>
            <div class="d-flex flex-wrap gap-2">
                <a href="#thuc-don" class="btn btn-caramel btn-lg rounded-pill px-4">
                    <i class="bi bi-cup-hot-fill me-1"></i> Xem Thực đơn
                </a>
                <c:choose>
                    <c:when test="${isLoggedIn and isManager}">
                        <a href="${ctx}/manager/categories" class="btn btn-outline-light btn-lg rounded-pill px-4">
                            <i class="bi bi-tags me-1"></i> Quản lý Danh mục
                        </a>
                    </c:when>
                    <c:when test="${not isLoggedIn}">
                        <a href="${ctx}/dang-nhap" class="btn btn-outline-light btn-lg rounded-pill px-4">
                            <i class="bi bi-shield-lock me-1"></i> Đăng nhập Nhân viên
                        </a>
                    </c:when>
                </c:choose>
            </div>
        </div>
        <div class="col-lg-4 d-none d-lg-block">
            <div class="bg-white bg-opacity-10 border border-white border-opacity-25 rounded-4 p-4">
                <div class="small text-warning fw-bold text-uppercase mb-2">Cam kết PolyCoffee</div>
                <div class="d-flex align-items-center gap-2 mb-2"><i class="bi bi-cup-hot text-warning"></i> <span>${drinks.size()} món pha chế thủ công tươi mới</span></div>
                <div class="d-flex align-items-center gap-2 mb-2"><i class="bi bi-stars text-warning"></i> <span>${toppingsCount} loại Topping chọn thêm</span></div>
                <div class="d-flex align-items-center gap-2"><i class="bi bi-shield-check text-warning"></i> <span>Phân quyền ADMIN &amp; Nhân viên an toàn</span></div>
            </div>
        </div>
    </div>
</section>

<!-- DANH MUC THUC DON + BEST SELLERS -->
<section class="mb-4" id="thuc-don">
    <div class="d-flex flex-wrap justify-content-between align-items-center gap-2 mb-3">
        <div>
            <h4 class="fw-bold text-espresso mb-0">Thực Đơn Hôm Nay</h4>
            <div class="small text-muted">Lọc theo danh mục và tham khảo bảng giá kích cỡ S / M / L tại quầy</div>
        </div>
        <div class="d-flex flex-wrap gap-2">
            <a href="${ctx}/trang-chu"
               class="btn btn-sm rounded-pill ${selectedCategoryId == 0 ? 'btn-coffee' : 'btn-outline-coffee'}">Tất cả</a>
            <c:forEach items="${categories}" var="cat">
                <a href="${ctx}/trang-chu?categoryId=${cat.idDanhMuc}"
                   class="btn btn-sm rounded-pill ${selectedCategoryId == cat.idDanhMuc ? 'btn-coffee' : 'btn-outline-coffee'}">
                    <c:out value="${cat.tenDanhMuc}"/>
                </a>
            </c:forEach>
        </div>
    </div>

    <div class="row g-3">
        <c:forEach items="${drinks}" var="sp">
            <c:set var="sizes" value="${sizeMap[sp.idSanPham]}"/>
            <div class="col-6 col-md-4 col-lg-3">
                <div class="portal-drink-card">
                    <div class="portal-drink-thumb">
                        <c:choose>
                            <c:when test="${not empty sp.hinhAnh and sp.hinhAnh.startsWith('http')}">
                                <img src="${sp.hinhAnh}" alt="<c:out value='${sp.tenSanPham}'/>"
                                     onerror="this.src='${ctx}/assets/img/drink-default.jpg'">
                            </c:when>
                            <c:when test="${not empty sp.hinhAnh and sp.hinhAnh.startsWith('/')}">
                                <img src="${ctx}${sp.hinhAnh}" alt="<c:out value='${sp.tenSanPham}'/>"
                                     onerror="this.src='${ctx}/assets/img/drink-default.jpg'">
                            </c:when>
                            <c:otherwise>
                                <img src="${ctx}/assets/img/drink-default.jpg" alt="<c:out value='${sp.tenSanPham}'/>">
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${sp.laMonBanChay}">
                            <span class="badge-bestseller"><i class="bi bi-fire"></i> Best Seller</span>
                        </c:if>
                        <c:if test="${not sp.laMonBanChay and sp.laMonMoi}">
                            <span class="badge-bestseller bg-success">Món mới</span>
                        </c:if>
                    </div>
                    <div class="p-3 d-flex flex-column justify-content-between flex-grow-1">
                        <div>
                            <h6 class="fw-bold text-espresso mb-1"><c:out value="${sp.tenSanPham}"/></h6>
                            <p class="small text-muted mb-2" style="display:-webkit-box;-webkit-line-clamp:2;-webkit-box-orient:vertical;overflow:hidden;">
                                <c:out value="${sp.moTa}"/>
                            </p>
                        </div>
                        <div>
                            <div class="d-flex justify-content-between align-items-center mb-2">
                                <span class="fw-bold text-caramel fs-6">
                                    <c:choose>
                                        <c:when test="${not empty sizes}">
                                            Từ <fmt:formatNumber value="${sizes[0].giaBan}" type="number"/>đ
                                        </c:when>
                                        <c:otherwise>Liên hệ quầy</c:otherwise>
                                    </c:choose>
                                </span>
                                <span class="small text-muted">${not empty sizes ? sizes.size() : 0} Size</span>
                            </div>
                            <c:if test="${not empty sizes}">
                                <div class="d-flex flex-wrap gap-1 pt-2 border-top">
                                    <c:forEach items="${sizes}" var="sz">
                                        <span class="badge bg-cream text-espresso border fw-normal">
                                            <c:out value="${sz.tenKichCo}"/>: <fmt:formatNumber value="${sz.giaBan}" type="number"/>đ
                                        </span>
                                    </c:forEach>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        <c:if test="${empty drinks}">
            <div class="col-12">
                <div class="card-poly p-5 text-center text-muted">Hiện chưa có món khả dụng trong danh mục này.</div>
            </div>
        </c:if>
    </div>
</section>

<jsp:include page="/views/layout/footer_portal.jsp"/>
