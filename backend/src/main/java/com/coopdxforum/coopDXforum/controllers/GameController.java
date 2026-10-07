package com.coopdxforum.coopDXforum.controllers;

import com.coopdxforum.coopDXforum.converters.GameConverter;
import com.coopdxforum.coopDXforum.models.dto.GameDTO;
import com.coopdxforum.coopDXforum.models.entities.Game;
import com.coopdxforum.coopDXforum.models.entities.User;
import com.coopdxforum.coopDXforum.repositories.GameRepository;
import com.coopdxforum.coopDXforum.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public GameController(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<GameDTO> getAllGames() {
        return gameRepository.findAll().stream()
                .map(GameConverter::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable Long id) {
        return gameRepository.findById(id)
                .map(GameConverter::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public GameDTO createGame(@RequestBody GameDTO gameDTO) {
        Game game = new Game();
        game.setHostUser(findHostUser(gameDTO.getHostUserId()));
        game.setType(gameDTO.getType());
        game.setCode(gameDTO.getCode());
        game.setDateStartGame(gameDTO.getDateStartGame());
        game.setDateEndGame(gameDTO.getDateEndGame());
        return GameConverter.toDTO(gameRepository.save(game));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameDTO> updateGame(@PathVariable Long id, @RequestBody GameDTO gameDetails) {
        return gameRepository.findById(id)
                .map(game -> {
                    game.setHostUser(findHostUser(gameDetails.getHostUserId()));
                    game.setType(gameDetails.getType());
                    game.setCode(gameDetails.getCode());
                    game.setDateStartGame(gameDetails.getDateStartGame());
                    game.setDateEndGame(gameDetails.getDateEndGame());
                    return ResponseEntity.ok(GameConverter.toDTO(gameRepository.save(game)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private User findHostUser(Integer userId) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "hostUserId is required");
        }
        return userRepository.findById(userId.longValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Host user not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        if (gameRepository.existsById(id)) {
            gameRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}