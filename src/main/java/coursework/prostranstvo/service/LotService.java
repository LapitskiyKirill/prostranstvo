package coursework.prostranstvo.service;

import coursework.prostranstvo.model.dto.*;
import coursework.prostranstvo.model.entity.Lot;
import coursework.prostranstvo.model.entity.LotPicture;
import coursework.prostranstvo.model.entity.User;
import coursework.prostranstvo.model.exception.NoSuchEntityException;
import coursework.prostranstvo.repository.LotPictureRepository;
import coursework.prostranstvo.repository.LotRepository;
import coursework.prostranstvo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LotService {
    private final LotRepository lotRepository;
    private final LotPictureRepository lotPictureRepository;
    private final UserRepository userRepository;
    private final PictureCompressingService pictureCompressingService;

    public void create(NewLotDto newLotDto, List<MultipartFile> images) {
        Lot lot = Lot.builder()
                .name(newLotDto.getName())
                .description(newLotDto.getDescription())
                .price(newLotDto.getPrice())
                .creationDate(LocalDateTime.now())
                .active(true)
                .pictureList(null)
                .user(userRepository.findById(newLotDto.getUserId()).orElseThrow(() -> new NoSuchEntityException("User does not exist.")))
                .build();

        lotRepository.save(lot);
        images.forEach(m -> {
            try {
                lotPictureRepository.save(
                        LotPicture.builder()
                                .image(pictureCompressingService.compressBytes(m.getBytes()))
                                .lot(lot).build()
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void close(Long id) {
        Lot lot = lotRepository.findById(id).orElseThrow(() -> new NoSuchEntityException("Lot does not exist."));
        lot.setActive(false);
        lotRepository.save(lot);
    }

    public void edit(Long id, EditLotDto editLotDto, List<MultipartFile> images) {
        Lot lot = lotRepository.findById(id).orElseThrow(() -> new NoSuchEntityException("Lot does not exist."));
        lot.setDescription(editLotDto.getDescription());
        lot.setName(editLotDto.getName());
        lot.setPrice(editLotDto.getPrice());
        lotRepository.save(lot);
        if (images != null)
            images.forEach(m -> {
                try {
                    lotPictureRepository.save(
                            LotPicture.builder()
                                    .image(pictureCompressingService.compressBytes(m.getBytes()))
                                    .lot(lot).build()
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        if (editLotDto.getDeletedPictures() != null) {
            lotPictureRepository.deleteAllByIds(editLotDto.getDeletedPictures());
        }
    }

    public List<LotDto> getAll(Filter filter, Pageable pageable) throws Exception {
        List<Lot> lots = lotRepository.findAllByPrice(
                filter.getHighestPrice(),
                filter.getLowestPrice(),
                filter.getOrderByPrice().orElseThrow(() -> new Exception("Sorting order is not specified.")),
                pageable
        );
        return lots.stream().map(this::lotMapping).collect(Collectors.toList());
    }

    public List<LotDto> getAllByUser(Long userId, coursework.prostranstvo.model.dto.Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchEntityException("User does not exist."));
        List<Lot> lots = lotRepository.findByUser(user,
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize()
                ));
        return lots.stream().map(this::lotMapping).collect(Collectors.toList());
    }

    public LotDto getById(Long lotId) {
        Lot lot = lotRepository.findById(lotId).orElseThrow(() -> new NoSuchEntityException("No lot with such id"));
        return lotMapping(lot);
    }

    public LotDto lotMapping(Lot lot) {
        return LotDto.builder()
                .id(lot.getId())
                .name(lot.getName())
                .description(lot.getDescription())
                .price(lot.getPrice())
                .creationDate(lot.getCreationDate())
                .active(lot.isActive())
                .picturesList(lot.getPictureList().stream()
                        .map(lotPicture -> LotPictureDto.builder()
                                .id(lotPicture.getId())
                                .image(pictureCompressingService.decompressBytes(lotPicture.getImage()))
                                .build())
                        .collect(Collectors.toList()))
                .userDto(
                        new UserDto(lot.getUser().getId(),
                                lot.getUser().getMail(),
                                lot.getUser().getPhoneNumber(),
                                lot.getUser().getFirstname(),
                                lot.getUser().getLastname()
                        ))
                .build();
    }
}