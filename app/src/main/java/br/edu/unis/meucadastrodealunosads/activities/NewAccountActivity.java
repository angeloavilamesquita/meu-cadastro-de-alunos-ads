package br.edu.unis.meucadastrodealunosads.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.List;

import br.edu.unis.meucadastrodealunosads.R;
import br.edu.unis.meucadastrodealunosads.daos.AppDatabase;
import br.edu.unis.meucadastrodealunosads.daos.UserDao;
import br.edu.unis.meucadastrodealunosads.entities.User;

public class NewAccountActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        toolbar = findViewById(R.id.new_account_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        UserDao userDao = db.userDao();
        // TODO implements new account persistence
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