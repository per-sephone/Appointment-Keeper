package edu.pdx.cs410J.nob;

import edu.pdx.cs410J.AbstractAppointment;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Contains data and methods for a single appointment.
 * @author Nora Luna
 */
public class Appointment extends AbstractAppointment implements Comparable<Appointment> {
    /**
     * DateTimeFormatter used for printing the ZonedDateTime string.
     */
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/uu, h:mm a z");
    /**
     * the person the appointment belongs to
     */
    private final String owner;
    /**
     * the description of the appointment
     */
    private String description;
    /**
     * the start date and time
     */
    private ZonedDateTime begin;
    /**
     * the end date and time
     */
    private ZonedDateTime end;

    /**
     * Constructs a new appointment and assigns the object's data with the following parameters:
     *
     * @param owner       the person the appointment belongs to
     * @param description the description of the appointment
     * @param begin       the start date and time
     * @param end         and end date and time
     */
    public Appointment(String owner, String description, ZonedDateTime begin, ZonedDateTime end) {
        this.owner = owner;
        this.description = description;
        this.begin = begin;
        this.end = end;
    }

    static ZonedDateTime parse(String datetime) {
        return ZonedDateTime.parse(datetime, formatter);
    }

    /**
     * Retrieves the start datetime associated with the object.
     *
     * @return the start date and time
     */
    @Override
    public String getBeginTimeString() {
        return (this.begin).format(formatter);
    }

    /**
     * Retrieves the start {@link ZonedDateTime} associated with the appointment object.
     *
     * @return the start ZonedDateTime
     */
    @Override
    public ZonedDateTime getBeginTime() {
        return this.begin;
    }

    /**
     * Retrieves the end datetime associated with the object.
     *
     * @return the end date and time
     */
    @Override
    public String getEndTimeString() {
        return (this.end).format(formatter);
    }

    /**
     * Retrieves the end {@link ZonedDateTime} assocated with the appointment object.
     *
     * @return the end ZoneDateTime
     */
    @Override
    public ZonedDateTime getEndTime() {
        return this.end;
    }

    /**
     * Retrieves the description associated with the object.
     *
     * @return the description of the appointment
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Retrieves the owner associated with the object.
     *
     * @return the owner of the appointment
     */
    public String getOwner() {
        return this.owner;
    }

    /**
     * Overrides the equals() method for {@link Object}.
     *
     * @param obj The object to compare with.
     * @return true or false value if the internal data is the same.
     */
    @Override
    public boolean equals(Object obj) {
        // https://mkyong.com/java/java-how-to-overrides-equals-and-hashcode/
        if (!(obj instanceof Appointment)) {
            return false;
        }
        Appointment appointment = (Appointment) obj;
        return (this.owner).equals(appointment.owner) && (this.description).equals(appointment.description) &&
                (this.begin).isEqual(appointment.begin) && (this.end).isEqual(appointment.end);
    }

    /**
     * Overrides the hashCode() method for {@link Object}.
     *
     * @return int The hashcade for the object.
     */
    @Override
    public int hashCode() {
        //https://mkyong.com/java/java-how-to-overrides-equals-and-hashcode/
        return Objects.hash(this.owner, this.description, this.begin, this.end);
    }

    /**
     * Overrides the compareTo function from {@link java.util.Comparator}
     *
     * @param that the Appointment object to be compared to
     * @return a positive or negative int that describes one objects as less than, equal to, or greater than the other object.
     */
    @Override
    public int compareTo(Appointment that) {
        int beginComparison = this.begin.compareTo(that.begin);
        if (beginComparison != 0) {
            return beginComparison;
        }
        int endComparison = this.end.compareTo(that.end);
        if (endComparison != 0) {
            return endComparison;
        }
        return this.description.compareTo(that.description);
    }
}


