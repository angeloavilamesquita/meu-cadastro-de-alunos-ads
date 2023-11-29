package br.edu.unis.meucadastrodealunosads.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.edu.unis.meucadastrodealunosads.entities.User;

@Database(entities = {User.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private volatile static AppDatabase instance;

    public abstract UserDao userDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            return Room.databaseBuilder(
                    context,
                    AppDatabase.class,
                    "cadastro-alunos.db"
            ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build();
        }
        return instance;
    }
}
