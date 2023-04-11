package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.repository;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.Documents.PlayDocument;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaysRepositoryMongo extends MongoRepository<PlayDocument, String>{
    //region METHODS
    List<PlayDocument> findAllByUserId(int userId);

    void deleteAllByUserId(int userId);

    //endregion METHODS

}
