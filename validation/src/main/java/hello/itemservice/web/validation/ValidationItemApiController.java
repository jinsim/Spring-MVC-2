package hello.itemservice.web.validation;

import hello.itemservice.web.validation.form.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController // 모든 메소드에 @ResponseBody가 붙게 된다.
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {

    @PostMapping("/add")
    public Object addItem(@RequestBody @Validated ItemSaveForm form, BindingResult bindingResult) {
        // form이 JSON 형식으로 Body에 들어온 상황

        log.info("API 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors={}", bindingResult);
            return bindingResult.getAllErrors(); // FieldError와 ObjectError를 다 반환해준다.
            //@ResponseBody 가 붙어있으므로, 리스트를 JSON 객체로 반환된다.
        }

        log.info("성공 로직 실행");
        return form;
    }
}
