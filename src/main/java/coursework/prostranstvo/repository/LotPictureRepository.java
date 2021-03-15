package coursework.prostranstvo.repository;

import coursework.prostranstvo.model.entity.LotPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotPictureRepository extends JpaRepository<LotPicture, Long> {
    @Modifying
    @Query(value = "delete from LotPicture l where l.id in :ids")
    void deleteAllByIds(@Param("ids") List<Long> ids);
}
