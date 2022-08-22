package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    // ex가 넘어왔을 때, 정상적인 ModelAndView로 반환해주면 된다.
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {

        try {
            if (ex instanceof IllegalArgumentException) {
                // 서버 내부에서 IllegalArgumentException 이 발생하면, 예외를 먹고 400 Error로 보낸다.
                log.info("IllegalArgumentException resolver to 400");

                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                // 새로운 ModelAndView 반환. 매개변수를 빈 값으로 넘기면, 정상적인 흐름으로 WAS까지 return이 된다.
                // 그리고 WAS는 sendError가 400으로 호출되었다고 인식한다.
                return new ModelAndView();
            }
        }
        // sendError가 IOException이 checkedException으로 되어있어서, try catch로 잡아줘야 한다.
        catch (IOException e) {
            log.error("resolver ex {}", e);
        }

        return null; // return null을 하면 예외가 다시 터져서 계속 날라간다.
    }
}
