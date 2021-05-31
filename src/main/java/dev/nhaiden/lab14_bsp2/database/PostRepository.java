package dev.nhaiden.lab14_bsp2.database;

import dev.nhaiden.lab14_bsp2.model.Post;
import dev.nhaiden.lab14_bsp2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Override
    Optional<Post> findById(Integer integer);

    @Override
    List<Post> findAll();

    @Override
    <S extends Post> S save(S s);

    @Override
    void delete(Post post);

    List<Post> findPostsByUser(User user);
}
