package com.example.contactmanager.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.contactmanager.models.Contact;
import com.example.contactmanager.repos.ContactRepo;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private ContactRepo contactRepo;
    private Context context;
    private LiveData<List<Contact>> contactLiveData;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
        contactRepo = new ContactRepo(context);

    }


    public long insertContactToRepo(Contact contact){
        return contactRepo.insertContactIntoDB(contact);
    }

    public int updateContactToRepo(Contact contact){
        return contactRepo.updateContactRepo(contact);
    }

    public int deleteContactToRepo(Contact contact){
        return contactRepo.deleteContectIntoDb(contact);
    }

    public LiveData<List<Contact>> getContactLiveData(){

        if(contactLiveData != null){
            return contactLiveData;
        }else{

            contactLiveData = contactRepo.getContacts();
            return contactLiveData;
        }
    }


}
