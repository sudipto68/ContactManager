package com.example.contactmanager.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.contactmanager.daos.ContactDao;
import com.example.contactmanager.models.Contact;

@Database(entities = {Contact.class},version = 1)
public abstract class ContactDb extends RoomDatabase {

    public abstract ContactDao getContactDao();

    private static ContactDb db;

    public static ContactDb getInstance(Context context){

        if(db !=null){
            return db;
        }
        db = Room.databaseBuilder(context,ContactDb.class,"Contact_db").allowMainThreadQueries()
                .build();
        return db;
    }




}
