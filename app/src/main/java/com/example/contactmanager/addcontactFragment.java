package com.example.contactmanager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.contactmanager.databinding.FragmentAddcontactBinding;
import com.example.contactmanager.db.ContactDb;
import com.example.contactmanager.models.Contact;
import com.example.contactmanager.viewmodels.ContactViewModel;


public class addcontactFragment extends Fragment {

    private FragmentAddcontactBinding binding;
    private long id = 0;
    private ContactViewModel contactViewModel;

    public addcontactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentAddcontactBinding.inflate(inflater);
        contactViewModel = new ViewModelProvider(requireActivity()).get(ContactViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            id = bundle.getLong("id");
            final Contact contact = ContactDb.getInstance(getActivity()).getContactDao().getContact(id);
            binding.nameET.setText(contact.getName());
            binding.mailET.setText(contact.getEmail());
            binding.numberET.setText(contact.getPhonenumber());
            binding.saveBtn.setText("Update");
        }
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.nameET.getText().toString();
                String email = binding.mailET.getText().toString();
                String number = binding.numberET.getText().toString();

                Contact contact = new Contact(name,email,number);
                if(id>0){

                    contact.setId(id);
                    contactViewModel.updateContactToRepo(contact);
                    Navigation.findNavController(v).navigate(R.id.action_addcontactFragment_to_contactFragment);

                }else{
                    long rowid = contactViewModel.insertContactToRepo(contact);
                    if(rowid>0){
                        Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(v).navigate(R.id.action_addcontactFragment_to_contactFragment);
                    }else{
                        Toast.makeText(getActivity(), "Could not save contact", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}
