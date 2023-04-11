package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.repository;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Class Repository for user to management SQL DDBB.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    //region METHODS
    UserEntity findByUserName(String nameIn);
    Boolean existsByUserName(String userNameIn);

    //endregion METHODS

}
