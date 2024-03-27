package superapp.data;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserCrud extends MongoRepository<UserEntity, UserIdEntity> {
    @ExistsQuery(value = "{'_id.email': {$regex : /^?1\\z/ , $options: 'i'}}")
    boolean existsByUserId_SuperappAndUserId_Email(
            @Param("_id.superapp") String superapp,
            @Param("_id.email") String email);

    @Query(value = "{'_id.email': {$regex : /^?1\\z/ , $options: 'i'}}")
    Optional<UserEntity> findByUserId_SuperappAndUserId_Email(
            @Param("_id.superapp") String superapp,
            @Param("_id.email") String email);
}
