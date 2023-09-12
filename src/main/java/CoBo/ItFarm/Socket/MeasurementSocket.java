package CoBo.ItFarm.Socket;

import CoBo.ItFarm.Data.Dto.Device.Res.DeviceLevelRes;
import CoBo.ItFarm.Data.Dto.Device.Res.DeviceMeasurementRes;
import CoBo.ItFarm.Data.Entity.LevelEntity;
import CoBo.ItFarm.Data.Entity.MeasurementEntity;
import CoBo.ItFarm.Repository.MeasurementRepository;
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

@Slf4j
@RequiredArgsConstructor
@Component
public class MeasurementSocket extends TextWebSocketHandler {

    private final List<WebSocketSession> webSocketSessionList = new ArrayList<>();
    private final MeasurementRepository measurementRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessionList.add(session);
        log.info("클라이언트 접속 : {}", session);
        List<DeviceMeasurementRes> deviceMeasurementResList = new ArrayList<>();
        for(MeasurementEntity measurementEntity : measurementRepository.getPastData(
                new Timestamp(new Date().getTime() - Duration.ofHours(24).toMillis())))
            deviceMeasurementResList.add(new DeviceMeasurementRes(measurementEntity));
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(deviceMeasurementResList)));
    }

    @EventListener
    public void sendData(DeviceMeasurementRes deviceMeasurementRes){
        for(WebSocketSession webSocketSession : webSocketSessionList){
            try{
                webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(deviceMeasurementRes)));
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
