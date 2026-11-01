package team.frostfield.xtrafight_api.exception;

import team.frostfield.xtrafight_api.main.XtraFightAPIMod;
import team.frostfield.xtrafight_api.utils.EnvironmentHelper;

public class DeveloperException extends RuntimeException {

    public DeveloperException(String message) {
        super(message);
    }

    /**
     * Throws a DeveloperException only if running in a development environment.
     */
    public static void throwEx(String message) {
        if (EnvironmentHelper.getEnvironment().isDevEnv()) {
            throw new DeveloperException(message);
        }
    }

    /**
     * Throws both RuntimeException if the environment is production (not-dev), and DeveloperException if it was an IDE environment, accepts args for logging
     */
    public static void throwExBoth(String message, Object... args) {
        String loggedMessage = "";
        for (Object argument : args) {
            loggedMessage = message.replace("{}", argument.toString());
        }
        if (EnvironmentHelper.getEnvironment().isDevEnv()) {
            throw new DeveloperException(loggedMessage);
        } else {
            throw new RuntimeException(loggedMessage);
        }
    }


    public static void throwOrLog(String message, Object... args) {
        String loggedMessage = "";
        for (int i = 0; i < args.length; i++) {
            loggedMessage = message.replace("{}", args[i].toString());
        }
        if (EnvironmentHelper.getEnvironment().isDevEnv()) {
            throw new DeveloperException(loggedMessage);
        } else {
            XtraFightAPIMod.err(loggedMessage);
        }
    }

    /**
     * Throws both RuntimeException if the environment is production (not-dev), and DeveloperException if it was an IDE environment
     */
    public static void throwExBoth(String message) {
        if (EnvironmentHelper.getEnvironment().isDevEnv()) {
            throw new DeveloperException(message);
        } else {
            throw new RuntimeException(message);
        }
    }

    public static void throwExBoth(Exception e) {
        throwExBoth(e.getMessage());
    }
}