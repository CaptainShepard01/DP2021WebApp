<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>

<body>
<div class="container">
    <h2>Units</h2>
    <!--Search Form -->
    <form action="/unit" method="get" id="searchUnitForm" role="form">
        <input type="hidden" id="searchAction" name="searchAction" value="searchByName">
        <div class="form-group col-xs-5">
            <input type="text" name="unitName" id="unitName" class="form-control" required="true"
                   placeholder="Type the Name of department unit"/>
        </div>
        <button type="submit" class="btn btn-info">
            <span class="glyphicon glyphicon-search"></span> Search
        </button>
        <br></br>
        <br></br>
    </form>

    <!--Employees List-->
    <c:if test="${not empty message}">
        <div class="alert alert-success">
                ${message}
        </div>
    </c:if>
    <form action="/unit" method="post" id="unitForm" role="form">
        <input type="hidden" id="idUnit" name="idUnit">
        <input type="hidden" id="action" name="action">
        <c:choose>
            <c:when test="${not empty unitList}">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <td>#</td>
                        <td>Name</td>
                        <td></td>
                    </tr>
                    </thead>
                    <c:forEach var="unit" items="${unitList}">
                        <c:set var="classSucess" value=""/>
                        <c:if test="${idUnit == unit.id}">
                            <c:set var="classSucess" value="info"/>
                        </c:if>
                        <tr class="${classSucess}">
                            <td>
                                <a href="/unit?idUnit=${unit.id}&searchAction=searchById&operation=edit">${unit.id}</a>
                            </td>
                            <td>${unit.name}</td>
                            <td>
                                <button class="btn btn-danger btn-md" href="#" id="remove"
                                        onclick="document.getElementById('action').value = 'remove';document.getElementById('idUnit').value = '${unit.id}';

                                                document.getElementById('unitForm').submit();">
                                    Remove
                                    <span class="glyphicon glyphicon-trash"/>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <br>
                <div class="alert alert-info">
                    No units found matching your search criteria
                </div>
            </c:otherwise>
        </c:choose>
    </form>
    <form action="jsp/unit-jsp/new-unit.jsp">
        <button type="submit" class="btn btn-primary btn-md">New department unit</button>
    </form>
    <form action="/employee">
        <button type="submit" class="btn btn-check btn-md">To employees list</button>
    </form>
</div>
</body>
</html>