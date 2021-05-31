package dev.nhaiden.lab14_bsp2.database;

import dev.nhaiden.lab14_bsp2.model.Post;
import dev.nhaiden.lab14_bsp2.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Override
    Optional<User> findById(Integer integer);

    @Override
    <S extends User> S save(S s);

    @Override
    List<User> findAll();

    @Override
    void delete(User user);
}
