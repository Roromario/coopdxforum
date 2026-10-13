package com.coopdxforum.coopDXforum.service;

import com.coopdxforum.coopDXforum.converters.GameConverter;
import com.coopdxforum.coopDXforum.models.dto.GameDTO;
import com.coopdxforum.coopDXforum.models.entities.Game;
import com.coopdxforum.coopDXforum.models.entities.User;
import com.coopdxforum.coopDXforum.repositories.GameRepository;
import com.coopdxforum.coopDXforum.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public GameService(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public List<GameDTO> getAllGames() {
        return gameRepository.findAll().stream()
                .map(GameConverter::toDTO)
                .collect(Collectors.toList());
    }

    public GameDTO getGameById(Integer id) {
        return gameRepository.findById(id)
                .map(GameConverter::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
    }

    public List<GameDTO> getGamesByUser(Integer userId) {
        return gameRepository.findAllGamesByUserId(userId).stream()
                .map(GameConverter::toDTO)
                .collect(Collectors.toList());
    }

    public List<GameDTO> getParticipatingGames(Integer userId) {
        return gameRepository.findParticipatingGamesByUserId(userId, LocalDateTime.now()).stream()
                .map(GameConverter::toDTO)
                .collect(Collectors.toList());
    }

    public List<GameDTO> getPreviouslyParticipatedGames(Integer userId) {
        return gameRepository.findPreviouslyParticipatedGamesByUserId(userId, LocalDateTime.now()).stream()
                .map(GameConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public GameDTO createGame(GameDTO gameDTO) {
        Game game = new Game();
        applyGameDetails(game, gameDTO);
        return GameConverter.toDTO(gameRepository.save(game));
    }

    @Transactional
    public GameDTO updateGame(Integer id, GameDTO gameDetails) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        applyGameDetails(game, gameDetails);
        return GameConverter.toDTO(gameRepository.save(game));
    }

    @Transactional
    public void deleteGame(Integer id) {
        if (!gameRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        gameRepository.deleteById(id);
    }

    private void applyGameDetails(Game game, GameDTO gameDetails) {
        game.setHostUser(findHostUser(gameDetails.getHostUserId()));
        game.setType(gameDetails.getType());
        game.setCode(gameDetails.getCode());
        game.setDateStartGame(gameDetails.getDateStartGame());
        game.setDateEndGame(gameDetails.getDateEndGame());
    }

    private User findHostUser(Integer userId) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "hostUserId is required");
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Host user not found"));
    }
}
