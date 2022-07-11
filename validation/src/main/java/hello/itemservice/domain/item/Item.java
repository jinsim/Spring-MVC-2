package hello.itemservice.domain.item;

import lombok.Data;
// hibernate 구현체에만 동작됨.
import org.hibernate.validator.constraints.Range;
// BeanValidation이 표준적으로 제공함. 어떤 구현체에서든 동작함.
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Item {

    private Long id;

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    @Max(9999)
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
