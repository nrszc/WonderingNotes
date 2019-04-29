$(document).ready(function(){

    setTimeout(function(){
        $(".my-message,.fri-message").each(function(){
            $(this).html(emojione.toImage($(this).text()));
        });

    }, 500);

var chat = new Vue({
    el:"#chat-container",
    data:{
        "user": [],
        "user1": [],
        "message":[],
        "nickname": "",
        "from_user_id": "",
        "to_user_id": "",
        "avatar":"",
        "user_id":""
    },
    methods: {
        send: function(){
            send();
        }
    },
    mounted:function(){

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
                chat.avatar = res.data.avatar;
                //alert(chat.avatar);
                chat.user_id = res.data.id;
            }
            else{
                layer.msg(res.msg)
            }
        }
    });
}

function getUserInfo1(user_id){
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

    var index = 0;
    var index1 = 0;


function send(){
    var text = $("#example1").val();
    if(text.length==0)
    {
        layer.msg("发送内容不能为空");
        return;
    }
    doSend();
    var str = '<div v-else class="my-message-wrapper1">';
    str+= '<div class="my-message-wrapper2">';
    str+= '<div class="my-message" id="test'+index+'">'+text+'</div>' ;
    str+= '<a href="index.html?location=user&user_id='+chat.user_id+'" target= "_blank" class="avatar-img2">'
    if(chat.avatar==null||chat.avatar=='')
        str+= '<img height="55" width="55" src="../../res/user/images/avatar.png" >';
    else
        str+= '<img height="55" width="55" src="'+chat.avatar+'" >' ;
    str+="</a></div></div>"
    $("#chat-box").append(str);
    var item = "#test"+index;
    index = index+1;
    var ele = document.getElementById('chat-box');
    ele.scrollTop = ele.scrollHeight;
    //对评论内容的emoji转化
     $(item).html(emojione.toImage($(item).text()));
}

//表情包相关
    $("#example1").emojioneArea({
        // options
        autoHideFilters: true
    });


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
       // alert(evt.data);
        var text1 = JSON.parse(evt.data);
        var text = JSON.parse(JSON.stringify(text1.message))
        $.ajax({
            type:"post",
            data:{
                "from_user_id": text.from_user_id,
                "to_user_id": text.to_user_id
            },
            url:"/message/update_message",
            async:true,
            success: function(res){
                console.log(res)
                if(res.status==1)
                    layer.msg(res.msg)
            }
        });
        var str = '<div v-if="m.from_user_id==from_user_id" class="fri-message-wrapper">';
        str+= '<a href="index.html?location=user&user_id='+chat.user1.id+'"  target= "_blank" class="avatar-img1">';
        if(chat.user1.avatar==null||chat.user1.avatar=='')
            str+= '<img height="55" width="55" src="../../res/user/images/avatar.png" >';
        else
            str+= '<img height="55" width="55" src="'+chat.user1.avatar+'" >';
        str+= '</a><div class="fri-message" id="ts'+index1+'">'+text.content+'</div> </div>';
        $("#chat-box").append(str);
        //对评论内容的emoji转化
        var item = "#ts"+index1;
        index1 = index1+1;
        var ele = document.getElementById('chat-box');
        ele.scrollTop = ele.scrollHeight;
        //对评论内容的emoji转化
        $(item).html(emojione.toImage($(item).text()));
    }
    function onError() {}
    function onClose() {}

    function doSend() {
        if (websocket.readyState == websocket.OPEN) {
            var content = $("#example1").val();
            let obj = {};
            obj.from_user_id = chat.user.id;
            obj.to_user_id  = chat.user1.id;
            obj.content = content;
            //websocket.send(msg);//调用后台handleTextMessage方法
            //websocket.send(JSON.stringify(obj));
            websocket.send(JSON.stringify({
                message : {
                    content : content,
                    from_user_id :chat.user.id,
                    to_user_id : chat.user1.id
                },
                type : "letter"
            }));
            //alert("发送成功!");
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

