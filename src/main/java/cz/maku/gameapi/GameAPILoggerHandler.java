package cz.maku.gameapi;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class GameAPILoggerHandler extends Handler {
    @Override
    public void publish(LogRecord record) {
        record.setLoggerName("GameAPI");
    }

    @Override
    public void flush() {
          System.out.flush();
    }

    @Override
    public void close() throws SecurityException {
        System.out.close();
    }
}
