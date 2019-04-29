var layer;
layui.use('layer', function(){
    layer = layui.layer;
});

var comment = new Vue({
    el:"#comment-box",
    data:{
        "comments": []
    },
    methods: {
        commentcomment: function(){
            commentcomment();
        },
        articlecomment: function(){
            articlecomment();
        }
    },
    created: function(){
        articlecomment();
    }
});

function articlecomment(){
    $.ajax({
        type:"post",
        url:"/report/articlecomment",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                comment.comments = res.data;
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

function commentcomment(){
    $.ajax({
        type:"post",
        url:"/report/commentcomment",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                comment.comments = res.data;
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