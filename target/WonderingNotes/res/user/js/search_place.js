var layer;
layui.use('layer', function(){
    layer = layui.layer;
});

var laypage;
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
                if(!first)
                searchPlaceByWord(obj.curr);
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
        ToFollow: function(){
            ToFollow(home.user.id);
        },
        tofavor: function(articleid,index){
            tofavor(articleid,index);
        },
        cancelfavor:function (articleid,index) {
            cancelfavor(articleid,index);
        }
    },
    created: function(){
        this.search_word = getUrlParam('search_word');
        searchPlaceByWord(1);
    }
});


//获取路径参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}

function searchPlaceByWord(pn) {
    var search_word = getUrlParam('search_word');
    $.ajax({
        url: "/article/search_placebyword",
        data: {
            "pn": pn,
            "search_word": search_word
        },
        type: 'post',
        success: function (res) {
            console.log(res);
            if (res.status == 0) {
                search.article = res.data.list;
                search.total = res.data.total;
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

function tofavor(articleid,index){
    $.ajax({
        type:"post",
        data:{
            articleid:articleid
        },
        url:"/articlefavor/insert_favor",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                search.article[index].isfavor="0";
                search.article[index].favor+=1;
            }
            else
                layer.msg(res.msg);
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

function cancelfavor(articleid,index){
    $.ajax({
        type:"post",
        data:{
            articleid:articleid
        },
        url:"/articlefavor/cancel_favor",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                search.article[index].isfavor=null;
                search.article[index].favor-=1;
            }
            else
                layer.msg(res.msg);
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}