package com.coopdxforum.coopDXforum.service;

import com.coopdxforum.coopDXforum.converters.GameConverter;
import com.coopdxforum.coopDXforum.models.dto.GameDTO;
import com.coopdxforum.coopDXforum.models.dto.UserGameDTO;
import com.coopdxforum.coopDXforum.models.entities.Game;
import com.coopdxforum.coopDXforum.models.entities.Invitation;
import com.coopdxforum.coopDXforum.models.entities.User;
import com.coopdxforum.coopDXforum.repositories.GameRepository;
import com.coopdxforum.coopDXforum.repositories.InvitationRepository;
import com.coopdxforum.coopDXforum.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GameService {

    private final GameRepository gameRepository;
    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;

    public GameService(
            GameRepository gameRepository,
            InvitationRepository invitationRepository,
            UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.invitationRepository = invitationRepository;
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

    public String getGameCode(Integer id) {
        String code = gameRepository.findCodeById(id);
        if (code == null && !gameRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        return code;
    }

    public List<UserGameDTO> getGamesByUser(Integer userId) {
        List<Game> games = gameRepository.findAllGamesByUserId(userId);
        if (games.isEmpty()) {
            return List.of();
        }

        Map<Integer, Boolean> joinedByGameId = invitationRepository
                .findByUser_IdAndEvent_Game_IdIn(
                        userId,
                        games.stream().map(Game::getId).collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(
                        invitation -> invitation.getEvent().getGame().getId(),
                        Invitation::isJoined,
                        Boolean::logicalOr));

        return games.stream()
                .map(game -> {
                    UserGameDTO dto = new UserGameDTO();
                    dto.setGame(GameConverter.toDTO(game));
                    dto.setJoined(
                            game.getHostUser().getId().equals(userId)
                                    || joinedByGameId.getOrDefault(game.getId(), false));
                    return dto;
                })
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
