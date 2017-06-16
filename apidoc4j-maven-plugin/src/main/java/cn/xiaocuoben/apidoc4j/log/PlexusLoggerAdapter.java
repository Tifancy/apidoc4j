package cn.xiaocuoben.apidoc4j.log;

import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.logging.AbstractLogger;
import org.codehaus.plexus.logging.Logger;

/**
 * @author 刘玉雨
 * @version 1.0
 */
public class PlexusLoggerAdapter extends AbstractLogger {
    private Log logger;

    public PlexusLoggerAdapter(Log logger) {
        super(0, "PlexusLoggerAdapter");
        this.logger = logger;
    }

    public void debug(String message, Throwable throwable) {
        if(throwable == null) {
            this.logger.debug(message);
        } else {
            this.logger.debug(message, throwable);
        }

    }

    public void info(String message, Throwable throwable) {
        if(throwable == null) {
            this.logger.info(message);
        } else {
            this.logger.info(message, throwable);
        }

    }

    public void warn(String message, Throwable throwable) {
        if(throwable == null) {
            this.logger.warn(message);
        } else {
            this.logger.warn(message, throwable);
        }

    }

    public void error(String message, Throwable throwable) {
        if(throwable == null) {
            this.logger.error(message);
        } else {
            this.logger.error(message, throwable);
        }

    }

    public void fatalError(String message, Throwable throwable) {
        if(throwable == null) {
            this.logger.error(message);
        } else {
            this.logger.error(message, throwable);
        }

    }

    public Logger getChildLogger(String name) {
        return null;
    }
}
