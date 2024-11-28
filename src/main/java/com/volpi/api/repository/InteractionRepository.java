package com.volpi.api.repository;

import com.volpi.api.model.Interaction;
import com.volpi.api.model.enums.InteractionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long>{
    @Query("""
            SELECT
            CASE WHEN COUNT(CASE WHEN i.type = 'SAVED' AND i.isInteractionEnabled = true AND i.user.id = :userId THEN 1 END) > 0 THEN TRUE ELSE FALSE END AS isSaved,
            CASE WHEN COUNT(CASE WHEN i.type = 'SUPPORTED' AND i.isInteractionEnabled = true AND i.user.id = :userId THEN 1 END) > 0 THEN TRUE ELSE FALSE END AS isSupported,
            SUM(CASE WHEN i.type = 'SAVED' AND i.isInteractionEnabled = true THEN 1 ELSE 0 END) AS saveCount,
            SUM(CASE WHEN i.type = 'SUPPORTED' AND i.isInteractionEnabled = true THEN 1 ELSE 0 END) AS supportCount
            FROM Interaction i
            WHERE i.post.id = :postId
       """)
    Object[] findPostInteractionDetails(@Param("userId") Long userId, @Param("postId") Long postId);

    Optional<Interaction> findByUserIdAndPostIdAndType(Long userId, Long postId, InteractionType type);
}
