package com.volpi.api.repository;

import com.volpi.api.dto.InteractionDetailsInterface;
import com.volpi.api.model.Interaction;
import com.volpi.api.model.Post;
import com.volpi.api.model.enums.InteractionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long>{

    @Query("""
            SELECT
                CASE
                    WHEN COALESCE(SUM(CASE WHEN i.type = 'SAVE' AND i.user.id = :userId THEN 1 ELSE 0 END), 0) > 0
                    THEN TRUE
                    ELSE FALSE
                END AS isSaved,
                CASE
                    WHEN COALESCE(SUM(CASE WHEN i.type = 'SUPPORT' AND i.user.id = :userId THEN 1 ELSE 0 END), 0) > 0
                    THEN TRUE
                    ELSE FALSE
                END AS isSupported,
                COALESCE(SUM(CASE WHEN i.type = 'SAVE' THEN 1 ELSE 0 END), 0) AS saveCount,
                COALESCE(SUM(CASE WHEN i.type = 'SUPPORT' THEN 1 ELSE 0 END), 0) AS supportCount
            FROM Interaction i
            WHERE i.post.id = :postId
       """)
    InteractionDetailsInterface findPostInteractionDetails(@Param("userId") Long userId, @Param("postId") Long postId);

    Optional<Interaction> findByUserIdAndPostIdAndType(Long userId, Long postId, InteractionType type);

    @Query("""
        SELECT i.post 
        FROM Interaction i 
        WHERE i.user.id = :userId AND i.type = 'SAVE'
    """)
    List<Post> findSavedPostsByUserId(@Param("userId") Long userId);

    List<Interaction> findByPostId(Long postId);
}
