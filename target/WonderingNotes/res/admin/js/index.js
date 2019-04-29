new Vue({
    el:"#bgimg",
    data:{
        "bgimg": []
    },
    methods: {

    },
    mounted:function(){

    },
    created: function(){
         check();
    }
})

function check() {
    $.ajax({
        type:"post",
        url:"/admin/check",
        async:true,
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