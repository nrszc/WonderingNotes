

var chart = new Vue({
    el:"#chart-box",
    data:{
        "user": [],
        "article":[],
        "sumuser":0,
        "sumarticle":0
    },
    methods: {
        articleSelect:function () {
            getstaarticle();
        },
        userSelect:function () {
            getstauser();
        }
    },
    created: function(){
        check();
        getsumuser();
        getsumarticle();
        getstauser();
        getstaarticle();
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


function getsumuser() {
    $.ajax({
        type:"post",
        url:"/user/get_sumuser",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                chart.sumuser = res.data;
            }
            else
                layer.msg(res.msg);
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

function getsumarticle() {
    $.ajax({
        type:"post",
        url:"/article/get_sumarticle",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                chart.sumarticle = res.data;
            }
            else
                layer.msg(res.msg);
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

function getstauser() {
    var year = $("#userSelect").children('option:selected').val();
    $.ajax({
        type:"post",
        data:{
            "year": year
        },
        url:"/user/get_stauser",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                chart.user = res.data;
                var x = new Array();
                for(var i=1;i<=12;i++)
                {
                    x[i] = 0;
                }
                for(var i=0; i<chart.user.length; i++)
                    x[chart.user[i].month] = chart.user[i].sum;
                main(x);
            }
            else
                layer.msg(res.msg);
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

function getstaarticle() {
    var year = $("#articleSelect").children('option:selected').val();
    $.ajax({
        type:"post",
        data:{
            "year": year
        },
        url:"/article/get_staarticle",
        async:true,
        success: function(res){
            console.log(res)
            if(res.status == 0) {
                chart.article = res.data;
                var x = new Array();
                for(var i=1;i<=12;i++)
                {
                    x[i] = 0;
                }
                for(var i=0; i<chart.article.length; i++)
                    x[chart.article[i].month] = chart.article[i].sum;
                main1(x);
            }
            else
                layer.msg(res.msg);
        },
        error:function(res){
            layer.msg("服务器出错，请重试！");
        }
    });
}

var option,option1;

function main(x) {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    option = {
        title: {
            text: '月新增用户'
        },
        tooltip: {},
        legend: {
            data:['人数']
        },
        xAxis: {
            data: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"]
        },
        yAxis: {},
        series: [{
            name: '人数',
            type: 'bar',
            data: [x[1], x[2], x[3], x[4], x[5], x[6], x[7], x[8], x[9], x[10], x[11], x[12]]
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

function main1(x) {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main1'));

    // 指定图表的配置项和数据
    option1 = {
        title: {
            text: '月发布文章'
        },
        tooltip: {},
        legend: {
            data:['数量']
        },
        xAxis: {
            data: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"]
        },
        yAxis: {},
        series: [{
            name: '数量',
            type: 'bar',
            data: [x[1], x[2], x[3], x[4], x[5], x[6], x[7], x[8], x[9], x[10], x[11], x[12]]
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option1);
}