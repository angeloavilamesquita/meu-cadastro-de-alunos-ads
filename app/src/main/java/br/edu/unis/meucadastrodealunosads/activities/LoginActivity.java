package br.edu.unis.meucadastrodealunosads.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.unis.meucadastrodealunosads.R;
import br.edu.unis.meucadastrodealunosads.daos.AppDatabase;
import br.edu.unis.meucadastrodealunosads.daos.UserDao;
import br.edu.unis.meucadastrodealunosads.entities.User;

public class LoginActivity extends AppCompatActivity {

    TextView newAccount;
    EditText edtUser;
    EditText edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initiateWidgets();
        makeCreateNewAccountButton();
        AuthenticateUser();
    }

    private void initiateWidgets() {
        edtUser = findViewById(R.id.login_edt_user);
        edtPassword = findViewById(R.id.login_edt_password);
        btnLogin = findViewById(R.id.login_btn_login);
        newAccount = findViewById(R.id.login_txt_create_account);
    }

    private void makeCreateNewAccountButton() {
        SpannableString ss = new SpannableString(newAccount.getText());
        ClickableSpan cs = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(LoginActivity.this, NewAccountActivity.class);
                startActivity(intent);
            }
        };
        ss.setSpan(cs, 0, newAccount.getText().length(), 0);
        newAccount.setText(ss);
        newAccount.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void AuthenticateUser() {
        btnLogin.setOnClickListener(view -> {
            String user = edtUser.getText().toString();
            String password = edtPassword.getText().toString();

            if (!validateCredentials(user, password)) {
                Toast.makeText(
                        LoginActivity.this, R.string.login_txt_fields_can_not_empty,
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            boolean isAuthenticated = authenticate(user, password);
            if (!isAuthenticated) {
                Toast.makeText(
                        LoginActivity.this, R.string.login_txt_user_or_password_incorrets,
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        });
    }

    private boolean validateCredentials(String user, String password) {
        return !user.isEmpty() && !password.isEmpty();
    }

    private boolean authenticate(String user, String password) {
        AppDatabase db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "cadastro-alunos"
        ).allowMainThreadQueries()
        .build();

        UserDao userDao = db.userDao();
        User hasUser = userDao.authenticate(user, password);

        return hasUser != null;
    }
}
