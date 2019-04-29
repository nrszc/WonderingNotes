
$(document).ready(function() {
	
  var layer;
  layui.use('layer', function(){
     layer = layui.layer;
  }); 
  
  $(".comment-reply-a").click(function(){
  	layer.open({
    id:1,
        type: 0,
        title:'回复',
        skin:'layui-layer-rim',
        area:['450px', '300px'],
        content: '<textarea id="example2"></textarea> '
        ,
        btn:['回复','取消'],
        btn1: function (index,layero) {
        },
        btn2:function (index,layero) {
             layer.close(index);
        }
 
    });
    
    $("#example2").emojioneArea({
    // options
    autoHideFilters: true
    
  });
  });
  
  //表情包相关
  $("#example1").emojioneArea({
    // options
    autoHideFilters: true
  });
  
  //消息下拉框
$(function() {
	$("a .share").hover(function() {
		$("#share").show();
	});
	$("#share").hover(function() {
		$(this).show();
	}, function() {
		$(this).hide();
	})
});
  
  
  
});

//搜索框2
var show_notes = new Vue({
    el:"#wn-container",
    data:{
        "article": "",
        "search_value": "",
        "isfans": false,
        "iscollect": false
    },
    methods: {
        ToFollow:function(user_id){
            ToFollow(user_id);
        },
        CancelFollow:function(user_id){
            CancelFollow(user_id);
        },
        ToCollect:function(articleid){
            ToCollect(articleid);
        },
        CancelCollect:function(articleid){
            CancelCollect(articleid);
        }
    },
    created: function(){
        var articleid = getUrlParam("articleid");
        var user_id = getUrlParam("user_id");
        show_notes(articleid);
        getUserFollow(user_id);
        getArticleCollect(articleid);
    }
});

function show_notes(articleid) {
    $.ajax({
        url: "/article/show_notes",
        data: {
            "articleid": articleid
        },
        type: 'post',
        success: function (res) {
            console.log(res);
            if (res.status == 0) {
                show_notes.article = res.data;
            }
            else
                layer.msg("找不到该游记");
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

function getUserFollow(user_id) {
    $.ajax({
        type:"post",
        data:{
            "user_id" : user_id
        },
        url:"/userfans/get_userfollow",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                show_notes.isfans = res.data;
            }
            else{
                $(".follows").hide();
            }
        }
    });
}

//获取url/后面的参数值，该参数值就是用户的id
function getValue(url){
    //如果不写这一行 将会取最后一个/前所有值
    var url = url.substr(url.lastIndexOf('/', url.lastIndexOf('/') - 1) + 1);
    //获取最后一个/的位置
    var site = url.lastIndexOf("\/");
    //截取最后一个/后的值
    return url.substring(site+1, url.length);
}


//获取路径参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}

function ToFollow(user_id) {
    $.ajax({
        type:"post",
        data:{
            "user_id" : user_id
        },
        url:"/userfans/tofollow",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                layer.msg(res.msg);
                show_notes.isfans = res.data;
            }
        }
    });
}

function CancelFollow(user_id) {
    $.ajax({
        type:"post",
        data:{
            "user_id" : user_id
        },
        url:"/userfans/cancelfollow",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                layer.msg(res.msg);
                show_notes.isfans =  res.data;
            }
        }
    });
}

function getArticleCollect(articleid) {
    $.ajax({
        type:"post",
        data:{
            "articleid" : articleid
        },
        url:"/articlecollect/get_articlecollect",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                show_notes.iscollect = res.data;
            }
        }
    });
}

function ToCollect(articleid) {
    $.ajax({
        type:"post",
        data:{
            "articleid" : articleid
        },
        url:"/articlecollect/tocollect",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                layer.msg(res.msg);
                show_notes.iscollect = res.data;
            }
        }
    });
}

function CancelCollect(articleid) {
    $.ajax({
        type:"post",
        data:{
            "articleid" : articleid
        },
        url:"/articlecollect/cancelcollect",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                layer.msg(res.msg);
                show_notes.iscollect =  res.data;
            }
        }
    });
}