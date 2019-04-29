
$("#comment").click(function(){
	$("#main-iframe").load("comment.html");
});

$("#like").click(function(){
	$("#main-iframe").load("like.html");
});

$("#letter").click(function(){
	$("#main-iframe").load("letter.html");
});

$("#message").click(function(){
	$("#main-iframe").load("message.html");
});

$(document).ready(function(){  
     // $("#main-iframe").load(function(){
     //     var mainheight = $(this).contents().find("body").height()+10;
     //     $(this).height(mainheight);
     //     alert(22)
     //     $(this).load("comment.html");
     //
     //  });
    $("#main-iframe").load("comment.html");
});  

