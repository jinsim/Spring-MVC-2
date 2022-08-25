package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.assertj.core.api.Assertions.assertThat;

public class ConversionServiceTest {

    @Test
    void conversionService() {
        // 등록
        // DefailtConversionService는 ConversionService 인터페이스를 구현한 구현체
        DefaultConversionService conversionService = new DefaultConversionService();
        // 컨버터들을 등록할 수 있다
        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());

        // 사용
        // conversionService가 적절한 컨버터를 찾아서 컨버팅해준다.
        assertThat(conversionService.convert("10", Integer.class)).isEqualTo(10);
        assertThat(conversionService.convert(10, String.class)).isEqualTo("10");

        IpPort ipPort = conversionService.convert("127.0.0.1:8000", IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8000));

        String ipPortString = conversionService.convert(new IpPort("127.0.0.1", 8080), String.class);
        assertThat(ipPortString).isEqualTo("127.0.0.1:8080");
    }
}
