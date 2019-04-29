var layer;
layui.use('layer', function(){
    layer = layui.layer;
});

function mycarousel() {
    layui.use('carousel', function () {
        var carousel = layui.carousel;
        //建造实例
        carousel.render({
            elem: '#test1'
            , width: '1000px' //设置容器宽度
            , height: '350px'
            , arrow: 'always' //始终显示箭头
        });
    });
}



var laypage;

function pagination(){
    layui.use('laypage', function(){
        laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'pagination' //注意，这里的 test1 是 ID，不用加 # 号
            ,count: main.total //数据总数，从服务端得到
            ,jump: function(obj, first) {
                //obj包含了当前分页的所有参数，比如：
                if(!first) {
                    getAllPage(obj.curr);
                }
            }
        });
    });
}


function pagination1() {
    layui.use('laypage', function () {
        laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'pagination1' //注意，这里的 test1 是 ID，不用加 # 号
            , count: main.newtotal //数据总数，从服务端得到
            , jump: function (obj, first) {
                //obj包含了当前分页的所有参数，比如：
                // console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                // console.log(obj.limit); //得到每页显示的条数
                if(!first)
                   getNewPage(obj.curr);
            }
        });
    });
}

function pagination2() {
    layui.use('laypage', function () {
        laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'pagination2' //注意，这里的 test1 是 ID，不用加 # 号
            , count: main.hottotal //数据总数，从服务端得到
            , jump: function (obj, first) {
                //obj包含了当前分页的所有参数，比如：
                // console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                // console.log(obj.limit); //得到每页显示的条数
                if(!first)
                   getHotPage(obj.curr);
            }
        });
    });
}

var main = new Vue({
    el:"#main-container",
    data:{
        "user": [],
        "article": [],
        "carousel": [],
        "total": 9
    },
    methods: {
        tofavor: function(articleid,index){
            tofavor(articleid,index);
        },
        cancelfavor:function (articleid,index) {
            cancelfavor(articleid,index);
        },
        write_notes:function () {
            window.open("http://wsgzjh.cn/view/user/index.html?location=write_notes");
        }
    },
    created: function(){
        getUserState();
       getCarousel();
       getAllPage(1);
    }
});

$(".tn-nar-all a").click(function(){
    $(".tn-nar-new a").removeClass("tn-nar-active");
    $(".tn-nar-hot a").removeClass("tn-nar-active");
    $(".tn-nar-all a").addClass("tn-nar-active");
    $("#pagination").show();
    $("#pagination1").hide();
    $("#pagination2").hide();
    getAllPage(1);
});
$(".tn-nar-new a").click(function(){
    $(".tn-nar-all a").removeClass("tn-nar-active");
    $(".tn-nar-hot a").removeClass("tn-nar-active");
    $(".tn-nar-new a").addClass("tn-nar-active");
    $("#pagination1").show();
    $("#pagination").hide();
    $("#pagination2").hide();
    getNewPage(1);
});
$(".tn-nar-hot a").click(function(){
    $(".tn-nar-new a").removeClass("tn-nar-active");
    $(".tn-nar-all a").removeClass("tn-nar-active");
    $(".tn-nar-hot a").addClass("tn-nar-active");
    $("#pagination2").show();
    $("#pagination1").hide();
    $("#pagination").hide();
    getHotPage(1);
});

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
                main.article[index].isfavor="0";
                main.article[index].favor+=1;
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
                main.article[index].isfavor=null;
                main.article[index].favor-=1;
            }
            else
                layer.msg(res.msg);
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}


function getUserState(){
    $.ajax({
        type:"post",
        url:"/user/get_userstate",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 1) {
                $(".tn-nar-new").hide();
                $(".write-nar").hide();
            }
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

function getCarousel(){
    $.ajax({
        type:"post",
        url:"/article/get_carousel",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                main.carousel = res.data;
                console.log("test", main.carousel);
                mycarousel();
            }
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

function getAllPage(pn) {
    $.ajax({
        type:"post",
        data:{
            pn : pn
        },
        url:"/article/get_allpage",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                main.article = res.data.list;
                main.total = res.data.total;
                if(pn==1)
                    pagination();
            }
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

function getNewPage(pn) {
    $.ajax({
        type:"post",
        data:{
            pn : pn
        },
        url:"/article/get_newpage",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                main.article = res.data.list;
                main.newtotal = res.data.total;
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

function getHotPage(pn) {
    $.ajax({
        type:"post",
        data:{
            pn : pn
        },
        url:"/article/get_hotpage",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                main.article = res.data.list;
                main.hottotal = res.data.total;
                if(pn==1)
                    pagination2();
            }

        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}
