
$(document).ready(function() {
	
  var layer;
  layui.use('layer', function(){
     layer = layui.layer;
  });

  
  // //表情包相关
  // $("#example1").emojioneArea({
  //   // options
  //   autoHideFilters: true
  // });
  //
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

var laypage;
function pagination() {
    layui.use('laypage', function () {
        laypage = layui.laypage;
        $("pagination1").hide();
        $("pagination").show();
        var articleid = getUrlParam("articleid");
        //执行一个laypage实例
        laypage.render({
            elem: 'pagination' //注意，这里的 test1 是 ID，不用加 # 号
            , count: show_notes.total //数据总数，从服务端得到
            , jump: function (obj, first) {
                if(!first)
                    getComment(articleid,obj.curr);
            }
        });
    });
}

function pagination1() {
    layui.use('laypage', function () {
        laypage = layui.laypage;
        $("pagination").hide();
        $("pagination1").show();
        var articleid = getUrlParam("articleid");
        //执行一个laypage实例
        laypage.render({
            elem: 'pagination1' //注意，这里的 test1 是 ID，不用加 # 号
            , count: show_notes.total //数据总数，从服务端得到
            , jump: function (obj, first) {
                if(!first)
                    getNewComment(articleid,obj.curr);
            }
        });
    });
}

//搜索框2
var show_notes = new Vue({
    el:"#wn-container",
    data:{
        "article": "",
        "search_value": "",
        "isfans": false,
        "iscollect": false,
        "comment":"",
        "total": 0
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
        },
        tofavor: function(articleid){
            tofavor(articleid);
        },
        cancelfavor:function (articleid) {
            cancelfavor(articleid);
        },
        tocomment:function (articleid) {
            tocomment(articleid);
        },
        newcomment:function (articleid) {
            getNewComment(articleid,1);
        },
        hotcomment:function (articleid) {
            getComment(articleid,1);
        },
        getreply:function (commentid, index) {
            getreply(commentid, index);
        },
        clickuser:function (user_id) {
            window.open("http://wsgzjh.cn/view/user/index.html?location=user&user_id="+user_id);
        },
        tofavorcomment:function (commentid, index) {
            tofavorcomment(commentid, index);
        },
        cancelfavorcomment:function (commentid, index) {
            cancelfavorcomment(commentid, index);
        },
        toreply:function (commentid, nickname, user_id) {
            toreply(commentid, nickname, user_id);
        },
        commentreport:function (commentid) {
            commentreport(commentid);
        },
        commentcommentreport:function (cc_id) {
            commentcommentreport(cc_id);
        },
        articlereport:function (articleid) {
            articlereport(articleid);
        },
    },
    created: function(){
        var articleid = getUrlParam("articleid");
        var user_id = getUrlParam("user_id");
        show_notes(articleid);
        getUserFollow(user_id);
        getArticleCollect(articleid);
        getComment(articleid,1)
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
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

function getComment(articleid,pn) {
    $.ajax({
        type:"post",
        data:{
            "articleid" : articleid,
            "pn": pn
        },
        url:"/articlecomment/get_comment",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                $(".hot").hide();
                $(".new").show();
                $("#mynewcomment").hide();
                show_notes.comment = res.data.list;
                for(var i=0; i<show_notes.comment.length; i++) {
                    show_notes.$set(show_notes.comment[i], 'isshow', false);
                }
                console.log(show_notes.comment)
                show_notes.total = res.data.total;
                if(pn==1)
                    pagination();
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

function getNewComment(articleid,pn) {
    $.ajax({
        type:"post",
        data:{
            "articleid" : articleid,
            "pn": pn
        },
        url:"/articlecomment/get_newcomment",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                $(".new").hide();
                $(".hot").show();
                $("#mynewcomment").hide();
                show_notes.comment = res.data.list;
                for(var i=0; i<show_notes.comment.length; i++) {
                    show_notes.$set(show_notes.comment[i], 'isshow', false);
                }
                show_notes.total = res.data.total;
                if(pn==1)
                    pagination1();
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

function getreply(commentid, index) {
    $.ajax({
        type:"post",
        data:{
            "commentid" : commentid
        },
        url:"/articlecomment/get_reply",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                show_notes.comment[index].isshow = true;
                show_notes.comment[index].comments = res.data;
                // var comment = "comment"+index;
                // var commentt = "commentt"+index;
                // $(comment).hide();
                // $(commentt).show();
                console.log(show_notes.comment)
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

function tofavor(articleid){
    $.ajax({
        type:"post",
        data:{
            articleid:articleid
        },
        url:"/articlefavor/insert_favor",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                show_notes.article.isfavor="0";
                show_notes.article.favor+=1;
            }
            else
                layer.msg(res.msg);
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

function cancelfavor(articleid){
    $.ajax({
        type:"post",
        data:{
            articleid:articleid
        },
        url:"/articlefavor/cancel_favor",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                show_notes.article.isfavor=null;
                show_notes.article.favor-=1;
            }
            else
                layer.msg(res.msg);
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

function tocomment(articleid){
    var content = $("#example1").val();
    $.ajax({
        type:"post",
        data:{
            articleid:articleid,
            content:content
        },
        url:"/articlecomment/tocomment",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                layer.msg(res.msg);
                showcomment(content);
            }
            else
                layer.msg(res.msg);
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}
var index = 0;
function showcomment(content) {
    $.ajax({
        type:"post",
        url:"/user/get_userinfo",
        async:false,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                var str = '<div class="comment-item">';
                str+='<div class="comment-avatar"> <a href="index.html?location=user&user_id='+res.data.id+'" target="_blank">';
                if(res.data.avatar==null||res.data.avatar=='')
                    str+='<img src="../../res/user/images/avatar.png" width="48" height="48">';
                else
                    str+='<img src="'+res.data.avatar+'" width="48" height="48">';
                str+='</a></div><div class="comment-div"><div class="main-comment"><span class="comment-name">'
                str+=''+res.data.nickname+':';
                str+='</span><span class="comments" id="emoji'+index+'">'+content+'';
                str+='</span></div> </span></div></div></div>';
                $("#mynewcomment").append(str);

                index++;

                $("#mynewcomment").show();
            }
            else
                layer.msg(res.msg);
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

function tofavorcomment(commentid, index) {
    $.ajax({
        type:"post",
        data:{
            "commentid" : commentid
        },
        url:"/articlecomment/to_favorcomment",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                show_notes.comment[index].isfavor = 1;
                show_notes.comment[index].favor+= 1;
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

function cancelfavorcomment(commentid, index) {
    $.ajax({
        type:"post",
        data:{
            "commentid" : commentid
        },
        url:"/articlecomment/cancel_favorcomment",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                show_notes.comment[index].isfavor = null;
                show_notes.comment[index].favor-= 1;
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

function toreply(commentid, nickname, user_id){
    layer.open({
        id:1,
        type: 0,
        title:'回复',
        skin:'layui-layer-rim',
        area:['450px', '300px'],
        content: '<textarea id="example2" placeholder="回复 '+nickname+'" style="width: 400px; height: 160px;"></textarea> '
        ,
        btn:['回复','取消'],
        btn1: function (index,layero) {
            var content=top.$('#example2').val();
            toreplyit(commentid, user_id, content);
        },
        btn2:function (index,layero) {
            layer.close(index);
        }

    });

}

function toreplyit(commentid, user_id, content) {
    $.ajax({
        type:"post",
        data:{
            "comment_id" : commentid,
            "reply_id": user_id,
            "content": content
        },
        url:"/articlecomment/to_replyit",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                layer.msg(res.msg);
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

function commentreport(commentid){
    layer.confirm('您确定要举报吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type:"post",
            data:{
                "comment_id" : commentid,
            },
            url:"/report/commentreport",
            async:true,
            success: function(res){
                console.log(res)
                if(res.status == 0) {
                    layer.msg(res.msg);
                }
                else{
                    layer.msg(res.msg);
                }
            },
            error:function(res){
                layer.msg("服务器出错，请重试！");
            }
        });
    }, function(){
        layer.closeAll('dialog');
    });
}

function commentcommentreport(cc_id){
    layer.confirm('您确定要举报吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type:"post",
            data:{
                "cc_id" : cc_id,
            },
            url:"/report/commentcommentreport",
            async:true,
            success: function(res){
                console.log(res)
                if(res.status == 0) {
                    layer.msg(res.msg);
                }
                else{
                    layer.msg(res.msg);
                }
            },
            error:function(res){
                layer.msg("服务器出错，请重试！");
            }
        });
    }, function(){
        layer.closeAll('dialog');
    });
}

function articlereport(articleid){
    layer.confirm('您确定要举报吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type:"post",
            data:{
                "article_id" : articleid,
            },
            url:"/report/articlereport",
            async:true,
            success: function(res){
                console.log(res)
                if(res.status == 0) {
                    layer.msg(res.msg);
                }
                else{
                    layer.msg(res.msg);
                }
            },
            error:function(res){
                layer.msg("服务器出错，请重试！");
            }
        });
    }, function(){
        layer.closeAll('dialog');
    });
}