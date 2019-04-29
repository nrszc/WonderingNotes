var layer;
layui.use('layer',function(){
    layer = layui.layer;
});


layui.use('form', function(){
    var form = layui.form;

    //各种基于事件的操作，下面会有进一步介绍
});


var message = new Vue({
    el:"#message",
    data:{
        "msg": [],
        "total": 1
    },
    methods: {
        addMsg: function () {
            addMsg();
        }
    },
    mounted:function(){

    },
    created: function(){
        check();
        getNoticePulice(1);
    }
})

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

function addMsg(){
    layer.open({
        id:1,
        type: 0,
        title:'系统通知',
        skin:'layui-layer-rim',
        area:['450px', '300px'],
        content: '<textarea id="msg1" rows="9" cols="53" placeholder="@全体人员"></textarea> ',
        btn:['发送','取消'],
        btn1: function (index,layero) {
            var txt = document.getElementById("msg1").value;
            sendMsg(txt);
        },
        btn2:function(index,layero) {
            layer.close(index);
        }

    });
}

function sendMsg(content) {
    $.ajax({
        type:"post",
        data:{
            "content":content
        },
        url:"/sysnotice/send_notice",
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


function getNoticePulice(pn) {
    $.ajax({
        type:"post",
        data:{
            "pn":pn
        },
        url:"/sysnotice/get_noticepublic",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                message.msg = res.data.list;
                message.total = res.data.total;
                if(pn=1)
                    page();
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

var laypage;
function page(){
    layui.use('laypage', function(){
        laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'page' //注意，这里的 test1 是 ID，不用加 # 号
            ,count: message.total //数据总数，从服务端得到
            ,jump: function(obj, first) {
                //obj包含了当前分页的所有参数，比如：
                if(!first) {
                    getNoticePulice(obj.curr);
                }
            }
        });
    });
}