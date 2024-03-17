package edu.pdx.cs410J.nob;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import edu.pdx.cs410J.nob.databinding.AddNewApptBinding;

public class AddNewAppt extends Fragment{

    private AddNewApptBinding binding;
    private EditText description;
    private DatePicker beginDate;
    private DatePicker endDate;
    private TimePicker beginTime;
    private TimePicker endTime;
    private AppointmentBook book;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AddNewApptBinding.inflate(inflater, container, false);
        this.description = binding.description;
        this.beginDate = binding.beginDate;
        this.beginTime = binding.beginTime;
        this.endDate = binding.endDate;
        this.endTime = binding.endTime;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.book = (AppointmentBook) bundle.getSerializable("appointmentBook");
        }
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.submitNewAppt.setOnClickListener(v ->
            createNewAppointment(view)
        );

    }

    private void createNewAppointment(View view) {
        TimeZone timeZone = TimeZone.getTimeZone("America/Los_Angeles");
        ZonedDateTime begin = ZonedDateTime.of(beginDate.getYear(), beginDate.getMonth(), beginDate.getDayOfMonth(),
                beginTime.getHour(), beginTime.getMinute(), 0 ,0 , timeZone.toZoneId());
        ZonedDateTime end = ZonedDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(),
                endTime.getHour(), endTime.getMinute(), 0 , 0, timeZone.toZoneId());

        String desc = description.getText().toString();

        if(!beginTimeBeforeEndTime(begin, end)) {
            Toast.makeText(getContext(), "Begin time must come before End time", Toast.LENGTH_LONG).show();
        }
        else if(desc.isEmpty()) {
            Toast.makeText(getContext(), "Please fill out description", Toast.LENGTH_LONG).show();
        }
        else {
            Appointment apt = new Appointment(this.book.getOwnerName(), desc, begin, end);
            this.book.addAppointment(apt);
            writeAppointmentBookFile();

            Toast.makeText(getContext(), "SUCCESSFUL APPT ADD", Toast.LENGTH_LONG).show();

            Bundle bundle = new Bundle();
            bundle.putSerializable("appointmentBook", this.book);

            NavHostFragment.findNavController(AddNewAppt.this)
                    .navigate(R.id.action_AddNewAppt_to_SecondFragment, bundle);
        }
    }

    private boolean beginTimeBeforeEndTime(ZonedDateTime begin, ZonedDateTime end) {
        return begin.compareTo(end) < 0;
    }

    public void writeAppointmentBookFile() {
        Context context = getContext();
        if (context != null) {
            File dataDirectory = context.getDataDir();
            File bookFile = new File(dataDirectory, this.book.getOwnerName() + ".txt");
            try {
                FileWriter fileWriter = new FileWriter(bookFile);
                TextDumper textDump = new TextDumper(fileWriter);
                textDump.dump(this.book);
            } catch (IOException i) {
                Toast.makeText(context, "IO Error Occurred: " + i, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
