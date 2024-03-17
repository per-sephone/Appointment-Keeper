package edu.pdx.cs410J.nob;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import edu.pdx.cs410J.nob.databinding.PrintAllBinding;

public class PrintAll extends Fragment {
    private PrintAllBinding binding;
    private AppointmentBook book;
    private ArrayAdapter<Appointment> appointments;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = PrintAllBinding.inflate(inflater, container, false);

        Bundle bundle = getArguments();
        if(bundle != null) {
            this.book = (AppointmentBook) bundle.getSerializable("appointmentBook");
        }

        ListView listView = binding.allAppointments;

        this.appointments = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, this.book.getAppointments());
        listView.setAdapter(this.appointments);

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
