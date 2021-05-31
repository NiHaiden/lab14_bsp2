package dev.nhaiden.lab14_bsp2.rest;

import dev.nhaiden.lab14_bsp2.database.PostRepository;
import dev.nhaiden.lab14_bsp2.database.UserRepository;
import dev.nhaiden.lab14_bsp2.exception.*;
import dev.nhaiden.lab14_bsp2.model.Post;
import dev.nhaiden.lab14_bsp2.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.System.*;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public RestController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        if (!Objects.isNull(user.getId())) {
            throw new IdNotNullException("ID of the new User must be null!");
        }

        try {
            User savedUser = userRepository.save(user);
            String path = "/users";

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath(path).build(savedUser.getId());
            return ResponseEntity.created(uri).body(savedUser);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getMessage().contains("ConstraintViolationException")) {
                throw new UserAlreadyExistsException("The user" + user.getName() + " already exists!");
            }
            throw ex;
        }
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Integer id) {
        if (Objects.isNull(id)) {
            throw new IdIsNullException("The user id can't be null!");
        }

        Optional<User> userOpt = userRepository.findById(id);


        if (userOpt.isPresent()) {
            return userOpt.get();
        }
        throw new UserNotFoundException("No user with the id " + id + " was found in the database!");

    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        if (id == null) {
            throw new IdIsNullException("The user id can't be null!");
        }

        Optional<User> userOpt = userRepository.findById(id);

        if(userOpt.isEmpty()) {
            throw new UserNotFoundException("No user with the id " + id + " was found in the database!");
        }
        userRepository.delete(userOpt.get());
    }


    @GetMapping("/users/{id}/posts")
    public List<Post> getPostsByUser(@PathVariable Integer id) {
        if (id == null) {
            throw new IdIsNullException("The user id can't be null!");
        }

        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) {
            throw new UserNotFoundException("No user with the id " + id + " was found in the database!");
        }

        return postRepository.findPostsByUser(user.get());
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> savePost(@RequestBody Post post, @PathVariable Integer id) {
        if (Objects.isNull(id)) {
            throw new IdIsNullException("The user id can't be null!");
        }

        if (!(Objects.isNull(post.getId()))) {
            throw new IdIsNullException("The post id must be null!");
        }

        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("No user with the id " + id + " was found in the database!");
        }

        try {

            post.setUser(userOpt.get());
            Post savedPost = postRepository.save(post);

            String path = "/users/{id}/posts";
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath(path).build(savedPost.getId());
            return ResponseEntity.created(uri).body(savedPost);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getMessage().contains("ConstraintViolationException")) {
                throw new UserAlreadyExistsException("The user" + post.getMessage() + " already exists!");
            }
            throw ex;
        }
    }

    @GetMapping("/posts/{id}")
    public Post getPostById(@PathVariable Integer id) {
        if (Objects.isNull(id)) {
            throw new IdIsNullException("The user id can't be null!");
        }

        Optional<Post> post = postRepository.findById(id);

        if (post.isEmpty()) {
            throw new PostNotFoundException("Post with id " + id + " was not found!");
        }

        return post.get();
    }
}
