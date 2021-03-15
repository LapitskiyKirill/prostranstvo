package coursework.prostranstvo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filter {
    private Double highestPrice;
    private Double lowestPrice;
    //true - ascending, false - descending
    private Optional<Boolean> orderByPrice;
}
