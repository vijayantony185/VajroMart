package com.example.vmart.RoomDataBase.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.vmart.Model.Products;

import java.util.List;

@Dao
public interface ProductDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insertProduct(Products mainData);

    //Delete query
    @Delete
    void deleteProduct(Products mainData);

    //Delete all query
    @Delete
    void resetProduct(List<Products> mainData);

    //Update query
    @Query("UPDATE cart_product SET cartQty = :sQuantity WHERE ID = :sID")
    void updateProduct(int sID, int sQuantity);

    //Get all data query
    @Query("SELECT * FROM cart_product")
    List<Products> getAllCartProducts();

    //Get data by id query
    @Query("SELECT * FROM cart_product WHERE ID = :sID")
    Products getProductById(int sID);
}
