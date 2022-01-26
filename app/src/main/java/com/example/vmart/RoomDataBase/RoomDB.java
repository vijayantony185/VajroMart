package com.example.vmart.RoomDataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.vmart.Model.Products;
import com.example.vmart.RoomDataBase.Dao.ProductDao;


//Add database entities
@Database(entities = {Products.class},version = 1,exportSchema = false)

public abstract class RoomDB extends RoomDatabase {
    //creat database instance
    private static RoomDB database;
    //Define database name
    private static String DATABASE_NAME = "database";

    public synchronized static RoomDB getInstance(Context context){
        //check condition
        if (database == null){
            //when database is null
            //Initialize database
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        //Return database
        return database;
    }

    //Creat Dao
    public abstract ProductDao productDao();
}
