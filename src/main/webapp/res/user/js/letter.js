var layer;
layui.use('layer', function(){
    layer = layui.layer;
});

setTimeout(function(){
    $(".letter-down span").each(function(){
        $(this).html(emojione.toImage($(this).text()));
    });
}, 500);


var letter = new Vue({
    el:"#letter-box",
    data:{
        "user": [],
        "message":[]
    },
    methods: {
        tochat: function(user_id){
            tochat(user_id);
        }
    },
    created: function(){
        getMessage();
    }
})

function getMessage() {
    $.ajax({
        type:"post",
        url:"/message/get_message",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                letter.message = res.data;
            }
            else
                layer.msg(res.msg);
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

function tochat(user_id) {
    window.open("http://wsgzjh.cn/view/user/index.html?location=chat&user_id="+user_id);
}