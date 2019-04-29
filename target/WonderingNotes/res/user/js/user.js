var layer;
layui.use('layer', function(){
    layer = layui.layer;
});

var laypage;
function page(){
    $("#page1").hide();
    $("#page2").hide();
    $("#page").show();
    layui.use('laypage', function(){
        laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'page' //注意，这里的 test1 是 ID，不用加 # 号
            ,count: home.total //数据总数，从服务端得到
            ,jump: function(obj, first) {
                //obj包含了当前分页的所有参数，比如：
                if(!first) {
                    getUserArticle(obj.curr);
                }
            }
        });
    });
}

function page1(){
    $("#page").hide();
    $("#page2").hide();
    $("#page1").show();
    layui.use('laypage', function(){
        laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'page1' //注意，这里的 test1 是 ID，不用加 # 号
            ,count: home.total //数据总数，从服务端得到
            ,jump: function(obj, first) {
                //obj包含了当前分页的所有参数，比如：
                if(!first) {
                    getUserFans(obj.curr);
                }
            }
        });
    });
}

function page2(){
    $("#page").hide();
    $("#page1").hide();
    $("#page2").show();
    layui.use('laypage', function(){
        laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'page2' //注意，这里的 test1 是 ID，不用加 # 号
            ,count: home.total //数据总数，从服务端得到
            ,jump: function(obj, first) {
                //obj包含了当前分页的所有参数，比如：
                if(!first) {
                    getUserFollows(obj.curr);
                }
            }
        });
    });
}

var home = new Vue({
    el:"#home",
    data:{
        "user": [],
        "article": [],
        "users":[],
        "total":0,
        "isfans": false
    },
    methods: {
        ToFollow: function(user_id){
            ToFollow(user_id);
        },
        CancelFollow: function(user_id){
            CancelFollow(user_id);
        },
        getUserFans:function () {
            getUserFans(1);
        },
        getUserFollows:function () {
            getUserFollows(1);
        },
        ToFollow1: function(user_id,index){
            ToFollow1(user_id,index);
        },
        CancelFollow1: function (user_id,index) {
            CancelFollow1(user_id, index);
        },
        tofavor: function(articleid,index){
            tofavor(articleid,index);
        },
        cancelfavor:function (articleid,index) {
            cancelfavor(articleid,index);
        },
        ToMsg:function (user_id) {
            window.open("http://wsgzjh.cn/view/user/index.html?location=chat&user_id="+user_id);
        }
    },
    created: function(){
        var user_id = getUrlParam("user_id");
        getUserInfo(user_id);
        getUserArticle(1);
        getUserFollow(user_id);
    }
});

function getUserInfo(user_id) {
    $.ajax({
        type:"post",
        data:{
            "user_id" : user_id
        },
        url:"/user/get_userinfo1",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                home.user = res.data;
            }
            else{

            }
        }
    });
}

function getUserArticle(pn) {
    var user_id = getUrlParam("user_id");
    $("#MN").addClass("link-active");
    $("#MFans").removeClass("link-active");
    $("#MFollows").removeClass("link-active");
    $("#show-article").show();
    $("#show-user").hide();
    $.ajax({
        type:"post",
        data:{
            "user_id" : user_id,
            "pn":pn
        },
        url:"/article/get_userarticle1",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                home.article = res.data.list;
                home.total = res.data.total;
                if(pn==1)
                    page();
            }
            else{

            }
        }
    });
}

function getUserFollows(pn){
    $("#MN").removeClass("link-active");
    $("#MFans").removeClass("link-active");
    $("#MFollows").addClass("link-active");
    $("#show-article").hide();
    $("#show-user").show();
    var user_id = getUrlParam("user_id");
    $.ajax({
        type:"post",
        data:{
            "user_id" : user_id,
            "pn":pn
        },
        url:"/user/get_userfollows1",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                home.users = res.data.list;
                home.total = res.data.total;
                if(pn==1)
                    page2();
            }
            else{
                layer.msg(res.msg);
            }
        }
    });
}

function getUserFans(pn){
    $("#MN").removeClass("link-active");
    $("#MFans").addClass("link-active");
    $("#MFollows").removeClass("link-active");
    $("#show-article").hide();
    $("#show-user").show();
    var user_id = getUrlParam("user_id");
    $.ajax({
        type:"post",
        data:{
            "user_id" : user_id,
            "pn":pn
        },
        url:"/user/get_userfans1",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                home.users = res.data.list;
                home.total = res.data.total;
                if(pn==1)
                    page1();
            }
            else{
                layer.msg(res.msg);
            }
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
                home.isfans = res.data;
            }
            else{
                window.location.href = 'http://wsgzjh.cn/view/user/index.html?location=home';
            }
        }
    });
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
                layer.msg(res.msg)
                home.isfans = res.data;
            }
            else{
                window.location.href = "http://wsgzjh.cn/view/user/index.html?location=home"
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
                layer.msg(res.msg)
                home.isfans = res.data;
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

function ToFollow1(user_id, index) {
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
                home.users[index].isfans = res.data;
            }
            else if(res.status=2){
                layer.msg(res.msg)
            }
        }
    });
}

function CancelFollow1(user_id, index) {
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
                home.users[index].isfans = res.data;
            }
        }
    });
}

function tofavor(articleid,index){
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
                home.article[index].isfavor="0";
                home.article[index].favor+=1;
            }
            else
                layer.msg(res.msg);
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

function cancelfavor(articleid,index){
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
                home.article[index].isfavor=null;
                home.article[index].favor-=1;
            }
            else
                layer.msg(res.msg);
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}