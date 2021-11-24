<%-- 
    Document   : reset
    Created on : 24-Nov-2021, 10:36:45 AM
    Author     : willi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset password</title>
    </head>
    <body>
        <h1>Reset Password</h1>
        <form method="post" action="reset">
            <p>Please enter your email address to reset your password.</p>
            Email Address: <input type="email" name="email"><br>
            <input type="submit" name="resetPassword"><br>
        </form>
        <br>${msg}
    </body>
</html>
