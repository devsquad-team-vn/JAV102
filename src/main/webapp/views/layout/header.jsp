<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- Router Layout: Admin/Employee backoffice -> Admin Shell; Web Customer -> Portal Shell --%>
<c:set var="fwdPath" value="${requestScope['jakarta.servlet.forward.servlet_path']}"/>
<c:set var="reqUri" value="${not empty fwdPath ? fwdPath : pageContext.request.requestURI}"/>
<c:set var="isBackoffice" value="${reqUri.contains('/manager/') or reqUri.contains('/employee/shift') or reqUri.contains('/employee/my-bills') or reqUri.contains('/views/categories/') or reqUri.contains('/views/drink/') or reqUri.contains('/views/topping/') or reqUri.contains('/views/staff/') or reqUri.contains('/views/customer/') or reqUri.contains('/views/voucher/') or reqUri.contains('/views/bills/') or reqUri.contains('/views/statistics/') or reqUri.contains('/views/audit/') or reqUri.contains('/views/shift/') or reqUri.contains('/views/pos/my-bills')}"/>
<c:choose>
    <c:when test="${isBackoffice and not empty sessionScope.user}">
        <jsp:include page="/views/layout/header_admin.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="/views/layout/header_portal.jsp"/>
    </c:otherwise>
</c:choose>
