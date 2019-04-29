//登录方式切换
$(document).ready(function(){

$(function() {
	$(".head-msg a").hover(function() {
		$(".avatar-ul").hide();
		$(".msg-ul").show();
	});
	$(".msg-ul").hover(function() {
		$(this).show();
	}, function() {
		$(this).hide();
	})
});

$(function() {
	$(".head-avatar a").hover(function() {
		$(".msg-ul").hide();
		$(".avatar-ul").show();
	});
	$(".avatar-ul").hover(function() {
		$(this).show();
	}, function() {
		$(this).hide();
	})
});
});