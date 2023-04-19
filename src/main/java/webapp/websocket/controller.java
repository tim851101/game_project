package webapp.websocket;


import java.io.IOException;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import webapp.websocket.Util;

@Component
@ServerEndpoint(value = "/WebSocketServer/{usernick}")
public class controller {

    @OnOpen
    public void onOpen(@PathParam(value = "usernick") String userNick, Session session) {
        String message = "有新成員[" + userNick + "]加入聊天室!";
        System.out.println(message);
        Util.addSession(userNick, session);
        Util.sendMessageForAll(message);
    }

    @OnClose
    public void onClose(@PathParam(value = "usernick") String userNick, Session session) {
        String message = "成員[" + userNick + "]退出聊天室!";
        System.out.println(message);
      Util.remoteSession(userNick);
       Util.sendMessageForAll(message);
    }

    @OnMessage
    public void OnMessage(@PathParam(value = "usernick") String userNick, String message) {
        String info = "成員[" + userNick + "]：" + message;
        System.out.println(info);
      Util.sendMessageForAll(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("錯誤:" + throwable);
        try {
            session.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }

}