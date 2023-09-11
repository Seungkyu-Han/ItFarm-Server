package CoBo.ItFarm.Socket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final LevelSocket levelSocket;
    private final MeasurementSocket measurementSocket;
    private final PredictionSocket predictionSocket;
    private final WarningSocket warningSocket;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(levelSocket, "/no-auth/level").setAllowedOrigins("*")
                .addHandler(measurementSocket, "/no-auth/measurement").setAllowedOrigins("*")
                .addHandler(predictionSocket, "/no-auth/prediction").setAllowedOrigins("*")
                .addHandler(warningSocket, "/auth/warning").setAllowedOrigins("*");
    }
}
