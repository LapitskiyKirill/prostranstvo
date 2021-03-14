package coursework.prostranstvo.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditLotDto {
    private String name;
    private String description;
    private Double price;
    private List<Long> deletedPictures;
}
