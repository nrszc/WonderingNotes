

//登录方式切换
$(document).ready(function(){

//登录状态
    var main = new Vue({
        el:"#main",
        data:{
            "user": [],
            "identity_type": 0
        },
        methods: {
            login: function(){
                dologin();
            },
            searchbyword: function(){
                searchbyword();
            },
            logout:function () {
                logout();
            }
        },
        created: function(){
            var that = this;
            $.ajax({
                type:"post",
                url:"/user/get_userinfo",
                async:true,
                success: function(res){
                    console.log(res)
                    if(res.status == 0) {
                        $(".head-login").hide();
                        $(".login-done").show();
                        that.user = res.data;
                        sessionStorage.setItem("user_id", res.data.user_id);
                    }
                    else{
                        $(".login-done").hide();
                        $(".head-login").show();
                        // $(".tn-nar-new a").hide();
                    }
                }
            });

            $.ajax({
                type:"post",
                url:"/user/getInfo",
                async:true,
                success: function(res){
                    console.log(res)
                    if(res.status == 0) {
                        that.identity_type = res.data;
                    }
                    else{
                    }
                }
            });

        }
    });

//消息下拉框
$(function() {
	$(".head-msg a").hover(function() {
		$(".avatar-ul").hide();
		$(".msg-ul").show();
	});
	$(".msg-ul").hover(function() {
		$(this).show();
	}, function() {
		$(this).hide();
	})
});

//头像下拉框
$(function() {
	$(".head-avatar a").hover(function() {
		$(".msg-ul").hide();
		$(".avatar-ul").show();
	});
	$(".avatar-ul").hover(function() {
		$(this).show();
	}, function() {
		$(this).hide();
	})
});
});

//导航栏选项切换
$("#a1").click(function(){
  $("#a2").removeClass("head-nav-active");
  $("#a3").removeClass("head-nav-active");
  $("#a1").addClass("head-nav-active");
});

$("#a2").click(function(){
  $("#a1").removeClass("head-nav-active");
  $("#a3").removeClass("head-nav-active");
  $("#a2").addClass("head-nav-active");
});

$("#a3").click(function(){
  $("#a1").removeClass("head-nav-active");
  $("#a2").removeClass("head-nav-active");
  $("#a3").addClass("head-nav-active");
});

function searchbyword(){
    var search_word = $(" #search ").val();
    window.location.href = "http://wsgzjh.cn/view/user/index.html?location=search_title&search_word="+ search_word;
}

//获取路径参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null; 
}

//frame跳转
$(function(){
		var name=getUrlParam('location');
		var search  = getUrlParam('search_word');
		if(search!=null)
           $("#search").attr("value", search);
        // if(name==null)
        // {
        //     $("#frame").load("main.html");
        //     return;
        // }
		// if(name.toString().indexOf("/") != -1)
         //   name = name.substring(0, name.indexOf("/"));
		switch (name)
        {
        	 case null:
                    $("#frame").load("main.html");
                    break;  
             case "regist":
                    $("#frame").load("regist.html");
                    break;
             case "home":
                    $("#frame").load("home.html");
                    break;  
             case "test":
                    $("#frame").load("test1.html");
                    break;  
             case "write_notes":
                    $("#frame").load("write_notes.html");
                    break; 
             case "show_notes":
                    $("#frame").load("show_notes.html");
                    break; 
             case "chat":
                    $("#frame").load("chat.html");
                    break; 
             case "avatar":
                    $("#frame").load("avatar.html");
                    break;
             case "perview_notes":
                    $("#frame").load("perview_notes.html");
                    break;
             case "user":
                    $("#frame").load("user.html");
                    break;
             case "search_title":
                    $("#frame").load("search_title.html");
                    break;
             case "search_place":
                    $("#frame").load("search_place.html");
                    break;
             case "search_user":
                    $("#frame").load("search_user.html");
                    break;
             case "setting":
                    $("#frame").load("setting.html");
                    break;
             case "updatepw1":
                    $("#frame").load("updatepw1.html");
                    break;
             case "news":
                    $("#frame").load("news.html");
                    break;
                    
        }
	});

function logout() {
    $.ajax({
        type:"post",
        url:"/user/logout",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                window.location.href="http://wsgzjh.cn/view/user/index.html";
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