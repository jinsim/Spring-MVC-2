package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId"; // 문자로 쓰기 그러니까 상수로 빼둠.

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        // 예외를 반환하는 afterCompletion에 UUID를 넘겨야한다.
        request.setAttribute(LOG_ID, uuid);

        //@RequestMapping를 사용하는 경우 HandlerMethod
        //정적 리소스를 사용하는 경우 ResourceHttpRequestHandler
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler; //호출할 컨트롤러 메서드의 모든 정보가 포함되어 있다.
        }
        // 일반적으로 사용하는 컨트롤러는 instanceof HandlerMethod 확인하고 사용하면 된다.

        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        return true; // false면 진행X, true면 위 handler가 실제 호출된다.
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView); // modelAndView 확인
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String)request.getAttribute(LOG_ID);
        log.info("RESPONSE [{}][{}]", logId, requestURI);
        if (ex != null) { // 예외가 있으면 예외를 로깅
            log.error("afterCompletion error!!", ex);
        }
    }
}
