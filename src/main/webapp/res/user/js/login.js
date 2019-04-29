//layui需引入，一般直接写在一个js文件中
var layer;
layui.use(['layer', 'form'], function(){
    layer = layui.layer
        ,form = layui.form;
});


new Vue({
    el:"#wrapper",
    data:{
        "user": []
    },
    methods: {

    },
    created: function(){
        getBgimg();
    }
});

//登录方式切换
$(".account-login").click(function(){
  $("#nopw-form").hide();
  $("#account-form").show();
  $(".nopw-login").css("border-bottom","0px solid #009688");
  $(".nopw-login").css("font-weight","500");
  $(this).css("font-weight","600");
  $(this).css("border-bottom","3px solid #009688");
});

$(".nopw-login").click(function(){
  $("#account-form").hide();
  $("#nopw-form").show();
  $(this).css("border-bottom","3px solid #009688");
  $(this).css("font-weight","600");
  $(".account-login").css("font-weight","500");
  $(".account-login").css("border-bottom","0px solid #009688");
});

//验证码
// var handler = function (captchaObj) {
//     $("#submit1").click(function (e) {
//         var result = captchaObj.getValidate();
//         if (!result) {
//             $("#notice1").show();
//             setTimeout(function () {
//                 $("#notice1").hide();
//             }, 2000);
//             e.preventDefault();
//         }
//     });
//     // 将验证码加到id为captcha的元素里，同时会有三个input的值用于表单提交
//     captchaObj.appendTo("#captcha1");
//     captchaObj.onReady(function () {
//         $("#wait1").hide();
//     });
//     // 更多接口参考：http://www.geetest.com/install/sections/idx-client-sdk.html
// };
//
//
// $(document).ready(function(){
//     console.log("dkkkkkkkkkk");
//     $.ajax({
//         url: "../../startcaptcha/start?t=" + (new Date()).getTime(), // 加随机数防止缓存
//         type: "get",
//         dataType: "json",
//         success: function (data) {
//             alert(data);
//             // 调用 initGeetest 初始化参数
//             // 参数1：配置参数
//             // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它调用相应的接口
//             initGeetest({
//                 gt: data.gt,
//                 challenge: data.challenge,
//                 new_captcha: data.new_captcha, // 用于宕机时表示是新验证码的宕机
//                 offline: !data.success, // 表示用户后台检测极验服务器是否宕机，一般不需要关注
//                 product: "float", // 产品形式，包括：float，popup
//                 width: "100%"
//                 // 更多配置参数请参见：http://www.geetest.com/install/sections/idx-client-sdk.html#config
//             }, handler);
//         }
//     });
// });
//
$(document).ready(function(){

    //getBgimg();

  //表单一验证码
    var handler1 = function (captchaObj) {
        captchaObj.onReady(function () {
            $("#wait").hide();
        }).onSuccess(function () {
            var result = captchaObj.getValidate();
            if (!result) {
                return layer.msg('请完成验证');
            }
            $.ajax({
                url: '../../verifylogin/verify',
                type: 'POST',
                data: {
                    identifier: $('#identifier1').val(),
                    credential: $('#password').val(),
                    logintype: 0,
                    geetest_challenge: result.geetest_challenge,
                    geetest_validate: result.geetest_validate,
                    geetest_seccode: result.geetest_seccode
                },
                success: function (data) {
                    console.log(data);
                    if (data.status === 0) {
                        setTimeout(function () {
                            window.location.href="http://wsgzjh.cn/view/user/index.html";
                        }, 1500);
                    } else if (data.status === 1) {
                        setTimeout(function () {
                            layer.msg(data.msg);
                            captchaObj.reset();
                        }, 1500);
                    }
                }
            });
        });
        $('#btn1').click(function () {
            // 调用之前先通过前端表单校验
            var input1 = $("input[name='identifier1']");
            var input2 = $("input[name='password']");
            if(input1.val().length == 0){
		        var top = input1.offset().top;
                var left = input1.offset().left;
                $("#tip").css("position","absolute");
                $("#tip").css("top",top-40);
                $("#tip").css("left",left+80);
                $("#span-content").text("请输入正确的手机号或邮箱");
                $("#tip").show();
                $(document).one("click", function() { //对document绑定一个影藏Div方法
                  $("#tip").hide();
                });
                event.stopPropagation();//阻止事件向上冒泡
                return;
             }
             else if(input2.val().length == 0){
                var top = input2.offset().top;
                var left = input2.offset().left;
                $("#tip").css("position","absolute");
                $("#tip").css("top",top-40);
                $("#tip").css("left",left+80);
                $("#span-content").text("请输入正确密码");
                $("#tip").show();
                $(document).one("click", function() { //对document绑定一个影藏Div方法
                    $("#tip").hide();
                });
                event.stopPropagation();//阻止事件向上冒泡
                return;
            }
            captchaObj.verify();
        });
        // 更多接口说明请参见：http://docs.geetest.com/install/client/web-front/
    };

    $.ajax({
        url: "../../startcaptcha/start?t=" + (new Date()).getTime(), // 加随机数防止缓存
        type: "get",
        dataType: "json",
        success: function (data) {
            // 调用 initGeetest 初始化参数
            // 参数1：配置参数
            // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它调用相应的接口
            initGeetest({
                gt: data.gt,
                challenge: data.challenge,
                new_captcha: data.new_captcha, // 用于宕机时表示是新验证码的宕机
                offline: !data.success, // 表示用户后台检测极验服务器是否宕机，一般不需要关注
                product: "bind", // 产品形式，包括：float，popup
                timeout: '5000',
                width: "100%",
                https: true
                // 更多配置参数请参见：http://www.geetest.com/install/sections/idx-client-sdk.html#config
            }, handler1);
        }
    });


    //表单而验证码
    var handler2 = function (captchaObj1) {
        captchaObj1.onReady(function () {
            $("#wait").hide();
        }).onSuccess(function () {
            var result = captchaObj1.getValidate();
            if (!result) {
                return layer.msg('请完成验证');
            }
            $.ajax({
                url: '/verifylogin/verify',
                type: 'POST',
                dataType: 'json',
                data: {
                    identifier: $('#identifier2').val(),
                    credential: $('#code').val(),
                    logintype:1,
                    geetest_challenge: result.geetest_challenge,
                    geetest_validate: result.geetest_validate,
                    geetest_seccode: result.geetest_seccode
                },
                success: function (data) {
                    if (data.status === 0) {
                        setTimeout(function () {
                            window.location.href="http://wsgzjh.cn/view/user/index.html";
                        }, 1500);
                    } else if (data.status === 1) {
                        setTimeout(function () {
                            layer.msg(data.msg);
                            captchaObj1.reset();
                        }, 1500);
                    }
                }
            });
        });
        $('#btn2').click(function () {
            // 调用之前先通过前端表单校验
            var input3 = $("input[name='identifier2']");
            var input4 = $("input[name='code']");
            if(input3.val().length == 0){
                var top = input3.offset().top;
                var left = input3.offset().left;
                $("#tip").css("position","absolute");
                $("#tip").css("top",top-40);
                $("#tip").css("left",left+80);
                $("#span-content").text("请输入正确的手机号或邮箱");
                $("#tip").show();
                $(document).one("click", function() { //对document绑定一个影藏Div方法
                    $("#tip").hide();
                });
                event.stopPropagation();//阻止事件向上冒泡
                return;
            }
            else if(input4.val().length == 0){
                var top = input4.offset().top;
                var left = input4.offset().left;
                $("#tip").css("position","absolute");
                $("#tip").css("top",top-40);
                $("#tip").css("left",left+7);
                $("#span-content").text("请输入正确验证码");
                $("#tip").show();
                $(document).one("click", function() { //对document绑定一个影藏Div方法
                    $("#tip").hide();
                });
                event.stopPropagation();//阻止事件向上冒泡
                return;
            }
            captchaObj1.verify();
        });
        // 更多接口说明请参见：http://docs.geetest.com/install/client/web-front/
    };

    $.ajax({
        url: "../../startcaptcha/start?t=" + (new Date()).getTime(), // 加随机数防止缓存
        type: "get",
        dataType: "json",
        success: function (data) {
            // 调用 initGeetest 初始化参数
            // 参数1：配置参数
            // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它调用相应的接口
            initGeetest({
                gt: data.gt,
                challenge: data.challenge,
                new_captcha: data.new_captcha, // 用于宕机时表示是新验证码的宕机
                offline: !data.success, // 表示用户后台检测极验服务器是否宕机，一般不需要关注
                product: "bind", // 产品形式，包括：float，popup
                timeout: '5000',
                width: "100%",
                https: true
                // 更多配置参数请参见：http://www.geetest.com/install/sections/idx-client-sdk.html#config
            }, handler2);
        }
    });


});

$("#tip").click(function(event) {
    event.stopPropagation(); //阻止事件向上冒泡
});

//验证码60秒倒计时
$("#codebtn").click(function() {
	  //先检验手机号是否已输入
	  var identifier2 = $("input[name='identifier2']").val();
	  var re = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
	  //alert(identifier2);
	  if((!(re.test(identifier2)))&&(!(/^1[34578]\d{9}$/.test(identifier2))))
	  {
          var input3 = $("input[name='identifier2']");
              var top = input3.offset().top;
              var left = input3.offset().left;
              $("#tip").css("position","absolute");
              $("#tip").css("top",top-40);
              $("#tip").css("left",left+80);
              $("#span-content").text("请输入正确的手机号或邮箱");
              $("#tip").show();
              $(document).one("click", function() { //对document绑定一个影藏Div方法
                  $("#tip").hide();
              });
              event.stopPropagation();//阻止事件向上冒泡
              return;
	  }
	  var a;
	  if(re.test(identifier2))
          a = sendemailcode(identifier2);
	  else
	      a = sendphonecode(identifier2);
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

//发送短信验证码
function sendphonecode(phone){
    var a=0;
    $.ajax({
        url: "/userauths/send_phonecodeLogin",
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
        }
    });
    return a;
}

//发送邮箱验证码
function sendemailcode(email){
    var a=0;
    $.ajax({
        url: "/userauths/send_emailcodeLogin",
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
        }
    });
    return a;
}


function getBgimg() {
    $.ajax({
        type:"post",
        url:"/bgimg/get_loginbgimg",
        async:false,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                $("body").css("background-image","url("+res.data.url+")");
            }
            else{
                layer.msg(res.msg);
            }
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}
