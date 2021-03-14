package coursework.prostranstvo.Controllers;

import coursework.prostranstvo.Model.DTO.EditLotDto;
import coursework.prostranstvo.Model.DTO.LotDto;
import coursework.prostranstvo.Model.DTO.NewLotDto;
import coursework.prostranstvo.Model.DTO.RequestLots;
import coursework.prostranstvo.Service.LotService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lot")
public class LotController {
    private final LotService lotService;

    @PostMapping("/create")
    public ResponseEntity<String> create(
            @RequestParam("name") String name,
            @RequestParam("files") List<MultipartFile> images,
            @RequestParam("description") String description,
            @RequestParam("price") String price,
            @RequestParam("userId") Long userId
    ) {
        lotService.create(new NewLotDto(name, description, Double.parseDouble(price), null, userId), images);
        return new ResponseEntity<>("Lot successfully auctioned.", HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/close")
    public ResponseEntity<String> close(@RequestParam("id") Long id) {
        lotService.close(id);
        return new ResponseEntity<>("Lot successfully closed.", HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/edit")
    public ResponseEntity<String> edit(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "files", required = false) List<MultipartFile> images,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "price", required = false) String price,
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "deletedPictures", required = false) List<Long> deletedPictures
    ) {
        lotService.edit(
                id,
                new EditLotDto(
                        name,
                        description,
                        Double.parseDouble(price),
                        deletedPictures
                ),
                images);
        return new ResponseEntity<>("Lot successfully closed.", HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/getAll")
    public List<LotDto> getAll(@RequestBody RequestLots requestLots) throws Exception {
        return lotService.getAll(
                requestLots.getFilter(),
                PageRequest.of(
                        requestLots.getPageable().getPageNumber(),
                        requestLots.getPageable().getPageSize()
                )
        );
    }

    @Transactional
    @PostMapping("/getById")
    public LotDto getById(@RequestParam("lotId") Long lotId) {
        return lotService.getById(lotId);
    }

    @Transactional
    @PostMapping("/getAllByUser")
    public List<LotDto> getAllByUser(@RequestParam("userId") Long userId, @RequestBody coursework.prostranstvo.Model.DTO.Pageable pageable) {
        return lotService.getAllByUser(userId, pageable);
    }
}
