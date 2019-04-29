var msg = new Vue({
    el:"#msg-box",
    data:{
        "message": [],
        "total":0
    },
    methods: {

    },
    created: function(){
        getmsg(1);
    }
});

function getmsg(pn){
    $.ajax({
        type:"post",
        data:{
            pn:pn
        },
        url:"/sysnotice/get_msg",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                msg.message = res.data.list;
                msg.total = res.data.total;
                if(pn==1)
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
            ,count: msg.total //数据总数，从服务端得到
            ,jump: function(obj, first) {
                //obj包含了当前分页的所有参数，比如：
                if(!first) {
                    getmsg(obj.curr);
                }
            }
        });
    });
}