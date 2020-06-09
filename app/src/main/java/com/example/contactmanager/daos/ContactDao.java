package com.example.contactmanager.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contactmanager.models.Contact;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    long insertContact(Contact contact);

    @Update
    int updateContact(Contact contact);

    @Delete
    int deleteContact(Contact contact);

    @Query("select * from tbl_contact")
    LiveData<List<Contact>> getallContact();

    @Query("select * from tbl_contact where id like :id")
    Contact getContact(long id);
}
