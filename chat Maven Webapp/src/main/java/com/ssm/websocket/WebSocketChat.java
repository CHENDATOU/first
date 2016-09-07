package com.ssm.websocket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import com.chat.pojo.User;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */

@ServerEndpoint(value="/websocket" ,configurator=GetHttpSessionConfigurator.class)
public class WebSocketChat {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketChat> webSocketSet = new CopyOnWriteArraySet<WebSocketChat>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
     
    //当前会话的httpession
    private HttpSession httpSession;
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session,EndpointConfig config){
    	//得到httpSession
    	this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());
    	this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
    	//获得消息发来时间
    	SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sd.format(new Date());
        //替换表情
    	System.out.println("来自客户端的消息:" + message);
    	message = replaceImage(message);
        //得到当前用户名
    	User user = (User)this.httpSession.getAttribute("user");
    	String name = user.getName();
    	//群发消息
        for(WebSocketChat item: webSocketSet){
            try {
                item.sendMessage(name+" "+time+"</br>"+message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }
    
    public void sendMessage(String message,String userid) throws IOException{
    	
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketChat.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketChat.onlineCount--;
    }
    //替换表情
    private String replaceImage(String message){
    	for(int i=1;i<11;i++){
    		
    		if(message.contains("<:"+i+":>")){
    			message = message.replace("<:"+i+":>", "<img "
    		    + "src='/chat/Images/" + i + ".gif' id='" + i + "' />");
    		}
    	}
    	return message;
    }
}
