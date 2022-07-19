package hello.login;

import hello.login.web.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter()); // 우리가 만든 로그 필터를 넣는다.
        filterRegistrationBean.setOrder(1); // 필터가 체인으로 들어갈 수 있으니 순서 설정
        filterRegistrationBean.addUrlPatterns("/*"); // 어떤 URL 패턴에 적용할 것인지. 여기서는 모든 경로 설정

        return filterRegistrationBean;
    }
}
