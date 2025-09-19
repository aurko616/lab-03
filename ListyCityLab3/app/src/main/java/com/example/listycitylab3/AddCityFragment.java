package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(City city);
    }

    private AddCityDialogListener listener;
    private City cityToEdit; // null means adding

    public AddCityFragment() {
        // empty constructor
    }

    public static AddCityFragment newInstance(City city) {
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Check if editing
        if (getArguments() != null) {
            cityToEdit = (City) getArguments().getSerializable("city");
            if (cityToEdit != null) {
                editCityName.setText(cityToEdit.getName());
                editProvinceName.setText(cityToEdit.getProvince());
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(cityToEdit == null ? "Add a city" : "Edit city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(cityToEdit == null ? "Add" : "Save",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = editCityName.getText().toString();
                                String province = editProvinceName.getText().toString();

                                if (cityToEdit == null) {
                                    // adding
                                    listener.addCity(new City(name, province));
                                } else {
                                    // editing
                                    cityToEdit.setName(name);
                                    cityToEdit.setProvince(province);
                                    listener.editCity(cityToEdit);
                                }
                            }
                        })
                .create();
    }
}
