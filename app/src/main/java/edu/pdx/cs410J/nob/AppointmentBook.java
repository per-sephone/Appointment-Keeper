package edu.pdx.cs410J.nob;

import edu.pdx.cs410J.AbstractAppointmentBook;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Used to manage a collection of Appointment objects
 * @author Nora Luna
 */
public class AppointmentBook extends AbstractAppointmentBook<Appointment> {
    /**
     * The person the AppointmentBook belongs to
     */
    String owner;
    /**
     * The collection of appointments
     */
    private final ArrayList<Appointment> appointments;

    /**
     * constructs a new AppointmentBook with a given owner
     * sets the empty collection of appointments as an ArrayList
     * @param owner the person the AppointmentBook belongs to
     */
    public AppointmentBook(String owner) {
        this.owner = owner;
        this.appointments = new ArrayList<>();
    }

    /**
     * Retrieves the owner associated with the AppointmentBook.
     * @return the name of the owner
     */
    @Override
    public String getOwnerName() {
        return this.owner;
    }

    /**
     * Retrieves the collection of appointments in the AppointmentBook.
     * Prints out an error message if there are no appointments in the AppointmentBook.
     * @return the collection of appointments
     */
    @Override
    public ArrayList<Appointment> getAppointments() {
        if (this.appointments.isEmpty()) {
            System.err.println("Appointment book empty, please add a new appointment.");
        }
        return this.appointments;
    }
    public ArrayList<Appointment> getAppointmentsBetween(ZonedDateTime begin, ZonedDateTime end) {
        ArrayList<Appointment> valid = new ArrayList<>();

        for(Appointment apt: this.appointments) {
            if (begin.compareTo(apt.getBeginTime()) <= 0 && apt.getBeginTime().compareTo(end) <= 0) {
                valid.add(apt);
            }
        }
        return valid;
    }
    /**
     * Adds a new Appointment object to the collection of appointments.
     * The appointment is added sorted by begin time, end time, then description.
     * @param appt the Appointment object to be added to the collection of Appointments
     */
    @Override
    public void addAppointment(Appointment appt) {
        if (appointments.isEmpty()) {
            appointments.add(appt);
            return;
        }

        ArrayList<Appointment> temp = (ArrayList<Appointment>) appointments.clone();

        for (int i = 0; i < temp.size(); i++) {
            if (appt.compareTo(temp.get(i)) < 0) { //appt less than appointments[i]
                appointments.add(i, appt);
                return;
            }
            if (appt.compareTo(temp.get(i)) == 0) { //duplicate appt
                //do not add this appointment to the book
                return;
            }
        }
        appointments.add(appt); //added to the end of the list
    }

    /**
     * Overrides the equals() method for {@link Object}.
     * @param obj The object to compare with.
     * @return true or false value if the internal data is the same.
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof AppointmentBook)) {return false;}
        AppointmentBook aptBook = (AppointmentBook) obj;
        return this.owner.equals(aptBook.owner) && this.appointments.equals(aptBook.appointments);
    }

    /**
     * Overrides the hashCode() method for {@link Object}.
     * @return int The hashcade for the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.appointments, this.owner);
    }
}
