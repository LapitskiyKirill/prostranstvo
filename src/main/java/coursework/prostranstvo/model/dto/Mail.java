package coursework.prostranstvo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Mail {
    private String subject;
    private String recipient;
    private String body;
}
