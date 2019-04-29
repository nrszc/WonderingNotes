var layer;
layui.use('layer', function(){
    layer = layui.layer;
});

layui.use(['layer', 'form'], function(){
layer = layui.layer
,form = layui.form;
});


var setting = new Vue({
    el:"#setting",
    data:{
        "user": [],
        "aa":"1"
    },
    methods: {
        Save: function(){
            Save();
        },
        CheckNickname: function () {
            CheckNickname();
        }

    },
    created: function(){
       getUser();
    }
});

function getUser() {
    $.ajax({
        type:"post",
        url:"/user/get_user",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                setting.user = res.data;
                if(setting.user.sex==null||setting.user.sex=='')
                    setting.user.sex = 0;
            }
            else{
                window.location.href="http://wsgzjh.cn/view/user/login.html";
            }
        }
    });
}

function CheckNickname() {
    $.ajax({
        type:"post",
        data:{
             "nickname": setting.user.nickname
        },
        url:"/user/check_nickname",
        async:true,
        success: function(res){
            console.log(res)
            layer.msg(res.msg);
        }
    });
}

function Save() {
    $.ajax({
        type:"post",
        data:{
            "nickname": setting.user.nickname,
            "city":setting.user.city,
            "birthday":setting.user.birthday,
            "signature":setting.user.signature,
            "sex":$("input[name='sex']:checked").val()
        },
        url:"/user/save",
        async:true,
        success: function(res){
            console.log(res)
            layer.msg(res.msg);
        }
    });
}