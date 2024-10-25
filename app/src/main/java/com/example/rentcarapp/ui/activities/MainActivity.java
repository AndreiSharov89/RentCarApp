package com.example.rentcarapp.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.rentcarapp.R;
import com.example.rentcarapp.data.AppDatabase;
import com.example.rentcarapp.data.entity.Car;
import com.example.rentcarapp.data.entity.Customer;
import com.example.rentcarapp.data.entity.Location;
import com.example.rentcarapp.data.entity.Rental;
import com.example.rentcarapp.data.entity.Review;
import com.example.rentcarapp.ui.fragments.CarListFragment;
import com.example.rentcarapp.ui.fragments.ClientPagerFragment;
import com.example.rentcarapp.ui.fragments.MainFragment;
import com.example.rentcarapp.ui.fragments.ReviewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private Handler backgroundHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        Drawable navigationIcon = toolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.setTint(getResources().getColor(R.color.white, getTheme()));
        }

        toolbar.setNavigationOnClickListener(v -> {
            Toast.makeText(this, "Функционал пока не реализован", Toast.LENGTH_SHORT).show();
        });

        loadFragment(new MainFragment());

        db = AppDatabase.getInstance(getApplicationContext());

        HandlerThread handlerThread = new HandlerThread("DatabaseThread");
        handlerThread.start();
        backgroundHandler = new Handler(handlerThread.getLooper());
        backgroundHandler.post(() -> {
            if (db.customerDao().getAllCustomers().isEmpty()) {
                addRandomCustomers();
            }
            if (db.carDao().getAllCars().isEmpty()) {
                addRandomCars();
            }
            if (db.locationDao().getAllLocations().isEmpty()) {
                addRandomLocations();
            }
            if (db.reviewDao().getAllReviews().isEmpty()) {
                addRandomReviews();
            }
        });

        if (!isUserLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.nav_add_client) {
                selectedFragment = new MainFragment();
            } else if (item.getItemId() == R.id.nav_client_list) {
                selectedFragment = new ClientPagerFragment();
            } else if (item.getItemId() == R.id.nav_car_list) {
                selectedFragment = new CarListFragment();
            } else if (item.getItemId() == R.id.nav_reviews) {
                selectedFragment = new ReviewFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }

            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem userItem = menu.findItem(R.id.action_user);
        if (userItem != null && userItem.getIcon() != null) {
            userItem.getIcon().setTint(getResources().getColor(R.color.white, getTheme()));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_user) {
            showUserPopup(findViewById(R.id.action_user));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showUserPopup(View anchorView) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_user_info, null);

        TextView tvUserName = popupView.findViewById(R.id.tvUserName);
        TextView tvUserFirstName = popupView.findViewById(R.id.tvUserFirstName);
        TextView tvUserLastName = popupView.findViewById(R.id.tvUserLastName);
        TextView tvUserEmail = popupView.findViewById(R.id.tvUserEmail);

        tvUserName.setText("Пользователь:");
        tvUserFirstName.setText("Имя: " + "Иван");
        tvUserLastName.setText("Фамилия: " + "Иванов");
        tvUserEmail.setText("Должность: " + "Менеджер");

        final PopupWindow popupWindow = new PopupWindow(popupView,
                Toolbar.LayoutParams.WRAP_CONTENT,
                Toolbar.LayoutParams.WRAP_CONTENT,
                true);

        popupWindow.showAsDropDown(anchorView, 0, 0, Gravity.END);
    }

    /**
     * Метод для рандомного заполнения клиентами базы данных
     */
    private void addRandomCustomers() {
        String[] names = {
                "Иван Иванов Иванович", "Алексей Смирнов Алексеевич", "Дмитрий Кузнецов Иванович",
                "Андрей Попов Алексеевич", "Михаил Лебедев Сергеевич", "Александр Морозов Сергеевич",
                "Сергей Новиков Вячеславовчи", "Владимир Киселёв Михайлович", "Павел Соколов Алексеевич",
                "Николай Михайлов Иванович", "Юрий Петров Иванович", "Олег Фёдоров Алексеевич"
        };

        String[] addresses = {
                "ул. Тверская, д. 10", "ул. Арбат, д. 15", "ул. Петровка, д. 20",
                "ул. Большая Дмитровка, д. 25", "ул. Красная Пресня, д. 30",
                "ул. Неглинная, д. 35", "ул. Ленинградский пр-т, д. 40",
                "ул. Новокузнецкая, д. 45", "ул. Преображенская, д. 50",
                "ул. Сретенка, д. 55", "ул. Якиманка, д. 60", "ул. Большая Полянка, д. 65"
        };

        String[] emailDomains = {"@stub.pm", "@example.com"};
        Random random = new Random();

        for (int i = 0; i < names.length; i++) {
            Customer customer = new Customer();
            customer.fullName = names[i];
            customer.address = addresses[i];
            customer.phone = "+7" + (9000000000L + (long) (Math.random() * 999999999));

            String emailPrefix = "user" + random.nextInt(10000);
            String emailDomain = emailDomains[random.nextInt(emailDomains.length)];
            customer.email = emailPrefix + emailDomain;

            customer.segment = random.nextInt(5) + 1;

            customer.rating = random.nextInt(10) + 1;

            db.customerDao().insertCustomer(customer);
        }
    }

    /**
     * Метод для рандомного заполнения автомобилями базы данных
     */
    private void addRandomCars() {
        List<Location> locations = db.locationDao().getAllLocations();
        if (locations.isEmpty()) {
            return;
        }

        String[] brands = {"Chevrolet", "Kia", "Suzuki", "Ford", "Volkswagen", "Honda", "Toyota", "Mazda"};
        String[] models = {"Spark", "Picanto", "Alto", "Fiesta", "Polo", "Civic", "Corolla", "6"};
        String[] segments = {
                "A-segment mini cars",
                "B-segment small cars",
                "C-segment medium cars",
                "D-segment large cars"
        };
        String[] conditions = {"New", "Used", "Certified"};
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            Car car = new Car();
            int index = random.nextInt(brands.length);
            car.brand = brands[index];
            car.model = models[index];

            if (car.brand.equals("Chevrolet") || car.brand.equals("Kia") || car.brand.equals("Suzuki")) {
                car.segment = segments[0];
            } else if (car.brand.equals("Ford") || car.brand.equals("Volkswagen") && car.model.equals("Polo")) {
                car.segment = segments[1];
            } else if (car.brand.equals("Honda") || car.brand.equals("Toyota") || car.model.equals("Golf")) {
                car.segment = segments[2];
            } else if (car.brand.equals("Ford") && car.model.equals("Mondeo") || car.brand.equals("Toyota") && car.model.equals("Camry") || car.brand.equals("Mazda")) {
                car.segment = segments[3];
            }

            car.year = 2000 + random.nextInt(23);
            car.registrationNumber = "A" + random.nextInt(999) + "BC" + (random.nextInt(99) + 1);
            car.condition = conditions[random.nextInt(conditions.length)];

            int locationIndex = random.nextInt(locations.size());
            car.locationId = locations.get(locationIndex).id;

            db.carDao().insertCar(car);
        }
    }

    /**
     * Метод для рандомного заполнения адресов
     */
    private void addRandomLocations() {
        String[] addresses = {
                "ул. Тверская, д. 12, Москва",
                "ул. Невский проспект, д. 22, Москва",
                "ул. Ленина, д. 5, Москва",
                "ул. Большая Покровская, д. 34, Москва",
                "ул. Баумана, д. 18, Москва"
        };

        for (String address : addresses) {
            Location location = new Location();
            location.address = address;
            db.locationDao().insertLocation(location);
        }
    }

    private void addRandomReviews() {
        List<Rental> rentals = db.rentalDao().getAllRentals();
        if (rentals.isEmpty()) {
            return;
        }

        String[] reviewTexts = {
                "Отличный сервис, все понравилось!",
                "Автомобиль был в хорошем состоянии.",
                "Сервис мог бы быть лучше, но в целом неплохо.",
                "Очень доволен обслуживанием и качеством машины.",
                "Не понравилось, что машину пришлось ждать."
        };

        Random random = new Random();
        for (Rental rental : rentals) {
            Review review = new Review();
            review.orderId = rental.id;
            review.serviceRating = random.nextInt(5) + 1;
            review.carRating = random.nextInt(5) + 1;
            review.customerReview = reviewTexts[random.nextInt(reviewTexts.length)];
            review.customerRating = random.nextInt(5) + 1;

            db.reviewDao().insertReview(review);
        }
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return sharedPreferences.contains("login") && sharedPreferences.contains("password");
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}