package coursework.prostranstvo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestLots {
    private Pageable pageable;
    private Filter filter;
}
