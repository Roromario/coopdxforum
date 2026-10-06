package com.coopdxforum.coopDXforum.controllers;

import com.coopdxforum.coopDXforum.models.entities.Game;
import com.coopdxforum.coopDXforum.repositories.GameRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameRepository gameRepository;

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        return gameRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Game createGame(@RequestBody Game game) {
        return gameRepository.save(game);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @RequestBody Game gameDetails) {
        return gameRepository.findById(id)
                .map(game -> {
                    game.setHostUser(gameDetails.getHostUser());
                    game.setType(gameDetails.getType());
                    game.setCode(gameDetails.getCode());
                    game.setDateStartGame(gameDetails.getDateStartGame());
                    game.setDateEndGame(gameDetails.getDateEndGame());
                    return ResponseEntity.ok(gameRepository.save(game));
                })
                .orElse(ResponseEntity.notFound().build());
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