package edu.pdx.cs410J.nob;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.time.ZonedDateTime;
import java.util.TimeZone;

import edu.pdx.cs410J.nob.databinding.SearchApptsBinding;

public class SearchAppts extends Fragment {

    private SearchApptsBinding binding;
    private DatePicker searchStartDate;
    private DatePicker searchEndDate;
    private TimePicker searchStartTime;
    private TimePicker searchEndTime;

    private AppointmentBook book;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = SearchApptsBinding.inflate(inflater, container, false);

        this.searchStartDate = binding.searchStartDate;
        this.searchEndDate = binding.searchEndDate;
        this.searchStartTime = binding.searchStartTime;
        this.searchEndTime = binding.searchEndTime;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.book = (AppointmentBook) bundle.getSerializable("appointmentBook");
        }

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.submitSearch.setOnClickListener(v ->
            searchForAppointments()
        );
    }

    private void searchForAppointments() {
        TimeZone timeZone = TimeZone.getTimeZone("America/Los_Angeles");
        ZonedDateTime start = ZonedDateTime.of(this.searchStartDate.getYear(), this.searchStartDate.getMonth(),
                this.searchStartDate.getDayOfMonth(), this.searchStartTime.getHour(), this.searchStartTime.getMinute(),
                0 ,0 , timeZone.toZoneId());
        ZonedDateTime end = ZonedDateTime.of(this.searchEndDate.getYear(), this.searchEndDate.getMonth(),
                this.searchEndDate.getDayOfMonth(), this.searchEndTime.getHour(), this.searchEndTime.getMinute(),
                0 , 0, timeZone.toZoneId());

        if(!beginTimeBeforeEndTime(start, end)) {
            Toast.makeText(getContext(), "Begin time must come before End time", Toast.LENGTH_LONG).show();
        } else {

            Bundle bundle = new Bundle();
            bundle.putSerializable("start", start);
            bundle.putSerializable("end", end);
            bundle.putSerializable("appointmentBook", this.book);

            NavHostFragment.findNavController(SearchAppts.this)
                    .navigate(R.id.action_SearchAppts_to_SearchResults, bundle);
        }
    }

    private boolean beginTimeBeforeEndTime(ZonedDateTime begin, ZonedDateTime end) {
        return begin.compareTo(end) < 0;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
