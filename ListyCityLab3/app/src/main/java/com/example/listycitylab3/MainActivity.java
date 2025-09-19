package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Starter data
        String[] cities = {"Edmonton", "Vancouver", "Toronto"};
        String[] provinces = {"AB", "BC", "ON"};

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // Tap a city to edit it
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City selectedCity = dataList.get(position);
            AddCityFragment editFragment = AddCityFragment.newInstance(selectedCity);
            editFragment.show(getSupportFragmentManager(), "Edit City");
        });

        // FAB to add new city
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            AddCityFragment addFragment = new AddCityFragment();
            addFragment.show(getSupportFragmentManager(), "Add City");
        });
    }

    // --- From AddCityFragment interface ---
    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void editCity(City city) {
        cityAdapter.notifyDataSetChanged(); // refresh after editing
    }
}
