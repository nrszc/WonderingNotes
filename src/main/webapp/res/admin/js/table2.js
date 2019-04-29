var layer;
layui.use('layer',function(){
    layer = layui.layer;
});


layui.use('laydate', function(){
    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        elem: '#date1' //指定元素
    });
});
layui.use('laydate', function(){
    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        elem: '#date2' //指定元素
    });
});

var report1 = new Vue({
    el:"#report",
    data:{
        "rep": "",
        "total": 1
    },
    methods: {
        reportpass:function () {
            reportccpass();
        },
        searchreport:function () {
            searchccreport(1);
        }
    },
    mounted:function(){

    },
    created: function(){
        check();
        getReportCommentC(1,this);
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

var re;
function getReportCommentC(pn,that) {
    re=that;
    $.ajax({
        type:"post",
        data:{
            "pn":pn
        },
        url:"/report/get_reportcc",
        async:false,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                that.rep = res.data.list;
                that.total = res.data.total;
                if(pn==1)
                    page(that);
                console.log(re.rep);
            }
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

var laypage;
function page(that){
    layui.use('laypage', function(){
        $("#page").show();
        $("#page1").hide();
        laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'page' //注意，这里的 test1 是 ID，不用加 # 号
            ,count: that.total //数据总数，从服务端得到
            ,jump: function(obj, first) {
                //obj包含了当前分页的所有参数，比如：
                if(!first) {
                    getReportCommentC(obj.curr,that);
                }
            }
        });
    });
}

function page1(that){
    layui.use('laypage', function(){
        $("#page1").show();
        $("#page").hide();
        laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'page1' //注意，这里的 test1 是 ID，不用加 # 号
            ,count: that.total //数据总数，从服务端得到
            ,jump: function(obj, first) {
                //obj包含了当前分页的所有参数，比如：
                if(!first) {
                    searchccreport(obj.curr);
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


function reportccpass() {
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
        url:"/report/update_ccreport",
        async:false,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                for(var i=0; i<re.rep.length;i++) {
                    if(checkID.indexOf(re.rep[i].id+"")!=-1) {
                        re.rep[i].status = 1;
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

function searchccreport(pn) {
    var status = $("#status").val();
    var date1 = $("#date1").val();
    var date2 = $("#date2").val();
    $.ajax({
        type:"post",
        data:{
            "status":status,
            "date1": date1,
            "date2": date2,
            "pn":pn
        },
        url:"/report/search_ccreport",
        async:false,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                re.rep = res.data.list;
                re.total = res.data.total;
                if(pn==1)
                    page1(re);
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