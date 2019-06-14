<!DOCTYPE html>
<#assign ctx=request.contextPath />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录界面</title>
</head>
<script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60=" crossorigin="anonymous"></script>
<body>

<form >
    <input  type="text" name="userName"  id="userName"><br>
    <input name="password" type="password" id="password"><br>
    <input type="button" onclick="login()" value="登录">
</form>
<script  type="text/javascript">
    function login(){
        var userName=$("#userName").val();
        var password =$("#password").val();
        if(userName==""||password==''){
            alert("账号密码不能为空");
        }
        var data ="userName="+userName+"&password="+password;
        $.ajax({
            type: "POST",
            url: "${ctx}/login",
            data: data,
            dataType: 'json',
            async:false,
            success: function(result){
                if(result.retCode =="000000"){//登录成功
                    alert(result.retDesc);
                }else{
                    alert(result.retDesc);
                }
            }
        });
    }

</script>
</body>
</html>