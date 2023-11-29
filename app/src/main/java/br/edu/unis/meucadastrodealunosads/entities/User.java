package br.edu.unis.meucadastrodealunosads.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    public long uid;

    public String username;
    public String password;

}
