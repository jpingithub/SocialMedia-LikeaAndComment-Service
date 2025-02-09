package com.rb.likeandcomment.repositoty;

import com.rb.likeandcomment.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like,Integer> {
    Optional<Like> findByUserIdAndPostId(String userId,Integer postId);
    List<Like> findByPostId(Integer postId);
}
