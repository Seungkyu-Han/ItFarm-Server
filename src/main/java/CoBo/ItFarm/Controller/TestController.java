package CoBo.ItFarm.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Tag(name = "테스트 API(삭제 예정)")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @GetMapping("/test")
    public void test(){
        log.info("헬로우");
    }
}
