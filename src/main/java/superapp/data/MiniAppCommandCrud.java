package superapp.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MiniAppCommandCrud extends MongoRepository<MiniAppCommandEntity, CommandIdEntity> {
    @Query(value = "{'_id.miniapp': {$regex : /^?0\\z/ , $options: 'i'}}")
    List<MiniAppCommandEntity> findAllByCommandId_Miniapp(
            @Param("_id.miniapp") String miniapp,
            Pageable pageable);
}