package edu.pdx.cs410J.nob;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.nob.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private AppointmentBook book;
    EditText mainMenuName;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        this.mainMenuName = binding.mainMenuName;
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.submitOwnerName.setOnClickListener(v ->
                getAppointmentBookFile(view)
        );
        binding.help.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_Help)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void getAppointmentBookFile(View view) {
        String name = mainMenuName.getText().toString().toLowerCase();
        if (name.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a name!", Toast.LENGTH_LONG).show();
        } else {
            File dataDirectory = getContext().getDataDir();
            File bookFile = new File(dataDirectory, name + ".txt");

            try {
                if (bookFile.exists()) {
                    FileReader fileReader = new FileReader(bookFile);
                    TextParser textParser = new TextParser(fileReader);
                    this.book = textParser.parse();
                } else { //file does not exist
                    FileWriter writer = new FileWriter(bookFile);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    bufferedWriter.write(name + "\n"); //add the owner to the file
                    bufferedWriter.close();
                    this.book = new AppointmentBook(name);
                }
            } catch (IOException i) {
                Toast.makeText(getContext(), "IO Error Occurred: " + i, Toast.LENGTH_LONG).show();
            } catch (ParserException p) {
                Toast.makeText(getContext(), "Error parsing file: " + p, Toast.LENGTH_LONG).show();
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("appointmentBook", this.book);

            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);

        }
    }

}