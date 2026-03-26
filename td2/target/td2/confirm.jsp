<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirmation</title>
</head>
<body>
    <h2>Utilisateur existe déjà</h2>
    <p>Voulez-vous l'ajouter quand même ?</p>

    <p>First name: <%= request.getAttribute("firstname") %></p>
    <p>Last name: <%= request.getAttribute("lastname") %></p>
    <p>Email: <%= request.getAttribute("email") %></p>
    <p>Gender: <%= request.getAttribute("gender") %></p>

    <form action="InsertUser" method="post">
        <input type="hidden" name="firstname" value="<%= request.getAttribute("firstname") %>">
        <input type="hidden" name="lastname" value="<%= request.getAttribute("lastname") %>">
        <input type="hidden" name="email" value="<%= request.getAttribute("email") %>">
        <input type="hidden" name="password" value="<%= request.getAttribute("password") %>">
        <input type="hidden" name="gender" value="<%= request.getAttribute("gender") %>">
        <input type="submit" value="Oui">
    </form>

    <form action="form.html" method="get">
        <input type="submit" value="Non">
    </form>
</body>
</html>