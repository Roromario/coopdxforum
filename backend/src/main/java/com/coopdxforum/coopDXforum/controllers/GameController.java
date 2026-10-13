package com.coopdxforum.coopDXforum.controllers;

import com.coopdxforum.coopDXforum.models.dto.GameDTO;
import com.coopdxforum.coopDXforum.models.dto.UserGameDTO;
import com.coopdxforum.coopDXforum.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<GameDTO> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/user/{userId}")
    public List<UserGameDTO> getGamesByUser(@PathVariable Integer userId) {
        return gameService.getGamesByUser(userId);
    }

    @GetMapping("/{id}/code")
    public ResponseEntity<String> getGameCode(@PathVariable Integer id) {
        return ResponseEntity.ok(gameService.getGameCode(id));
    }

    @GetMapping("/user/{userId}/participating")
    public List<GameDTO> getParticipatingGames(@PathVariable Integer userId) {
        return gameService.getParticipatingGames(userId);
    }

    @GetMapping("/user/{userId}/participated")
    public List<GameDTO> getPreviouslyParticipatedGames(@PathVariable Integer userId) {
        return gameService.getPreviouslyParticipatedGames(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable Integer id) {
        return ResponseEntity.ok(gameService.getGameById(id));
    }

    @PostMapping
    public GameDTO createGame(@RequestBody GameDTO gameDTO) {
        return gameService.createGame(gameDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameDTO> updateGame(@PathVariable Integer id, @RequestBody GameDTO gameDetails) {
        return ResponseEntity.ok(gameService.updateGame(id, gameDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Integer id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
}