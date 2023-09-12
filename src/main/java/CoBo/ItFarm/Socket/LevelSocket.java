package CoBo.ItFarm.Socket;

import CoBo.ItFarm.Data.Dto.Device.Res.DeviceLevelRes;
import CoBo.ItFarm.Data.Entity.LevelEntity;
import CoBo.ItFarm.Repository.LevelRepository;
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
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class LevelSocket extends TextWebSocketHandler {

    private final List<WebSocketSession> webSocketSessionList = new ArrayList<>();
    private final LevelRepository levelRepository;

    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessionList.add(session);
        log.info("클라이언트 접속 : {}", session);
        List<DeviceLevelRes> deviceLevelResList = new ArrayList<>();
        for(LevelEntity levelEntity : levelRepository.getPastData(
                new Timestamp(new Date().getTime() - Duration.ofHours(2).toMillis())))
            deviceLevelResList.add(new DeviceLevelRes(levelEntity));
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(deviceLevelResList)));
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
        webSocketSessionList.remove(session);
        log.info("클라이언트 접속 해제: {}", session);
    }
}
