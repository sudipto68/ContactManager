package com.example.contactmanager.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactmanager.R;
import com.example.contactmanager.databinding.ContactRowBinding;
import com.example.contactmanager.db.ContactDb;
import com.example.contactmanager.models.Contact;
import com.example.contactmanager.viewmodels.ContactViewModel;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private ContactRowBinding binding;
    private Context context;
    private List<Contact> contactArrayList;
    private ContactViewModel contactViewModel;

    public ContactAdapter(List<Contact> contactArrayList,Context context) {

        this.contactArrayList = contactArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = ContactRowBinding.inflate(LayoutInflater.from(context));
        contactViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(ContactViewModel.class);
        return new ContactViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, final int position) {
        binding.rowName.setText(contactArrayList.get(position).getName());
        binding.rowEmail.setText(contactArrayList.get(position).getEmail());
        binding.menuIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                PopupMenu popupMenu = new PopupMenu(context,v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        final Contact contact = contactArrayList.get(position);

                        switch (item.getItemId()){

                            case R.id.edit_popup:
                                long id = contact.getId();
                                Bundle bundle = new Bundle();
                                bundle.putLong("id",id);
                                Navigation.findNavController(v).navigate(R.id.action_contactFragment_to_addcontactFragment,bundle);
                                return true;

                            case R.id.delete_popup:

                                confirmDelete(contact,position);

                                return true;

                            default:
                                return false;
                        }
                    }


                });
            }
        });

    }
    private void confirmDelete(final Contact contact, int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_delete_black_24dp);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure to delete "+contact.getName()+ " ?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                contactViewModel.deleteContactToRepo(contact);
                contactArrayList.remove(contact);
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancle",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return contactArrayList.size();
    }


    class ContactViewHolder extends RecyclerView.ViewHolder{

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
