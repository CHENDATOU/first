$(function () {
    //登录判断
    $("#btnLogin").click(function () {
        var name = $("#txtName");
        var pwd = $("#txtPwd");
    
    	if (name.val().replace(" ", "") == "") {
            alert("用户名不能为空!");
            name.focus();
            return false;
        }
        else if(pwd.val().replace(" ", "") == "") {
            alert("密码不能为空!");
            pwd.focus();
            return false;
        }
        
    });
});

