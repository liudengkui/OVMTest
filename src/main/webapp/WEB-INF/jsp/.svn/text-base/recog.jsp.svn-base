<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>第一个页面</title>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-1.11.0.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
<input id="timestamp" type="hidden" value="${wxJsapi.timestamp}" />
<input id="noncestr" type="hidden" value="${wxJsapi.nonceStr}" />
<input id="signature" type="hidden" value="${wxJsapi.signature}"/>
<a id="paizhao">
	<img src="${ctx}/images/xiangji.png"/>
</a>
<p>
<img id=img src=""/>
<p>
<p>
<div id = "txtrslt">

</div>
</body>
<script type="text/javascript">
$(function() {
	wx.config( {
		debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId : 'wx04f43ef4cd7a7b3e', // 必填，公众号的唯一标识
		timestamp : $("#timestamp").val(), // 必填，生成签名的时间戳
		nonceStr : $("#noncestr").val(), // 必填，生成签名的随机串
		signature : $("#signature").val(),// 必填，签名，见附录1
		jsApiList : ['chooseImage', 'uploadImage']
	// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
	
	$("#paizhao").click(function(){
	  	wx.chooseImage({
			count: 1, // 默认9
			sizeType: ['original'], // 可以指定是原图还是压缩图，默认二者都有
	        sourceType: ['camera'], // 可以指定来源是相册还是相机，默认二者都有
			success: function (res) {
				var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
				$("#img").attr({src:localIds});
				
				wx.uploadImage({
	                localId: localIds[0].toString(), // 需要上传的图片的本地ID，由chooseImage接口获得
	               // isShowProgressTips: 1, // 默认为1，显示进度提示
	                success: function (res) {
	                    var mediaId = res.serverId; // 返回图片的服务器端ID，即mediaId
	                    //将获取到的 mediaId 传入后台 方法savePicture
	                    $.ajax({
	                		type : "POST",
	                		url : "${ctx}/wechat/savePicture",
	                		data:{"mediaId":mediaId},
	                		success : function(data) {
	                			$('#txtrslt').empty();
	                			var xmldoc = $.parseXML(data);
	                    		var stat = 0;
	                    		$(xmldoc).find("status").each(function(i){
	                    			stat = $(this).text();
	                    		});
	                    		if(parseInt(stat)<0){
	                    			$(xmldoc).find("message").each(function(i){
	                    				$(xmldoc).find("value").each(function(i){
	                    					$("#txtrslt").append( $("<span />").text($(this).text() ));
	                    				});
	            	           		});
	                    		}else{
	                    			$(xmldoc).find("card").each(function(i){
	                    				$(this).find("item").each(function(l){
	                    					if("头像"!=$(this).attr('desc')){
	                    						$("#txtrslt").append( $("<span />").text($(this).attr('desc')+":"));
	                    						$("#txtrslt").append( $("<span />").text($(this).text() ));
	                    						$("#txtrslt").append( $("<br />"));
	                    					}
	                    				});
	                    			});
	                    		}
	                		}
	                	});
	                },
	                fail: function (res) {
	                    alertModal('上传图片失败，请重试')
	                }
	            }); 
			}
		});
	});
});

</script>
</html>