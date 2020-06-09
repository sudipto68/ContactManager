package com.example.contactmanager.repos;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.contactmanager.db.ContactDb;
import com.example.contactmanager.models.Contact;

import java.util.List;

public class ContactRepo {

    private Context context;

    public ContactRepo(Context context) {
        this.context = context;
    }

    public long insertContactIntoDB(Contact contact){
        return ContactDb.getInstance(context).getContactDao().insertContact(contact);
    }

    public int deleteContectIntoDb(Contact contact){
        return ContactDb.getInstance(context).getContactDao().deleteContact(contact);
    }

    public LiveData<List<Contact>> getContacts(){
        return ContactDb.getInstance(context).getContactDao().getallContact();
    }

    public int updateContactRepo(Contact contact){
        return ContactDb.getInstance(context).getContactDao().updateContact(contact);
    }



}
