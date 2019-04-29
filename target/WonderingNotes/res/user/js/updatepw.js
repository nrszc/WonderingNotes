//layui需引入，一般直接写在一个js文件中
var layer;
layui.use(['layer', 'form'], function(){
  layer = layui.layer
  ,form = layui.form;
});

//验证码60秒倒计时
$("#codebtn").click(function() {
	  var identifier = $("input[name='identifier']").val();
      var re = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
	  //先校验手机号或邮箱是否被正确填写
	  if(!(/^1[34578]\d{9}$/.test(identifier))&&(!(re.test(identifier))))
	  {
		  layer.msg("请正确填写手机号或邮箱");
		  return;
	  }
	  var a = sendcode(identifier);
	  if(a>0) {
          return;
      }
    //这里写发送验证码的代码
    var time = 60;
    settime($(this));
    function settime(obj){
        if (time==0) {
            $(obj).attr('disabled', false);
            $(obj).html("获取验证码");
            time = 60;
            return;
        } else{
            $(obj).attr('disabled', true);
            $(obj).html("重新发送("+time+")");
            time--;
        }
        setTimeout(function() {
            settime(obj)
        },1000)
    }
});

//发送短信验证码
function sendcode(identifier){
    var a=0;
    $.ajax({
        url: "/userauths/send_code",
        data: {
            "identifier": identifier
        },
        type: 'post',
        async:false,
        success: function (res) {
            console.log(res);
            if(res.status==1) {
                a = res.status;
                $("#icon-right1").hide();
                layer.msg(res.msg);
            }
            else if(res.status==0)
            {
                layer.msg(res.msg);
            }
        }
    });
    return a;
}

//点击手机注册下一步按钮1
$("#nextbtn1").click(function() {
    var identifier = $("input[name='identifier']").val();
    var re = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
    //先校验手机号或邮箱是否被正确填写
    if(!(/^1[34578]\d{9}$/.test(identifier))&&(!(re.test(identifier))))
    {
        layer.msg("请正确填写手机号或邮箱");
        return;
    }
    var code = $("input[name='code']").val();
    if(code.length != 6)
    {
        layer.msg("验证码错误");
        return;
    }

    //将参数提交上去进行检验和注册
    $.ajax({
        url: "/userauths/updatepw_first",
        data: {
            "identifier": identifier,
            "code":code
        },
        type: 'post',
        success: function (res) {
            console.log(res);
            if(res.status==0)
            {
                $("#li1").removeClass("step-active");
                $("#li1").addClass("step-done");
                $("#li2").addClass("step-active");
                $(".check-box").hide();
                $(".password-box").show();
            }
            else if(res.status == 1){
                layer.msg(res.msg);
            }
        }
    });

});

//密码检验
$("input[name='password']").blur(function(){
    var password = $("input[name='password']").val();
    var re = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,21}$/;
    if(!(re.test(password)))
    {
        $("#tip-error1").show();
        $("#icon-right1").css("display","none");
    }
    else{
        $("#icon-right1").show();
        $("#tip-error1").css("display","none");
    }
});


//确认密码检验
$("input[name='repw']").blur(function(){
    var password = $("input[name='password']").val();
    var repw = $("input[name='repw']").val();
    if(password != repw)
    {
        $("#tip-error2").show();
        $("#icon-right2").css("display","none");
    }
    else{
        $("#icon-right2").show();
        $("#tip-error2").css("display","none");
    }
});

//下一步按钮2
$("#nextbtn2").click(function() {
    var password = $("input[name='password']").val();
    var re = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,21}$/;
    if(!(re.test(password)))
    {
        $("#tip-error1").show();
        $("#icon-right1").hide();
        return;
    }

    var repw = $("input[name='repw']").val();
    if(password != repw)
    {
        $("#tip-error2").show();
        $("#icon-right2").hide();
        return;
    }

        var identifier = $("input[name='identifier']").val();
        $.ajax({
            url: "/userauths/updatepwd_second",
            data: {
                "identifier": identifier,
                "password":repw
            },
            type: 'post',
            dataType : "json",
            success: function (res) {
                console.log(res);
                if(res.status==0)
                {
                    $("#li2").removeClass("step-active");
                    $("#li2").addClass("step-done");
                    $("#li3").addClass("step-active");
                    $(".password-box").hide();
                    $(".finish-box").show();
                }
                else if(res.status==1){
                    layer.msg(res.msg);
                }
            }
        });


});