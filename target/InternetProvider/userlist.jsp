<%@ include file="WEB-INF/blocks/taglib.jspf" %>
<%@ include file="WEB-INF/blocks/pageinit.jspf" %>
<html>
<%@ include file="WEB-INF/blocks/head.jspf" %>
<body class="d-flex flex-column h-100">
<%@ include file="WEB-INF/blocks/menu.jspf" %>
<div class="container mt-5">

    <p>
        <a class="btn btn-primary" data-bs-toggle="collapse" href="#collapseAdd" role="button" aria-expanded="false" aria-controls="collapseAdd">
            Add user
        </a>
        <a class="btn btn-primary" href="/ip/controller?command=charge">
            Charge for services
        </a>
    </p>
    <div class="collapse mb-3" id="collapseAdd">
        <form action="controller" method="post">
            <input type="hidden" name="command" value="addUser"/>
            <div class="mb-3">
                <label for="login" class="form-label">Login</label>
                <input type="text" class="form-control" name="login" id="login">
            </div>
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" class="form-control" name="name" id="name">
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" name="email" id="email" aria-describedby="emailHelp">
                <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
            </div>
            <div class="mb-3">
                <label for="Password" class="form-label">Password</label>
                <input type="password" class="form-control" name="password" id="Password">
            </div>
            <div class="mb-3 form-check form-check-inline">
                <span>Language </span>
                <select name="language" class="form-check-select form-select-sm" aria-label=".form-select-sm example">
                    <option <c:if test="${element.language == 'EN'}">selected</c:if> value="EN">EN</option>
                    <option <c:if test="${element.language == 'UK'}">selected</c:if> value="UK">UK</option>
                </select>
            </div>
            <div class="mb-3 form-check form-check-inline">
                <input type="checkbox" class="form-check-input" name="active" id="Active">
                <label class="form-check-label" for="Active">active</label>
            </div>
            <div class="mb-3 form-check form-check-inline">
                <input type="checkbox" class="form-check-input" name="admin" id="Admin">
                <label class="form-check-label" for="Admin">admin</label>
            </div>
            <div class="mb-3 form-check form-check-inline">
                <label for="account" class="form-check-label">Account</label>
                <input type="number" name="account" id="account"  class="form-check-control" style="text-align: right"
                       max="5000" min="0" step="0.01" value="0.00"/>
            </div>
            <br/>
            <button type="submit" class="btn btn-primary">Add</button>
        </form>
    </div>

    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Login</th>
            <th scope="col">Name</th>
            <th scope="col">Email</th>
            <th scope="col">Language</th>
            <th scope="col">Admin</th>
            <th scope="col">Active</th>
            <th scope="col">Action</th>
            <th scope="col">Account</th>
            <th scope="col">Money</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.userList}" var="element" varStatus="loop">
            <tr>
                <form action="controller" method="post">
<%--                    <c:set var="type" value="text"/>--%>
                   <th scope="row">${loop.index+1}<input type="hidden" name="command" value="updateUser"/></th>
                    <td><input type="text" name="login" class="form-control" value="${element.login}" readonly/></td>
                    <td><input type="text" name="name" class="form-control" value="${element.name}"/></td>
                    <td><input type="text" name="email" class="form-control" value="${element.email}"/></td>
                    <td>
                        <select name="language" class="form-select form-select-sm" aria-label=".form-select-sm example">
                            <option <c:if test="${element.language == 'EN'}">selected</c:if> value="EN">EN</option>
                            <option <c:if test="${element.language == 'UK'}">selected</c:if> value="UK">UK</option>
                        </select>
                    </td>
                    <td><input type="checkbox" name="admin" <c:if test="${element.admin}">checked</c:if>/></td>
                    <td><input type="checkbox" name="active" <c:if test="${element.active}">checked</c:if>/></td>
                    <td><button type="submit" class="btn btn-primary">edit</button></td>
                </form>
                <form action="controller" method="post">
                    <td><input type="hidden" name="command" value="updateUserAccount"/>
                        <input type="hidden" name="login" class="form-control" value="${element.login}"/>
                        <input type="number" name="account" class="form-control" style="text-align: right" value="${element.account}" readonly/></td>
                    <td><input type="number" name="put" class="form-control" style="text-align: right"
                               max="5000" min="0" step="0.01" value="0.00"/></td>
                    <td><button type="submit" class="btn btn-primary">put</button></td>
                </form>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
<%@ include file="WEB-INF/blocks/bootstrap.jspf" %>
</body>
</html>