package com.ssm.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;
/*
 * 获取HttpSession的配置类
 * 只要在websocket类上@ServerEndpoint注解中加入configurator=GetHttpSessionConfigurator.class
 * 再在 @OnOpen注解的方法里加入参EndpointConfig config
 * 通过config得到httpSession
 */

public class GetHttpSessionConfigurator extends Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec,
            HandshakeRequest request, HandshakeResponse response) {
        // TODO Auto-generated method stub
        HttpSession httpSession=(HttpSession) request.getHttpSession();
        sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }
    
}