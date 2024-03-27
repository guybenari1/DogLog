package superapp.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SuperAppObjectCrud extends MongoRepository<SuperAppObjectEntity, SuperAppObjectIdEntity> {
    List<SuperAppObjectEntity> findAllByActiveIsTrue(Pageable pageable);

    Optional<SuperAppObjectEntity> findByObjectIdAndActiveIsTrue(
            @Param("_id") SuperAppObjectIdEntity objectId);

    List<SuperAppObjectEntity> findAllByObjectIdAndActiveIsTrue(
            @Param("_id") SuperAppObjectIdEntity objectId,
            Pageable pageable);

    List<SuperAppObjectEntity> findAllByType(
            @Param("type") String type,
            Pageable pageable);

    List<SuperAppObjectEntity> findAllByTypeAndActiveIsTrue(
            @Param("type") String type,
            Pageable pageable);

    List<SuperAppObjectEntity> findAllByAlias(
            @Param("alias") String alias,
            Pageable pageable);

    List<SuperAppObjectEntity> findAllByAliasAndActiveIsTrue(
            @Param("alias") String alias,
            Pageable pageable);

//    List<SuperAppObjectEntity> findAllByLocation_LatBetweenAndLocation_LngBetween(
//            @Param("minLat") float minLat,
//            @Param("maxLat") float maxLat,
//            @Param("minLng") float minLng,
//            @Param("maxLng") float maxLng,
//            Pageable pageable);
//
//    List<SuperAppObjectEntity> findAllByLocation_LatBetweenAndLocation_LngBetweenAndActiveIsTrue(
//            @Param("minLat") float minLat,
//            @Param("maxLat") float maxLat,
//            @Param("minLng") float minLng,
//            @Param("maxLng") float maxLng,
//            Pageable pageable);

    List<SuperAppObjectEntity> findAllByLocationNear(
            @Param("location") Point location,
            @Param("maxDistance") Distance maxDistance,
            Pageable pageable);

    List<SuperAppObjectEntity> findAllByLocationNearAndActiveIsTrue(
            @Param("location") Point location,
            @Param("maxDistance") Distance maxDistance,
            Pageable pageable);
}