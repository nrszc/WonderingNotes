var layer;
layui.use('layer',function(){
    layer = layui.layer;
});


var user = new Vue({
    el:"#user",
    data:{
        "user": "",
        "total": 1
    },
    methods: {
        ban:function () {
            ban();
        },
        unban:function () {
            unban();
        },
        searchuser:function () {
            searchuser(1);
        }
    },
    mounted:function(){

    },
    created: function(){
        check();
        getuser(this,1);
    }
});

function check() {
    $.ajax({
        type:"post",
        url:"/admin/check",
        async:false,
        success: function(res){
            console.log(res)
            if(res.status == 1) {
                window.location.href="http://wsgzjh.cn/view/admin/login.html";
            }
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

var u;
function getuser(that,pn) {
    u = that;
    $.ajax({
        type:"post",
        data:{
            "pn":pn
        },
        url:"/user/get_adminuser",
        async:false,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                u.user = res.data.list;
                u.total = res.data.total;
                if(pn==1)
                    page(u);
            }
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

var laypage;
function page(u){
    layui.use('laypage', function(){
        $("#page").show();
        $("#page1").hide();
        laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'page' //注意，这里的 test1 是 ID，不用加 # 号
            ,count: u.total //数据总数，从服务端得到
            ,jump: function(obj, first) {
                //obj包含了当前分页的所有参数，比如：
                if(!first) {
                    getuser(u,obj.curr);
                }
            }
        });
    });
}

function page1(){
    layui.use('laypage', function(){
        $("#page1").show();
        $("#page").hide();
        laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'page1' //注意，这里的 test1 是 ID，不用加 # 号
            ,count: u.total //数据总数，从服务端得到
            ,jump: function(obj, first) {
                //obj包含了当前分页的所有参数，比如：
                if(!first) {
                    searchuser(obj.curr);
                }
            }
        });
    });
}


$("#selectAll").click(function () {
    if (this.checked) {
        $("input[name=check]:checkbox").prop("checked", true);
    } else {
        $("input[name=check]:checkbox").prop("checked", false);
    }
})


function ban() {
    var checkID=[];
    $("input[name='check']:checked").each(function(i){
        checkID[i] = $(this).val();
    });
    console.log(checkID)
    $.ajax({
        type:"post",
        data:{
            "checkID":checkID
        },
        url:"/user/ban_user",
        async:false,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                for(var i=0; i<u.user.length;i++) {
                    if(checkID.indexOf(u.user[i].user_id+"")!=-1) {
                        u.user[i].status = 1;
                    }
                }
                layer.msg(res.msg);
            }
            else {
                layer.msg(res.msg);
            }
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

function unban() {
    var checkID=[];
    $("input[name='check']:checked").each(function(i){
        checkID[i] = $(this).val();
    });
    console.log(checkID)
    $.ajax({
        type:"post",
        data:{
            "checkID":checkID
        },
        url:"/user/unban_user",
        async:false,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                for(var i=0; i<u.user.length;i++) {
                    if(checkID.indexOf(u.user[i].user_id+"")!=-1) {
                        u.user[i].status = 0;
                    }
                }
                layer.msg(res.msg);
            }
            else {
                layer.msg(res.msg);
            }
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

function searchuser(pn) {
    var status = $("#status").val();
    var word = $("#word").val();
    $.ajax({
        type:"post",
        data:{
            "word":word,
            "status":status,
            "pn":pn
        },
        url:"/user/search_byword",
        async:false,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                u.user = res.data.list;
                u.total = res.data.total;
                if(pn==1)
                    page1();
            }
            else {
                layer.msg(res.msg);
            }
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}