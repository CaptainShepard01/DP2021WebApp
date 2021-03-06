<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    </head>
    <body>
        <div class="container">
            <form action="/employee" method="post"  role="form" data-toggle="validator" >
                <c:if test ="${empty action}">                        	
                    <c:set var="action" value="add"/>
                </c:if>
                <input type="hidden" id="action" name="action" value="${action}">
                <input type="hidden" id="idEmployee" name="idEmployee" value="${employee.id}">
                <h2>Employee</h2>
                <div class="form-group col-xs-4">
                    <label for="name" class="control-label col-xs-4">Name:</label>
                    <input type="text" name="name" id="name" class="form-control" value="${employee.name}" required="true"/>

                    <label for="isDepartmentHead" class="control-label col-xs-4">IsDepartmentHead:</label>
                    <input type="text" name="isDepartmentHead" id="isDepartmentHead" class="form-control" value="${employee.departmentHead}" required="true"/>

                    <label for="lengthOfEmployment" class="control-label col-xs-4">LengthOfEmployment:</label>
                    <input type="text" name="lengthOfEmployment" id="lengthOfEmployment" class="form-control" value="${employee.lengthOfEmployment}" required="true"/>

                    <label for="unitId" class="control-label col-xs-4">UnitId:</label>
                    <input type="text" name="unitId" id="unitId" class="form-control" value="${employee.unitId}" required="true"/>

                    <br></br>
                    <button type="submit" class="btn btn-primary  btn-md">Accept</button> 
                </div>                                                      
            </form>
        </div>
    </body>
</html>