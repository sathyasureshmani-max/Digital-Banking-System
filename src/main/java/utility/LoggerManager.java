package utility;

import java.util.logging.*;

public final class LoggerManager {
    private static final Logger LOGGER = Logger.getLogger("DigitalBankingSystem");
    private LoggerManager() {}
    public static Logger getLogger() { return LOGGER; }
    public static void warning(String message) { LOGGER.warning(message); }
}
