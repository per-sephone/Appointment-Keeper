package edu.pdx.cs410J.nob;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import edu.pdx.cs410J.nob.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private AppointmentBook book;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);

        Bundle args = getArguments();
        if (args != null) {
            this.book = (AppointmentBook) args.getSerializable("appointmentBook");
        }
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.printAll.setOnClickListener(v ->
                printAll(view)
        );

        binding.addNewAppt.setOnClickListener(v ->
            addNewAppt(view)
        );

        binding.searchAppts.setOnClickListener(v ->
            searchAppts(view)
        );

        binding.help2.setOnClickListener(v ->
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_Help)
        );
    }

    private void addNewAppt(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("appointmentBook", this.book);
        NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_AddNewAppt, bundle);

    }

    private void printAll(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("appointmentBook", this.book);
        NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_PrintAll, bundle);
    }

    private void searchAppts(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("appointmentBook", this.book);
        NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_SearchAppts, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}