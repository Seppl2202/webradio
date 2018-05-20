package de.dhbw.webradio.exceptions;

import java.io.File;

public class NoURLTagFoundException extends Exception {
    public NoURLTagFoundException(String fileContent) {
        super();
        System.err.println("No url tag was found in file: ");
        System.err.println(fileContent);
    }
}
