package CoBo.ItFarm.Socket;

import CoBo.ItFarm.Config.Jwt.JwtTokenProvider;
import CoBo.ItFarm.Data.Dto.Device.Res.DeviceLevelRes;
import CoBo.ItFarm.Data.Dto.Socket.SocketTestRes;
import CoBo.ItFarm.Data.Entity.LevelEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class SocketTest extends TextWebSocketHandler {
    private final List<WebSocketSession> webSocketSessionList = new ArrayList<>();

    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessionList.add(session);
        log.info(session + "클라이언트 접속");
    }

    @EventListener
    public void sendData(DeviceLevelRes deviceLevelRes){
        for(WebSocketSession webSocketSession : webSocketSessionList){
            try{
                webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(deviceLevelRes)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(session + " 클라이언트 접속 해제");

        webSocketSessionList.remove(session);
    }
}
