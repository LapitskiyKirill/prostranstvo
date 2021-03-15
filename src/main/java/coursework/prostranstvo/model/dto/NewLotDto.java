package coursework.prostranstvo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewLotDto {
    private String name;
    private String description;
    private Double price;
    private List<NewLotPictureDto> picturesList;
    private Long userId;
}
