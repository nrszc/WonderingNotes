$(document).ready(function(){
var layer;
layui.use('layer', function(){
    layer = layui.layer;
});

    var verifyCode = new GVerify("v_container");
    $("#loginbtn").click(function(){
        var res = verifyCode.validate(document.getElementById("verify").value);
        if(!res) {
            layer.msg("验证码错误");
            return;
        }
        var name = $("#name").val();
        if(name.length==0)
        {
            layer.msg("用户名不能为空");
            return;
        }
        var password = $("#password").val();
        if(password.length==0)
        {
            layer.msg("密码不能为空");
            return;
        }
        $.ajax({
            type:"post",
            data:{
                "name": name,
                "password": password
            },
            url:"/admin/adminlogin",
            async:true,
            success: function(res){
                console.log(res)
                if(res.status==0)
                    window.location.href="http://wsgzjh.cn/view/admin/index.html";
                else
                    layer.msg(res.msg)
            },
            error:function(res){
                layer.msg("服务器出错，请重试！");
            }
        });
    });
});