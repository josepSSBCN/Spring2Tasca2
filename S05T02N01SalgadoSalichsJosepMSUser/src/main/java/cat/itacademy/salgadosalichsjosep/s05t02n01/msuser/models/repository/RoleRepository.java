package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.repository;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.entity.RoleEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    //region METHODS
    Optional<RoleEntity> findByName(String name);

    //endregion METHODS

}
