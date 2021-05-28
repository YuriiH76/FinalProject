<%@ include file="WEB-INF/blocks/taglib.jspf" %>
<%@ include file="WEB-INF/blocks/pageinit.jspf" %>
<html>
<%@ include file="WEB-INF/blocks/head.jspf" %>
<body class="d-flex flex-column h-100">

<%@ include file="WEB-INF/blocks/menu.jspf" %>

<div class="container mt-5">
    <div class="card mx-auto" style="width: 18rem;">
        <div class="card-header">
            <form action="controller" method="post">
                <c:set var="isInvalid" value=""/>
                <c:if test="${requestScope.errorMessage != null}">
                    <c:set var="isInvalid" value=" is-invalid"/>
                </c:if>
                <div class="form-group">
                    <input type="hidden" name="command" value="login"/>
                    <label for="login" class="form-label">
                        <fmt:message key="login_jsp.label.login"/>
                    </label>
                    <input type="text" name="login" class="form-control${isInvalid}"
                           id="login" value='<c:out value="${requestScope.login}"/>'
                           placeholder="user login"/>
                </div>
                <div class="form-group">
                    <label for="password" class="form-label mt-2"><fmt:message key="login_jsp.label.password"/></label>
                    <input type="password" name="password" class="form-control${isInvalid}"
                           id="password" placeholder="password"/>
                    <div id="passwordFeedback" class="invalid-feedback"><c:out value="${requestScope.errorMessage}"/></div>
                </div>
<%--                <a href="/registration">Sign up</a>--%>
                <button type="submit" class="btn btn-primary mt-4"><fmt:message key="login_jsp.button.login"/></button>
            </form>
        </div>
    </div>
</div>
<%@ include file="WEB-INF/blocks/bootstrap.jspf" %>
</body>
</html>