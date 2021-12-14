<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    </head>
    <body>
        <div class="container">
            <form action="/unit" method="post"  role="form" data-toggle="validator" >
                <c:if test ="${empty action}">                        	
                    <c:set var="action" value="edit"/>
                </c:if>
                <input type="hidden" id="action" name="action" value="${action}">
                <input type="hidden" id="idUnit" name="idUnit" value="${unit.id}">
                <h2>Unit</h2>
                <div class="form-group col-xs-4">
                    <label for="newName" class="control-label col-xs-4">Enter new name:</label>
                    <input type="text" name="newName" id="newName" class="form-control" value="${unit.name}" required="true"/>

                    <br></br>
                    <button type="submit" class="btn btn-primary btn-md">Accept</button>
                </div>                                                      
            </form>
        </div>
    </body>
</html>