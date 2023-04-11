package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.repository;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.entity.PlayEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
@Repository
public interface PlaysRepository extends JpaRepository<PlayEntity, Integer> {
    //region METHODS
    List<PlayEntity> findAllByUserId(int userId);

    void  deleteAllByUserId(int userId);

    //endregion METHODS

}

