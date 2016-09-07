<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
    <title>聊天室</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/chat.css" />
    <script src="${pageContext.request.contextPath}/Js/jquery-1.8.2.min.js"></script>
    <script src="${pageContext.request.contextPath}/Js/chat.js"></script>    
	<script type="text/javascript">
		 //获取当前用户
	    function getUser(){
	    $("#chat-user-con ul").html("");
	    	$.post("${pageContext.request.contextPath}/user/getAll",{},
	    		function(data){
	    		var temp;
	    		for(temp=0;temp<data.length;temp++){
	    			$("#chat-user-con ul").append("<li>"+data[temp].name+"</li>");
	    		}
	    	},"json");
	    }
    
    	//下线
    	function downLine(){
    		$.post("${pageContext.request.contextPath}/user/downLine",{},
    		function(){});
    	}
	</script>
</head>
<body>
	<span id="message"></span>
    <div id="chat">
        <div id="chat-top">
            <div id="chat-dialog">
                <div id="chat-dialog-t">聊天室</div>
                <div id="chat-dialog-con">
                    <ul>

                    </ul>
                </div>
            </div>
            <div id="chat-user">
                <div id="chat-user-t">当前在线用户</div>
                <div id="chat-user-con">
                    <ul>

                    </ul>
                </div>
            </div>
        </div>
        <div id="chat-bottom">
            <div id="chat-input">
                <div id="chat-input-expr">
                    <!--<img src="Images/1.gif" id="1" /><img src="Images/2.gif" id="2" /><img src="Images/3.gif" id="3" /><img src="Images/4.gif" id="4" /><img src="Images/5.gif" id="5" /><img src="Images/6.gif" id="6" /><img src="Images/7.gif" id="7" /><img src="Images/8.gif" id="8" /><img src="Images/9.gif" id="9" /><img src="Images/10.gif" id="10" />-->
                </div>
                <div id="chat-input-edit">
                    <div id="input-field">
                        <textarea id="txtInput"></textarea>
                    </div>
                    <div id="input-btn">
                        <input id="btnSend" type="button" value="发送" />
                    </div>
                </div>
                <div id="chat-input-tip">发送内容不能为空</div>
            </div>
        </div>
    </div>
    <div id="chat-msg"></div>
</body>
</html>
