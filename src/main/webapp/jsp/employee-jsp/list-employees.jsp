<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>

<body>
<div class="container">
    <h2>Employees</h2>
    <!--Search Form -->
    <form action="/employee" method="get" id="searchEmployeeForm" role="form">
        <input type="hidden" id="searchAction" name="searchAction" value="searchByName">
        <div class="form-group col-xs-5">
            <input type="text" name="employeeName" id="employeeName" class="form-control" required="true"
                   placeholder="Type the Name of the employee"/>
            <input type="text" name="unitId" id="unitId" class="form-control" required="true"
                   placeholder="Type the unit id of the employee"/>
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
    <form action="/employee" method="post" id="employeeForm" role="form">
        <input type="hidden" id="idEmployee" name="idEmployee">
        <input type="hidden" id="action" name="action">
        <c:choose>
            <c:when test="${not empty employeeList}">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <td>#</td>
                        <td>Name</td>
                        <td>Is department head</td>
                        <td>Length of employment</td>
                        <td>Department</td>
                        <td></td>
                    </tr>
                    </thead>
                    <c:forEach var="employee" items="${employeeList}">
                        <c:set var="classSucess" value=""/>
                        <c:if test="${idEmployee == employee.id}">
                            <c:set var="classSucess" value="info"/>
                        </c:if>
                        <tr class="${classSucess}">
                            <td>
                                <a href="/employee?idEmployee=${employee.id}&searchAction=searchById&operation=edit">${employee.id}</a>
                            </td>
                            <td>${employee.name}</td>
                            <td>${employee.departmentHead}</td>
                            <td>${employee.lengthOfEmployment}</td>
                            <td>${employee.unitId}</td>
                            <td>
                                <button class="btn btn-danger btn-md" href="#" id="remove"
                                        onclick="document.getElementById('action').value = 'remove';document.getElementById('idEmployee').value = '${employee.id}';

                                                document.getElementById('employeeForm').submit();">
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
                    No people found matching your search criteria
                </div>
            </c:otherwise>
        </c:choose>
    </form>
    <form action="jsp/employee-jsp/new-employee.jsp">
        <button type="submit" class="btn btn-primary btn-md">New employee</button>
    </form>
    <form action="/unit">
        <button type="submit" class="btn btn-check btn-md">To units list</button>
    </form>
</div>
</body>
</html>