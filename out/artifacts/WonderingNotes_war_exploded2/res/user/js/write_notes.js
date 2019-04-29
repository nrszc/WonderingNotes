//layui需引入，一般直接写在一个js文件中
var layer;
layui.use(['layer', 'form'], function(){
    layer = layui.layer
        ,form = layui.form;
});

var title_img = "";

$(function(){

    var E = window.wangEditor;
    //这里的id为<div id="editor"中的id.
    var editor = new E('#editor');
    //Base64保存图片
    editor.customConfig.uploadImgShowBase64 = true;
    //创建编辑器
    editor.create();

     $("#editorGetBtn1").click(function(){
         var title = $(" #title ").val();
         if(title.length == 0) {
             layer.msg("请输入游记标题");
             return;
         }
         if(title_img.length == 0) {
             layer.msg("请插入游记头图");
             return;
         }
         if(editor.txt.text().length==0)
         {
             layer.msg("游记内容不能为空");
             return;
         }
         sessionStorage.setItem('title',title);
         sessionStorage.setItem('title_img',title_img);
         sessionStorage.setItem('article',editor.txt.html());
         window.open("../../view/user/index.html?location=perview_notes");
     })
     $("#editorGetBtn2").click(function(){
         var title = $(" #title ").val();
         var city = $(" #city ").val();
         if(title.length == 0) {
             layer.msg("请输入游记标题");
             return;
         }
         if(city.length == 0) {
             layer.msg("请城市名称");
             return;
         }
         if(title_img.length == 0) {
             layer.msg("请插入游记头图");
             return;
         }
         alert(editor.txt.text());
         if(editor.txt.text().length==0)
         {
             layer.msg("游记内容不能为空");
             return;
         }
         alert(editor.txt.text().substring(0, 250));
         $("#article").attr("value",editor.txt.html());
         $("#outline").attr("value",editor.txt.text().substring(0, 250));
         $.ajax({
             url: "/article/submit_notes",
             data: new FormData($('#noteform')[0]),
             type: 'post',
             processData: false,
             contentType: false,
             success: function (res) {
                 console.log(res);
                 if(res.status==1) {
                     layer.msg(res.msg);
                 }
             }
         });
    })
})

$("#file_btn").click(function(){
    //获取编辑器的内容，不带样式，纯文本
    $("#file").click();
});

$("#file").change(function(){
    var preview = document.querySelector(".img-banner");
    var file    = document.querySelector("#file").files[0];
    var reader  = new FileReader();
    reader.onloadend = function () {
        title_img = reader.result;
       $(".img-banner").css("background", "url("+reader.result+")");
    }
    if (file) {
       reader.readAsDataURL(file);
    } else {
       $(".img-banner").css("background", "none");
    }
});