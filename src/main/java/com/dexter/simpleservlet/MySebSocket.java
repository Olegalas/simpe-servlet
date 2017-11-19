package com.dexter.simpleservlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/chat")
public class MySebSocket {

    private static final Logger log = LoggerFactory.getLogger(MySebSocket.class);

    private Session session;
    private static Set<MySebSocket> websockets = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session){
        log.info("New connection - {}", session.getId());
        this.session = session;
        websockets.add(this);

        broadcast("Success");
    }

    @OnMessage
    public void  onMessage(String message, Session session){
        log.info("Was received message : {}. SessionId : {}", message, session.getId());
        broadcast("Was received message : " + message);
    }

    @OnClose
    public void onClose(Session session) {
        log.info("Was closed connection with sessionId : {}", session.getId());
        websockets.remove(this);
        broadcast("Disconnected");
    }

    private static void broadcast(String message){
        websockets.forEach(ws ->{

            try {
                ws.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.warn("Exception during sending message : {}", e.getMessage());
            }

        });
    }

}
