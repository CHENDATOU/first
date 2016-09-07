$(function () { 
var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080/chat/websocket");
    }
    else {
        alert('当前浏览器版本过低不支持websocket!!!')
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    }

    //连接成功建立的回调方法
    websocket.onopen = function () {
        setMessageInnerHTML("WebSocket连接成功");
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
    	showMessageInnerHTML(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("WebSocket连接关闭");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    //同时退出聊天室（下线）
    window.onbeforeunload = function () {
        closeWebSocket();
        downLine();
    }

    //将是否连接websocket消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        $("#message").append(innerHTML+"</br>");
    }
    //将消息显示在页面上
    function showMessageInnerHTML(innerHTML){
//    	document.getElementById('chat-dialog-con').innerHTML += innerHTML + '<br/>';
    	$("#chat-dialog-con ul").append("<li>"+innerHTML+"</li>");
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

    //发送消息
    function send() {
        var message = $("#txtInput").val();
        websocket.send(message);
    }


    //添加表情
    for (var i = 1; i <= 10; i++) {
        $("#chat-input-expr").html($("#chat-input-expr").html() + "<img src='/chat/Images/" + i + ".gif' id='" + i + "' />");
        //html和text方法不一样，前者可添加html标签和纯文本，后者只可添加纯文本。
    }

    //向消息中添加表情
    $("#chat-input-expr img").click(function () {
        $("#txtInput").val($("#txtInput").val() + "<:" + $(this).attr("ID") + ":>");
    });

    //6.发送消息判断
    $("#btnSend").click(function () {
        var sendMsg = $("#txtInput");
        if (sendMsg.val() != "") {
            send();
            sendMsg.val("")
        }
        else {
            alert("发送内容不能为空!");
            sendMsg.focus();
            return false;
        }
    });
    
    //得到在线用户
    getUser();
    setInterval("getUser()",3000);
});
