<%@ include file="WEB-INF/blocks/taglib.jspf" %>
<%@ include file="WEB-INF/blocks/pageinit.jspf" %>
<html>
<%@ include file="WEB-INF/blocks/head.jspf" %>
<body class="d-flex flex-column h-100">
<%@ include file="WEB-INF/blocks/menu.jspf" %>

<c:choose>
    <c:when test="${language == 'en'}">
        <%@ include file="WEB-INF/blocks/about_en.jspf" %>
    </c:when>
    <c:when test="${language == 'uk'}">
        <%@ include file="WEB-INF/blocks/about_uk.jspf" %>
    </c:when>
    <c:otherwise>
        <%@ include file="WEB-INF/blocks/about_en.jspf" %>
    </c:otherwise>
</c:choose>

<%@ include file="WEB-INF/blocks/bootstrap.jspf" %>
</body>
</html>
