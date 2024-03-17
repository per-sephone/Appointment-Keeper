package edu.pdx.cs410J.nob;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Implements the {@link AppointmentBookDumper} interface.
 * Dumps the contents of an appointment book into a text file.
 */
public class TextDumper implements AppointmentBookDumper<AppointmentBook> {
    /**
     * We will pass in a {@link FileWriter} here with an associated file path.
     */
    final Writer writer;

    /**
     * Constructor that assigns the {@link FileWriter} the object.
     * @param writer the Writer that will write to file.
     */
    public TextDumper(Writer writer) {
        this.writer = writer;
    }

    /**
     * Dumps an {@link AppointmentBook} to a text file.
     * @param book The {@link AppointmentBook} to be dumped to text.
     */
    @Override
    public void dump(AppointmentBook book) {
        //https://www.baeldung.com/java-write-to-file

        String divider = "-----";

        try (
                PrintWriter pw = new PrintWriter(this.writer)
        ) {
            pw.println(book.getOwnerName());
            pw.flush();

            Collection<Appointment> appts = new ArrayList<>(book.getAppointments());

            appts.forEach(element -> {
                pw.println(divider);
                pw.println(element.toString());
                pw.flush();
            });

        }
    }
}
