<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="fwdPath" value="${requestScope['jakarta.servlet.forward.servlet_path']}"/>
<c:set var="reqUri" value="${not empty fwdPath ? fwdPath : pageContext.request.requestURI}"/>
<c:set var="isBackoffice" value="${reqUri.contains('/manager/') or reqUri.contains('/employee/') or reqUri.contains('/views/categories/') or reqUri.contains('/views/topping/') or reqUri.contains('/views/employee/')}"/>
<c:choose>
    <c:when test="${isBackoffice and not empty sessionScope.user}">
        <jsp:include page="/views/layout/footer_admin.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="/views/layout/footer_portal.jsp"/>
    </c:otherwise>
</c:choose>
