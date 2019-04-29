//表情包相关
  $("#example1").emojioneArea({
    // options
    autoHideFilters: true
  });

var chat = new Vue({
    el:"#chat-container",
    data:{
        "user": [],
        "user1": [],
        "message":[],
        "nickname": "",
        "from_user_id": "",
        "to_user_id": ""
    },
    methods: {
        login: function(){
            dologin();
        }
    },
    created: function(){
        var user_id = getUrlParam('user_id');
        this.from_user_id = user_id;
        getUserInfo();
        getUserInfo1(user_id);
        getUserMessage(user_id);
    }
})

function getUserInfo(){
    $.ajax({
        type:"post",
        url:"/user/get_userinfo",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                chat.user = res.data;
            }
            else{
                layer.msg(res.msg)
            }
        }
    });
}

function getUserInfo1(user_id){
    alert(user_id)
    $.ajax({
        type:"post",
        data:{
            user_id: user_id
        },
        url:"/user/get_userinfo1",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                chat.user1 = res.data;
            }
            else{
                layer.msg(res.msg)
            }
        }
    });
}

function getUserMessage(user_id){
    $.ajax({
        type:"post",
        data:{
            "user_id": user_id
        },
        url:"/message/get_usermessage",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                chat.message = res.data;
            }
            else{
                layer.msg(res.msg)
            }
        }
    });
}

$(document).ready(function(){
    var websocket = null;
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://wsgzjh.cn/websocket/socketServer");
    }
    else if ('MozWebSocket' in window) {
        websocket = new MozWebSocket("ws://wsgzjh.cn/websocket/socketServer");
    }
    else {
        websocket = new SockJS("http://wsgzjh.cn/sockjs/socketServer");
    }
    websocket.onopen = onOpen;
    websocket.onmessage = onMessage;
    websocket.onerror = onError;
    websocket.onclose = onClose;

    function onOpen(openEvt) {
        //alert(openEvt.Data);
    }

    function onMessage(evt) {
        alert(evt.data);
    }
    function onError() {}
    function onClose() {}

    function doSend() {
        if (websocket.readyState == websocket.OPEN) {
            var content = $("#example1").val();
            let obj = {};
            obj.nickname = 'jinkkkk';
            obj.id = 12;
            //websocket.send(msg);//调用后台handleTextMessage方法
            websocket.send(JSON.stringify(obj));
            alert("发送成功!");
        } else {
            alert("连接失败!");
        }
    }
    window.close=function()
    {
        websocket.onclose();
    }
});


//获取路径参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}