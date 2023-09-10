package CoBo.ItFarm.Socket;

import CoBo.ItFarm.Data.Dto.Socket.SocketTestRes;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Component
@Slf4j
public class SocketTest extends TextWebSocketHandler {

    private final List<WebSocketSession> webSocketSessionList = new ArrayList<>();

    private final ObjectMapper objectMapper;

    public SocketTest(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SendTask(), 1000, 1000);
        objectMapper = new ObjectMapper();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessionList.add(session);
        log.info(session + "클라이언트 접속");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : " + payload);

        for(WebSocketSession socketSession : webSocketSessionList)
            socketSession.sendMessage(message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(session + " 클라이언트 접속 해제");

        webSocketSessionList.remove(session);
    }

    public class SendTask extends TimerTask{

        @Override
        public void run() {
            for(WebSocketSession webSocketSession : webSocketSessionList){

                SocketTestRes socketTestRes = SocketTestRes.builder()
                        .name("Seungkyu")
                        .time(new Timestamp(System.currentTimeMillis()).toString())
                        .build();
                try {
                    webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(socketTestRes)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
