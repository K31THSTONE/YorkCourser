<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
  <head>
    <title>Add Numbers</title>
    <style type="text/css">
    .error {
      color: red;
    }

    td.label {
      text-align: right;
    }
    </style>
  </head>

  <body>
    <c:if test="${! empty errorMessage}">
      <div class="error">${errorMessage}</div>
    </c:if>

    <form action="${pageContext.servletContext.contextPath}/addNumbers" method="post">
      <table>
        <tr>
          <td class="label">Schedule Name?:</td>
          <td><input type="text" name="scheduleName" size="12" value="${scheduleName}" /></td>
        </tr>
        <tr>
          <td class="label">Schedule:</td>
          <td>${schedule}</td>
        </tr>
      </table>
      <input type="Submit" name="submit" value="Add Numbers!">
    </form>
  </body>
</html>