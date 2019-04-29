var layer;
layui.use('layer', function(){
    layer = layui.layer;
});

// setTimeout(function(){
//     $(".content").each(function(){
//         $(this).html(emojione.toImage($(this).text()));
//     });
// }, 500);

var like = new Vue({
    el:"#like-box",
    data:{
        "favor": []
    },
    methods: {
        commentfavor: function(){
            commentfavor();

        },
        articlefavor: function(){
            articlefavor();

        }
    },
    created: function(){
        articlefavor();
    }
});

function articlefavor(){
    $.ajax({
        type:"post",
        url:"/report/articlefavor",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                like.favor = res.data;
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

function commentfavor(){
    $.ajax({
        type:"post",
        url:"/report/commentfavor",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                like.favor = res.data;
                $("span .content").each(function(){
                    $(this).html(emojione.toImage($(this).text()));
                });
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