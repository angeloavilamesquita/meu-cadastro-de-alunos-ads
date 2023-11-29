package br.edu.unis.meucadastrodealunosads.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.unis.meucadastrodealunosads.R;
import br.edu.unis.meucadastrodealunosads.dao.AppDatabase;
import br.edu.unis.meucadastrodealunosads.dao.UserDao;
import br.edu.unis.meucadastrodealunosads.entities.User;

public class NewAccountActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText edtUser;
    EditText edtPassword;
    EditText edtRePassword;
    Button btnSave;
    Button btnClear;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        initiateWidgets();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnClear.setOnClickListener(view -> clearAllFields());

        btnSave.setOnClickListener(view -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            UserDao userDao = db.userDao();

            String username = edtUser.getText().toString();
            String password = edtPassword.getText().toString();
            String rePassword = edtRePassword.getText().toString();

            if (!validateCredentials(username, password, rePassword)) {
                Toast.makeText(
                        NewAccountActivity.this,
                        "Todos os campos devem ser preenchidos",
                        Toast.LENGTH_LONG).show();
                return;
            }

            if (!password.equals(rePassword)) {
                Toast.makeText(
                        NewAccountActivity.this,
                        "As duas senhas devem ser iguais",
                        Toast.LENGTH_LONG).show();
                return;
            }

            User userExists = userDao.findUsername(username);
            if (userExists instanceof User) {
                Toast.makeText(
                        NewAccountActivity.this,
                        "O Username precisa ser único!",
                        Toast.LENGTH_LONG).show();
                return;
            }


            User user = new User();
            user.username = username;
            user.password = password;

            try {
                userDao.insertAll(user);
                Toast.makeText(
                        NewAccountActivity.this,
                        "Usuário criado com sucesso",
                        Toast.LENGTH_LONG).show();
                return;
            } catch (SQLiteException exception) {
                Log.e("SQLITE", "INSERT USER: " + exception.getMessage());
            }

        });
    }

    public void initiateWidgets() {
        toolbar = findViewById(R.id.new_account_toolbar);
        edtUser = findViewById(R.id.new_account_edt_user);
        edtPassword = findViewById(R.id.new_account_edt_password);
        edtRePassword = findViewById(R.id.new_account_edt_re_password);
        btnSave = findViewById(R.id.new_account_btn_save);
        btnClear = findViewById(R.id.new_account_btn_clear);
    }

    public void clearAllFields() {
        edtUser.setText("");
        edtPassword.setText("");
        edtRePassword.setText("");
        edtUser.requestFocus();
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

    private boolean validateCredentials(String user, String password, String rePassword) {
        return !user.isEmpty() && !password.isEmpty() && !rePassword.isEmpty();
    }
}