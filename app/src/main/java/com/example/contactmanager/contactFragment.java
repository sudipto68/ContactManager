package com.example.contactmanager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.contactmanager.adapters.ContactAdapter;
import com.example.contactmanager.databinding.FragmentContactBinding;
import com.example.contactmanager.models.Contact;
import com.example.contactmanager.viewmodels.ContactViewModel;

import java.util.List;


public class contactFragment extends Fragment {

    private FragmentContactBinding binding;
    private ContactAdapter adapter;
    private ContactViewModel contactViewModel;
    public contactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactBinding.inflate(inflater);
        contactViewModel = new ViewModelProvider(requireActivity()).get(ContactViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        contactViewModel.getContactLiveData().observe(getViewLifecycleOwner(), new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {

                if(contacts.size()>0){
                    binding.emptyIV.setVisibility(View.GONE);
                }
                adapter = new ContactAdapter(contacts,getActivity());
                binding.contactRV.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.contactRV.setAdapter(adapter);

            }
        });


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_contactFragment_to_addcontactFragment);
            }
        });


    }
}
