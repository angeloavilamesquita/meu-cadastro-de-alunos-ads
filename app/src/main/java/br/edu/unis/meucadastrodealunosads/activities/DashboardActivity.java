package br.edu.unis.meucadastrodealunosads.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import br.edu.unis.meucadastrodealunosads.R;
import br.edu.unis.meucadastrodealunosads.dao.AppDatabase;
import br.edu.unis.meucadastrodealunosads.dao.UserDao;
import br.edu.unis.meucadastrodealunosads.entities.User;

public class DashboardActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.dashboard_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        UserDao userDao = db.userDao();

        for (User user: userDao.getAll()) {
            Log.d("USERS_CREATED", user.uid + "-" + user.username);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}