//layui需引入，一般直接写在一个js文件中
var layer;
var type;
layui.use(['layer', 'form'], function(){
  layer = layui.layer
  ,form = layui.form;
});

//注册方式切换
$(".phone-a").click(function(){
  $(".email-form").hide();
  $(".phone-form").show();
  $(this).css("background-color","#5FB878");
  $(".email-a").css("background-color","#F2F2F2");
  $(".email-a").css("color","#989898");
  $(".phone-a").css("color","#FFFFFF");
});

$(".email-a").click(function(){
  $(".phone-form").hide();
  $(".email-form").show();
  $(this).css("background-color","#5FB878");
  $(".phone-a").css("background-color","#F2F2F2");
  $(".phone-a").css("color","#989898");
  $(".email-a").css("color","#FFFFFF");
});

//验证码60秒倒计时
$("#codebtn1").click(function() {
	  var phone = $("input[name='phone']").val();
	  //先校验手机号是否被填写
	  if(!(/^1[34578]\d{9}$/.test(phone)))
	  {
		  $("#tip-error1").show();
		  $("#icon-right1").css("display","none");
		  return;
	  }
	  var a = sendphonecode(phone);
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

//验证码60秒倒计时
$("#codebtn2").click(function() {
	  //先检验邮箱地址是否已被正确输入
	  var email = $("input[name='email']").val();
	  var re = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
	  if(!(re.test(email)))
	  {
		  $("#tip-error3").show();
		  $("#icon-right3").css("display","none");
		  return;
	  }

    var a = sendemailcode(email);
	  if(a>0)
	      return;
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


//手机注册表单校验
$("input[name='phone']").blur(function(){
	var phone = $("input[name='phone']").val();
	if(!(/^1[34578]\d{9}$/.test(phone)))
	{
		$("#tip-error1").show();
		$("#icon-right1").css("display","none");
	}
	else{
		$("#icon-right1").show();
		$("#tip-error1").css("display","none");
	}
});

//$("input[name='code1']").blur(function(){
//	var code1 = $("input[name='code1']").val();
//	if(code1.length == 0)
//	{
//		$("#tip-error2").show();
//	}
//	else{
//		$("#tip-error2").css("display","none");
//	}
//});


//邮箱注册表单校验
$("input[name='email']").blur(function(){
	var email = $("input[name='email']").val();
	var re = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
	if(!(re.test(email)))
	{
		$("#tip-error3").show();
		$("#icon-right3").css("display","none");
	}
	else{
		$("#icon-right3").show();
		$("#tip-error3").css("display","none");
	}
});

//$("input[name='code2']").blur(function(){
//	var code2 = $("input[name='code2']").val();
//	if(code2.length == 0)
//	{
//		$("#tip-error4").show();
//	}
//	else{
//		$("#tip-error4").css("display","none");
//	}
//});

//昵称检验
$("input[name='nickname']").blur(function(){
	var nickname = $("input[name='nickname']").val();
	if(nickname.length == 0)
	{
		$("#tip-error5").show();
	}
	else{
		$("#tip-error5").hide();
	}
});

//密码检验
$("input[name='password']").blur(function(){
	var password = $("input[name='password']").val();
	var re = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,21}$/;
	if(!(re.test(password)))
	{
		$("#tip-error6").show();
		$("#icon-right6").css("display","none");
	}
	else{
		$("#icon-right6").show();
		$("#tip-error6").css("display","none");
	}
});


//确认密码检验
$("input[name='cpwd']").blur(function(){
	var password = $("input[name='password']").val();
	var cpwd = $("input[name='cpwd']").val();
	if(cpwd.length == 0)
	{
		$("#tip-error7").show();
		$("#icon-right7").css("display","none");
	}
	else if(password != cpwd)
	{
		$("#cpwd-span").text("两次输入密码不一致");
		$("#tip-error7").show();
		$("#icon-right7").css("display","none");
	}
	else{
		$("#icon-right7").show();
		$("#tip-error7").css("display","none");
	}
});

//点击手机注册下一步按钮1
$("#nextbtn1").click(function() {
	var phone = $("input[name='phone']").val();
	if(!(/^1[34578]\d{9}$/.test(phone)))
	{
		$("#tip-error1").show();
		$("#icon-right1").hide();
		return;
	}
	var code1 = $("input[name='code1']").val();
	if(code1.length != 6)
	{
		$("#tip-error2").show();
		return;
	}

	//将参数提交上去进行检验和注册
    $.ajax({
        url: "/userauths/regist_phonefirst",
        data: {
            "phone": phone,
            "code":code1
        },
        type: 'get',
        dataType : "json",
        contentType: "application/json; charset=utf-8",
        success: function (res) {
            console.log(res);
            if(res.status==0)
            {
                $("#li1").removeClass("step-active");
                $("#li1").addClass("step-done");
                $("#li2").addClass("step-active");
                $(".regist-box").hide();
                $(".news-box").show();
                type = 0;  //手机注册
            }
            else if(res.status==1){
                $("#tip-error2").show();
            }
            else if(res.status == 2){
                layer.msg(res.msg);
            }
        }
    });

});

//下一步按钮2
$("#nextbtn2").click(function() {
	var email = $("input[name='email']").val();
	var re = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
	if(!(re.test(email)))
	{
		$("#tip-error3").show();
		$("#icon-right3").css("display","none");
		return;
	}
	var code2 = $("input[name='code2']").val();
	if(code2.length != 6)
	{
		$("#tip-error4").show();
		return;
	}
    //将参数提交上去进行检验和注册
    $.ajax({
        url: "/userauths/regist_emailfirst",
        data: {
            "email": email,
            "code":code2
        },
        type: 'get',
        dataType : "json",
        contentType: "application/json; charset=utf-8",
        success: function (res) {
            console.log(res);
            if(res.status==0)
            {
                $("#li1").removeClass("step-active");
                $("#li1").addClass("step-done");
                $("#li2").addClass("step-active");
                $(".regist-box").hide();
                $(".news-box").show();
                type = 1;  //邮箱注册
            }
            else if(res.status==1){
                $("#tip-error4").show();
            }
            else if(res.status == 2){
                layer.msg(res.msg);
            }
        }
    });
});

//下一步按钮3
$("#nextbtn3").click(function() {
	var nickname = $("input[name='nickname']").val();
	if(nickname.length == 0)
	{
		$("#tip-error5").show();
		return;
	}
	
	var password = $("input[name='password']").val();
	var re = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,21}$/;
	if(!(re.test(password)))
	{
		$("#tip-error6").show();
		$("#icon-right6").css("display","none");
		return;
	}
	
	var cpwd = $("input[name='cpwd']").val();
	if(cpwd.length == 0)
	{
		$("#tip-error7").show();
		$("#icon-right7").css("display","none");
		return;
	}
	else if(password != cpwd)
	{
		$("#cpwd-span").text("两次输入密码不一致");
		$("#tip-error7").show();
		$("#icon-right7").css("display","none");
		return;
	}

	//将参数提交上去进行检验和注册
    if(type==0)
	{
        var phone = $("input[name='phone']").val();
        $.ajax({
            url: "/userauths/regist_phonesecond",
            data: {
                "phone": phone,
				"nickname":nickname,
				"password":cpwd
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
                    $(".news-box").hide();
                    $(".finish-box").show();
                }
                else{
                    layer.msg(res.msg);
                }
            }
        });
	}
    if(type==1)
    {
        var email = $("input[name='email']").val();
        $.ajax({
            url: "/userauths/regist_emailsecond",
            data: {
                "email": email,
                "nickname":nickname,
                "password":cpwd
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
                    $(".news-box").hide();
                    $(".finish-box").show();
                }
                else{
                    layer.msg(res.msg);
                }
            }
        });
    }

});

//发送短信验证码
function sendphonecode(phone){
    var a=0;
    $.ajax({
        url: "/userauths/send_phonecode",
        data: {
            "phone": phone
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

//发送邮箱验证码
function sendemailcode(email){
    var a=0;
    $.ajax({
        url: "/userauths/send_emailcode",
        data: {
            "email": email
        },
        type: 'post',
        async:false,
        success: function (res) {
            console.log(res);
            if(res.status==1) {
                a = res.status;
                $("#icon-right3").hide();
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

