package hello.login.web.argumentresolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 애너테이션 문법상 넣어야 한다. 파라미터에만 사용한다.
@Retention(RetentionPolicy.RUNTIME) // 실제 동작할 때까지 애너테이션이 남아있어야 하므로.
public @interface Login {

}
