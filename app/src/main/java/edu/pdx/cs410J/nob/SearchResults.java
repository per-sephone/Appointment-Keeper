package edu.pdx.cs410J.nob;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.time.ZonedDateTime;

import edu.pdx.cs410J.nob.databinding.SearchResultsBinding;

public class SearchResults extends Fragment {
    private SearchResultsBinding binding;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private AppointmentBook book;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = SearchResultsBinding.inflate(inflater, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.start = (ZonedDateTime) bundle.getSerializable("start");
            this.end = (ZonedDateTime) bundle.getSerializable("end");
            this.book = (AppointmentBook) bundle.getSerializable("appointmentBook");
        }

        ListView listView = binding.searchResults;

        ArrayAdapter<Appointment> appointments = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1,
                this.book.getAppointmentsBetween(this.start, this.end));
        listView.setAdapter(appointments);

        if (this.book.getAppointmentsBetween(this.start, this.end).isEmpty()) {
            Toast.makeText(getContext(), "No Appointments Found!", Toast.LENGTH_LONG).show();
        }

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
