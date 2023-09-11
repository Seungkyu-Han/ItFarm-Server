package CoBo.ItFarm.Socket;


import CoBo.ItFarm.Data.Dto.Device.Res.DeviceWarningRes;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class WarningSocket extends TextWebSocketHandler {

    private final List<WebSocketSession> webSocketSessionList = new ArrayList<>();

    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessionList.add(session);
        log.info("클라이언트 접속 : {}", session);
    }

    @EventListener
    public void sendData(DeviceWarningRes deviceWarningRes){
        for(WebSocketSession webSocketSession : webSocketSessionList){
            try{
                webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(deviceWarningRes)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketSessionList.remove(session);
        log.info("클라이언트 접속 해제: {}", session);
    }
}
