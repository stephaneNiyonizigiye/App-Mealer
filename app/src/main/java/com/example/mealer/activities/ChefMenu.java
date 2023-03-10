package com.example.mealer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mealer.R;
import com.example.mealer.structure.Chef;
import com.example.mealer.structure.Meal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChefMenu extends AppCompatActivity implements View.OnClickListener {
    private EditText mealName, mealDescription, mealPrice;
    private Switch vegetarian, availableMeal;
    private Button addMeal,editMenu, backHome;
    private String chefID;
//    private ArrayList<Meal> menu;
    private DatabaseReference databaseMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_menu);

        initializeVariables();
    }

    private void initializeVariables() {
        Bundle extras = getIntent().getExtras();
        chefID = extras.getString("chefID");
        databaseMeals = FirebaseDatabase.getInstance().getReference("Users").child(chefID);

        mealName = (EditText) findViewById(R.id.editTextMealName);
        mealDescription = (EditText) findViewById(R.id.editTextMealDescription);
        mealPrice = (EditText) findViewById(R.id.editTextMealPrice);

        vegetarian = (Switch) findViewById(R.id.switchVegetarian);
        availableMeal = (Switch) findViewById(R.id.switchAvailableMeal);

        addMeal = (Button) findViewById(R.id.btnAddToMenu);
        addMeal.setOnClickListener(this);
        editMenu = (Button) findViewById(R.id.btnEditMenu);
        editMenu.setOnClickListener(this);
        backHome = (Button) findViewById(R.id.btnBackToChefHome);
        backHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddToMenu:
                addMeal();
                break;
            case R.id.btnEditMenu:
                Intent intent = new Intent(this, ChefUpdateMenu.class);
                intent.putExtra("chefID", chefID);
                startActivity(intent);
                break;
            case R.id.btnBackToChefHome:
                finish();
        }
    }

    private void addMeal() {
        if (validCredentials()) {
            final String name = mealName.getText().toString().trim();
            final String description = mealDescription.getText().toString().trim();
            final String price = mealPrice.getText().toString().trim();
            final Boolean vegetarianState = vegetarian.isChecked();
            final Boolean availableState = availableMeal.isChecked();

            databaseMeals.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Meal meal = new Meal(name, description, price);
                    meal.setVegetarian(vegetarianState);
                    meal.setAvailable(availableState);

                    Chef chef = snapshot.getValue(Chef.class);
                    chef.getMenu().addMeal(meal);
                    snapshot.getRef().child("menu").setValue(chef.getMenu());
                    mealName.setText("");
                    mealDescription.setText("");
                    mealPrice.setText("");
                    vegetarian.setChecked(false);
                    availableMeal.setChecked(false);
                    Toast.makeText(ChefMenu.this, "New Meal Added to Menu!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private boolean validCredentials() {
        boolean isValid = true;

        String name = mealName.getText().toString().trim();
        String description = mealDescription.getText().toString().trim();
        String price = mealPrice.getText().toString().trim();

        if (name.isEmpty()) {
            mealName.setError("Please Enter a Meal Name");
            mealName.requestFocus();
            isValid = false;
        }
        if (description.isEmpty()) {
            mealDescription.setError("Please Enter a Meal Description");
            mealDescription.requestFocus();
            isValid = false;
        } else if (description.length() > 101) {
            mealDescription.setError("Maximum length is 100 characters!");
            mealDescription.requestFocus();
        }
        if (price.isEmpty()) {
            mealPrice.setError("Please Enter a price for the meal");
            mealPrice.requestFocus();
            isValid = false;
        }
        return isValid;
    }
}