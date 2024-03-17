package edu.pdx.cs410J.nob;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Implements the {@link AppointmentBookParser} interface.
 * Parses the contents of a text file into an AppointmentBook.
 */
public class TextParser implements AppointmentBookParser<AppointmentBook> {
    /**
     * We will pass in a {@link FileReader} here with an associated file path.
     */
    private final Reader reader;

    /**
     * Constructor that assigns the {@link FileReader} the object.
     * @param reader the Reader that will read the file.
     */
    public TextParser(Reader reader) {
        this.reader = reader;
    }

    /**
     * Parses a text file into an {@link AppointmentBook}.
     * Returns an {@link AppointmentBook} parsed from the text file.
     */
    @Override
    public AppointmentBook parse() throws ParserException {
        try (
                BufferedReader br = new BufferedReader(this.reader)
        ) {
            String owner = br.readLine();
            if (owner == null) {
                throw new ParserException("Missing owner");
            }

            AppointmentBook aptBook = new AppointmentBook(owner);

            while(br.readLine() != null) { //separator
                String line = br.readLine(); //appointment
                if (line == null) {
                    throw new ParserException("File is malformatted.");
                }
                String[] tokens = fromString(line);
                if(tokens.length != 3) {
                    throw new ParserException("File is malformatted.");
                } else {
                    aptBook.addAppointment(new Appointment(owner, tokens[0].trim(),
                            stringToZonedDateTime(tokens[1]), stringToZonedDateTime(tokens[2])));
                }
            }
            return aptBook;

        } catch (IOException e) {
            throw new ParserException("Error occurred while parsing appointment book file", e);
        }
    }

    /**
     * Takes an appointment string {@link Appointment#toString()}
     * and turns it into tokens that can be parsed into an {@link Appointment} object.
     * @param string An Appointment string (from the toString() method).
     * @return tokens that can be used in an Appointment object.
     */
    public String[] fromString(String string) {
        //https://stackoverflow.com/questions/5993779/use-string-split-with-multiple-delimiters
        String[] tokens = string.split("from|until");
        String[] trimmedTokens = tokens.clone();
        for (int i = 0; i < tokens.length; i++) {
            trimmedTokens[i] = tokens[i].trim();
        }
        return trimmedTokens;
    }

    /**
     * Turns a string into a {@link ZonedDateTime}
     * @param time A datetime string
     * @return A ZonedDateTime object
     */
    @VisibleForTesting
    static ZonedDateTime stringToZonedDateTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/uu, h:mm a z").withResolverStyle(ResolverStyle.LENIENT);
        ZonedDateTime zonedDateTime;
        try {
            zonedDateTime = ZonedDateTime.parse(time, formatter);
        } catch (DateTimeParseException d) {
            System.err.println("Cannot parse datetime string");
            return null;
        }
        return zonedDateTime;
    }
}
