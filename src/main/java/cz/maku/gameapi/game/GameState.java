package cz.maku.gameapi.game;

public enum GameState {
    WAITING("§eWaiting for players..."),
    STARTING("§eStarting..."),
    RUNNING("§cRunning...");

    public final String displayName;

    GameState(String displayName) {
        this.displayName = displayName;
    }
}
