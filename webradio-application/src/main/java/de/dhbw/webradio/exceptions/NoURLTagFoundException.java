package de.dhbw.webradio.exceptions;

import de.dhbw.webradio.logger.Logger;

import java.io.File;

public class NoURLTagFoundException extends Exception {
    public NoURLTagFoundException(String fileContent) {
        super();
        Logger.logError("No url tag was found in file: ");
        Logger.logInfo(fileContent);
    }
}
