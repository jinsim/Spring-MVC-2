package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component // 스프링 빈에 등록이 된다.
// 스프링이 제공하는 Validator를 사용하자.
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        // 파라미터로 넘어오는 클래스가 Item에 지원이 되느냐
        // clazz가 Item또는 그 자식 클래스
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target; // 인터페이스가 Object로 박혀있으므로, Item으로 캐스팅한다.

        // Errors는 BindingResult의 부모 클래스이다. errors에 BindingResult를 넣어줄 수 있다.
        if (!StringUtils.hasText(item.getItemName())) {
            errors.rejectValue("itemName", "required");
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.rejectValue("price", "range", new Object[]{1000, 10000000}, null);
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
    }
}
