var layer;
layui.use('layer', function(){
    layer = layui.layer;
});

var laypage;
function page(){
    $("#page1").hide();
    $("#page2").hide();
    $("#page3").hide();
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
    $("#page3").hide();
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
                    getUserCollectArticle(obj.curr);
                }
            }
        });
    });
}

function page2(){
    $("#page1").hide();
    $("#page").hide();
    $("#page3").hide();
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
                    getUserFans(obj.curr);
                }
            }
        });
    });
}

function page3(){
    $("#page1").hide();
    $("#page2").hide();
    $("#page").hide();
    $("#page3").show();
    layui.use('laypage', function(){
        laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'page3' //注意，这里的 test1 是 ID，不用加 # 号
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
        "total": 0
    },
    methods: {
        moreopre: function(index){
            moreopre(index);
        },
        DeleteArticle: function(articleid,index){
            DeleteArticle(articleid,index);
        },
        getUserArticle: function () {
            getUserArticle(1);
        },
        getUserCollectArticle:function () {
            getUserCollectArticle(1);
        },
        CancelCollectArticle:function (articleid,index) {
            CancelCollectArticle(articleid,index);
        },
        getUserFans:function () {
            getUserFans(1);
        },
        getUserFollows:function () {
            getUserFollows(1);
        },
        ToFollow: function(user_id,index){
            ToFollow(user_id,index);
        },
        CancelFollow: function (user_id,index) {
            CancelFollow(user_id, index);
        }
    },
    created: function(){
        $(".more-opre").hide();
        getUserInfo();
        getUserArticle(1);
    }
});

function getUserInfo() {
    $.ajax({
        type:"post",
        url:"/user/get_userinfo",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
               home.user = res.data;
            }
            else{
                window.location.href="http://wsgzjh.cn/view/user/login.html";
            }
        }
    });
}

function getUserArticle(pn) {
    $("#MN").addClass("link-active");
    $("#MCN").removeClass("link-active");
    $("#MFans").removeClass("link-active");
    $("#MFollows").removeClass("link-active");
    $("#show-article").show();
    $("#show-user").hide();
    $(".mynotes").show();
    $(".mynotes").show();
    $(".mycollectnotes").hide();
    $.ajax({
        type:"post",
        data:{
            "pn":pn
        },
        url:"/article/get_userarticle",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                home.article = res.data.list;
                home.total = res.data.total;
                if(pn=1)
                    page();
            }
            else{
                 layer.msg(res.msg);
            }
        }
    });
}

function getUserCollectArticle(pn){
    $("#MN").removeClass("link-active");
    $("#MCN").addClass("link-active");
    $("#MFans").removeClass("link-active");
    $("#MFollows").removeClass("link-active");
    $("#show-article").show();
    $("#show-user").hide();
    $(".mynotes").hide();
    $(".mycollectnotes").show();
    $.ajax({
        type:"post",
        data:{
            "pn":pn
        },
        url:"/articlecollect/get_usercollectarticle",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                home.article = res.data.list;
                home.total = res.data.total;
                if(pn=1)
                    page1();
            }
            else{
                layer.msg(res.msg);
            }
        }
    });
}

function getUserFollows(pn){
    $("#MN").removeClass("link-active");
    $("#MCN").removeClass("link-active");
    $("#MFans").removeClass("link-active");
    $("#MFollows").addClass("link-active");
    $("#show-article").hide();
    $("#show-user").show();
    $.ajax({
        type:"post",
        data:{
            "pn":pn
        },
        url:"/user/get_userfollows",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                home.users = res.data.list;
                home.total = res.data.total;
                if(pn=1)
                    page3();
            }
            else{
                layer.msg(res.msg);
            }
        }
    });
}

function getUserFans(pn){
    $("#MN").removeClass("link-active");
    $("#MCN").removeClass("link-active");
    $("#MFans").addClass("link-active");
    $("#MFollows").removeClass("link-active");
    $("#show-article").hide();
    $("#show-user").show();
    $.ajax({
        type:"post",
        data:{
            "pn":pn
        },
        url:"/user/get_userfans",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                home.users = res.data.list;
                home.total = res.data.total;
                if(pn=1)
                    page2();
            }
            else{
                layer.msg(res.msg);
            }
        }
    });
}

$("#file_btn").click(function(){
    //获取编辑器的内容，不带样式，纯文本
    $("#file").click();
});

$("#file").change(function(){
    var From = new FormData();
    From.append("file", $('#file')[0].files[0]);
    $.ajax({
        url: "/user/update_bgimg",
        data: From,
        type: 'post',
        processData: false,
        contentType: false,
        success: function (res) {
            console.log(res);
            if(res.status==1) {
                layer.msg(res.msg);
            }
            else {
                var preview = document.querySelector(".img-banner");
                var file    = document.querySelector("#file").files[0];
                var reader  = new FileReader();
                reader.onloadend = function () {
                    title_img = reader.result;
                    home.user.bgimg = reader.result;
                    //$(".img-banner").css("background", "url("+reader.result+")");
                }
                if (file) {
                    reader.readAsDataURL(file);
                }
            }
        }
    });

});

$("#uploadmusic").click(function(){
    //获取编辑器的内容，不带样式，纯文本
    $("#music").click();
});

$("#music").change(function(){
    var From = new FormData();
    From.append("music", $('#music')[0].files[0]);
    $.ajax({
        url: "/user/update_music",
        data: From,
        type: 'post',
        processData: false,
        contentType: false,
        success: function (res) {
            console.log(res);
            if(res.status==1) {
                layer.msg(res.msg);
            }
            else {
                home.user.music = res.data;
                layer.msg(res.msg);
            }
        }
    });

});

$("#edit").click(function(){
    var profile = $(".profile span").text().trim();
    layer.open({
        id:1,
        type: 0,
        title:'签名',
        skin:'layui-layer-rim',
        area:['400px', '220px'],
        content: '<textarea id="editarea" style="width: 350px; height: 80px;"></textarea> '
        ,
        btn:['保存','取消'],
        btn1: function (index,layero) {
            $.ajax({
                data:{
                    "signature": $("#editarea").val()
                },
                type:"post",
                url:"/user/update_signature",
                async:true,
                success: function(res){
                    console.log(res)
                    if(res.status == 0) {
                        home.user.signature = $("#editarea").val()
                    }
                    layer.msg(res.msg);
                }
            });
        },
        btn2:function (index,layero) {
            layer.close(index);
        }

});
    document.getElementById("editarea").value=profile;


});

$(".wn-avatar img").click(function(){
    window.open("http://wsgzjh.cn/view/user/index.html?location=avatar");
});

function  moreopre(index){
    var id = "#more-opre"+index;
    // showDiv();//调用显示DIV方法
    $(id).toggle();
    $(document).one("click",
        function() { //对document绑定一个影藏Div方法
            $(id).hide();
        });

    event.stopPropagation(); //阻止事件向上冒泡
}

$(".more-opre").click(function(event) {
    event.stopPropagation(); //阻止事件向上冒泡
});

function DeleteArticle(articleid,index) {
    $.ajax({
        url: "/article/delete_article",
        data: {
            articleid: articleid
        },
        type: 'post',
        success: function (res) {
            console.log(res);
            if(res.status==0) {
                home.article.splice(index, 1);
                layer.msg(res.msg);
            }
            else {
                layer.msg(res.msg);
            }
        }
    });
}

function CancelCollectArticle(articleid,index) {
    $.ajax({
        url: "/articlecollect/cancelcollect",
        data: {
            articleid: articleid
        },
        type: 'post',
        success: function (res) {
            console.log(res);
            if(res.status==0) {
                home.article.splice(index, 1);
                layer.msg(res.msg);
            }
            else {
                layer.msg(res.msg);
            }
        }
    });
}

function ToFollow(user_id, index) {
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
        }
    });
}

function CancelFollow(user_id, index) {
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
