package cz.maku.gameapi.game;

import cz.maku.gameapi.configuration.GameConfiguration;
import cz.maku.mommons.worker.annotation.PostInitialize;
import cz.maku.mommons.worker.annotation.Service;
import lombok.Getter;
import lombok.Setter;

@Service
public class GameService {

    @Setter
    @Getter
    private GameConfiguration gameConfiguration;

    @Getter
    private GameState gameState;

    @PostInitialize
    private void initGame() {
        gameState = GameState.WAITING;
    }

    public boolean isState(GameState gameState) {
        return gameState.equals(this.gameState);
    }

    public boolean isGameTeamless() {
        return gameConfiguration.getMaxTeams() == 0;
    }
}
