package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

//@Component // 스프링에 등록
// Runtime Exception이 WAS에 전달되거나, response.sendError()가 호출되면, 등록한 예외 페이지 경로가 호출된다.
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {

        // 톰캣 서버에 에러페이지를 등록한다.
        // 404 에러가 발생하면, "/error-page/404" 이 컨트롤러를 호출해라. 나머지도 동일.
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");
        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");

        // 등록을 해야함.
        factory.addErrorPages(errorPage404, errorPage500, errorPageEx);
    }
}
