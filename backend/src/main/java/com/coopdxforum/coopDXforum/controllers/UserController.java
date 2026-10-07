package com.coopdxforum.coopDXforum.controllers;

import com.coopdxforum.coopDXforum.converters.UserConverter;
import com.coopdxforum.coopDXforum.models.dto.UserDTO;
import com.coopdxforum.coopDXforum.models.entities.User;
import com.coopdxforum.coopDXforum.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET ALL : SELECT * FROM "Users"
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserConverter::toDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        return userRepository.findById(id)
                .map(UserConverter::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST : INSERT
    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setNickname(userDTO.getNickname());
        user.setGlobalName(userDTO.getGlobalName());
        user.setPp(userDTO.getPp());
        user.setLastConnection(userDTO.getLastConnection());
        user.setMailAddress(userDTO.getMailAddress());
        return UserConverter.toDTO(userRepository.save(user));
    }

    // PUT : UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setNickname(userDetails.getNickname());
                    user.setGlobalName(userDetails.getGlobalName());
                    user.setPp(userDetails.getPp());
                    user.setLastConnection(userDetails.getLastConnection());
                    user.setMailAddress(userDetails.getMailAddress());
                    return ResponseEntity.ok(UserConverter.toDTO(userRepository.save(user)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE : REMOVE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}