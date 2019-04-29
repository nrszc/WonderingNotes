var layer;
layui.use('layer', function(){
    layer = layui.layer;
});
function previewImg(obj) {
    var imgHtml = "<img src='" + obj + "' width='830px' height='500px'/>";
    //弹出层
    layer.open({
        type: 1,
        shade: 0.8,
        offset: 'auto',
        area: [830 + 'px',545+'px'],
        shadeClose:true,
        scrollbar: false,
        title: "图片预览", //不显示标题
        content: imgHtml, //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
        cancel: function () {
            //layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', { time: 5000, icon: 6 });
        }
    });
}

var bgimg = new Vue({
    el:"#bgimg",
    data:{
        "img": []
    },
    methods: {
        uploadFile: function () {
            uploadFile();
        },
        deleteimg: function (bgimg_id, index) {
            deleteimg(bgimg_id, index);
        },
        previewImg: function (that) {
            previewImg(that);
        }
    },
    mounted:function(){

    },
    created: function(){
        check();
        getBgimg();
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

function getBgimg() {
    $.ajax({
        type:"post",
        url:"/bgimg/get_bgimg",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                bgimg.img = res.data;
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


function uploadFile() {
    var From = new FormData();
    From.append("file", $('#file')[0].files[0]);
    $.ajax({
        url: "/bgimg/upload_bgimg",
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
                bgimg.img.push(res.data);
                console.log(bgimg.img);
            }
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

function deleteimg(bgimg_id, index) {
    layer.confirm('您确定要删除吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type:"post",
            data:{
                bgimg_id : bgimg_id
            },
            url:"/bgimg/delete_bgimg",
            async:true,
            success: function(res){
                console.log(res)
                if(res.status == 0) {
                    bgimg.img.splice(index, 1);
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
    }, function(){
        layer.closeAll('dialog');
    });

}