package br.edu.unis.meucadastrodealunosads.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.edu.unis.meucadastrodealunosads.entities.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE USERNAME = :username")
    User findUsername(String username);

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE username = :username "
    + "AND password = :password")
    User authenticate(String username, String password);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(User... user);

    @Delete
    void delete(User user);
}
