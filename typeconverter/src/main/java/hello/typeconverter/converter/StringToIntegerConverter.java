package hello.typeconverter.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

// 문자를 숫자로 바꾸는 타입 컨버터
@Slf4j
public class StringToIntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String source) {
        log.info("converter source={}", source);
        Integer integer = Integer.valueOf(source);
        return integer;
    }
}
