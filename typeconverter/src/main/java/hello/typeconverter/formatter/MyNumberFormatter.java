package hello.typeconverter.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Slf4j
public class MyNumberFormatter implements Formatter<Number> {
    // String으로 변환되는 것이므로, String은 제외하고 타입을 적으면 된다. Number는 Integer, Long 등의 부모 클래스이다.

    @Override
    public Number parse(String text, Locale locale) throws ParseException {
        // "1,000" -> 1000
        log.info("text={}, locale={}", text, locale);
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.parse(text);
    }

    @Override
    public String print(Number object, Locale locale) {
        // 1000 -> "1,000"
        log.info("object={}, locale={}", object, locale);
        return NumberFormat.getInstance(locale).format(object);
    }
}
