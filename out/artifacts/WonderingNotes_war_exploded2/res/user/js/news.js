
$("#comment").click(function(){
	$("#main-iframe").attr("src","../../view/user/comment.html");
});

$("#like").click(function(){
	$("#main-iframe").attr("src","../../view/user/like.html");
});

$("#letter").click(function(){
	$("#main-iframe").attr("src","../../view/user/letter.html");
});

$("#message").click(function(){
	$("#main-iframe").attr("src","../../view/user/message.html");
});

$(document).ready(function(){  
     $("#main-iframe").load(function(){
         var mainheight = $(this).contents().find("body").height()+10;
         $(this).height(mainheight);
      });
});  

