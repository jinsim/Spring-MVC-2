package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 사용하는 이유가 있다기 보다는, 스프링이 이렇게 제공해주는 것. 외워야 한다.
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**") // 서블릿이랑 패턴이 다르다. / 하위 전부를 나타냄.
                .excludePathPatterns("/css/**", "/*.ico", "/error"); // 이 경로는 해당 인터셉터를 호출하지 않는다.

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                // 패턴을 편하고 세밀하게 할 수 있어서 편리하다.
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/member/add", "/login", "/logout",
                        "/css/**", "/*.ico", "/error");
    }

//    @Bean // 로그는 인터셉터를 활용한다.
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter()); // 우리가 만든 로그 필터를 넣는다.
        filterRegistrationBean.setOrder(1); // 필터가 체인으로 들어갈 수 있으니 순서 설정
        filterRegistrationBean.addUrlPatterns("/*"); // 어떤 URL 패턴에 적용할 것인지. 여기서는 모든 경로 설정

        return filterRegistrationBean;
    }

//    @Bean // 인증 체크는 인터셉터를 활용한다.
    public FilterRegistrationBean loginCheckFilter() { // 메모리의 메소드 하나 호출하는 것에 대한 성능 이슈는 없다.
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter()); // 우리가 만든 로그 필터를 넣는다.
        filterRegistrationBean.setOrder(2); // 필터가 체인으로 들어갈 수 있으니 순서 설정
        filterRegistrationBean.addUrlPatterns("/*"); // 어떤 URL 패턴에 적용할 것인지. 여기서는 모든 경로 설정

        return filterRegistrationBean;
    }

}
