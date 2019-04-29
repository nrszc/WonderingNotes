var layer;
layui.use('layer', function(){
    layer = layui.layer;
});

var laypag;
function pagination() {
    layui.use('laypage', function () {
        laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'pagination' //注意，这里的 test1 是 ID，不用加 # 号
            , count: search.total //数据总数，从服务端得到
            , jump: function (obj, first) {
                //obj包含了当前分页的所有参数，比如：
                // console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                // console.log(obj.limit); //得到每页显示的条数
                if (!first)
                    searchUserByWord(obj.curr);
            }
        });
    });
}

var search = new Vue({
    el:"#search1",
    data:{
        "user": [],
        "article": [],
        "total": 0,
        "search_word": ""
    },
    methods: {
        ToFollow: function(user_id,index){
            ToFollow(user_id,index);
        },
        CancelFollow: function (user_id,index) {
            CancelFollow(user_id, index);
        },
        ToMsg:function (user_id) {
            window.open("http://wsgzjh.cn/view/user/index.html?location=chat&user_id="+user_id);
        }
    },
    created: function(){
        this.search_word = getUrlParam('search_word');
        searchUserByWord(1);
    }
});


//获取路径参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}

function searchUserByWord(pn) {
    var search_word = getUrlParam('search_word');
    $.ajax({
        url: "/user/search_userbyword",
        data: {
            "pn": pn,
            "search_word": search_word
        },
        type: 'post',
        success: function (res) {
            console.log(res);
            if (res.status == 0) {
                search.user = res.data.list;
                search.total = res.data.total;
                for(var i = 0; i < search.user.length; i++)
                {
                    if(search.user[i].isfans != null)
                        search.user[i].isfans = true;
                    else
                        search.user[i].isfans = false;
                }
                if(pn==1)
                    pagination();
            }
            else
                layer.msg("找不到该游记");
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

function ToFollow(user_id, index) {
    $.ajax({
        type:"post",
        data:{
            "user_id" : user_id
        },
        url:"/userfans/tofollow",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                layer.msg(res.msg);
                search.user[index].isfans = res.data;
            }
        }
    });
}

function CancelFollow(user_id, index) {
    $.ajax({
        type:"post",
        data:{
            "user_id" : user_id
        },
        url:"/userfans/cancelfollow",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                layer.msg(res.msg);
                search.user[index].isfans =  res.data;
            }
        }
    });
}
