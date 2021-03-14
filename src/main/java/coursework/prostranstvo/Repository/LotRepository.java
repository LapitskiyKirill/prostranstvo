package coursework.prostranstvo.Repository;

import coursework.prostranstvo.Model.Entity.Lot;
import coursework.prostranstvo.Model.Entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {

    @Query(value = "SELECT lot FROM Lot lot where " +
            "(lot.active = true)" +
            "and (:highestPrice is null or lot.price < :highestPrice) " +
            "and (:lowestPrice is null or lot.price > :lowestPrice) " +
            "order by case when :orderByPrice = true then lot.price else -lot.price end asc")
    List<Lot> findAllByPrice(
            @Param("highestPrice") Double highestPrice,
            @Param("lowestPrice") Double lowestPrice,
            @Param("orderByPrice") Boolean orderByPrice,
            Pageable pageable);

    List<Lot> findByUser(User user, Pageable pageable);
}