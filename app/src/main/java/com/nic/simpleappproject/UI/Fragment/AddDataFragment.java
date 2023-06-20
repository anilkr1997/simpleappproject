package com.nic.simpleappproject.UI.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.model.LatLng;
import com.nic.simpleappproject.Model.RealDbdatabase;

import com.nic.simpleappproject.Uttile;
import com.nic.simpleappproject.databinding.FragmentAddDataBinding;

import java.util.Objects;

import io.realm.Realm;

public class AddDataFragment extends Fragment {

    private AddDataViewModel mViewModel;
    private FragmentAddDataBinding addDataBinding;
    private LatLng latLng;
private int ids =0;
    public AddDataFragment(LatLng latloglocation) {
        this.latLng=latloglocation;
    }

    public FragmentAddDataBinding getAddDataBinding() {
        return addDataBinding;
    }

private Realm realm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        addDataBinding = FragmentAddDataBinding.inflate(getLayoutInflater(), container, false);
        return addDataBinding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddDataViewModel.class);
             realm=Realm.getDefaultInstance();
// buttion for upload data in data base
        addDataBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (validation()) {

                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                            try {
                                // get current id from data base for next id
                                String number    = String.valueOf(Integer.parseInt(String.valueOf(realm.where(RealDbdatabase.class).max("id"))));

                                Log.e("TAG", "execute: "+number );
                                if(number.equalsIgnoreCase("null")){
                                   ids=0 ;
                                }else {
                                    ids = Integer.parseInt(number)+1;
                                }


                            } catch (NumberFormatException ex){
                                Log.e("TAG", "execute: "+ex );
                            }
// create object database model class
                            RealDbdatabase realDbdatabase = new RealDbdatabase();

                            realDbdatabase.setId(ids);
                            realDbdatabase.setName(addDataBinding.etName.getText().toString());
                            realDbdatabase.setPhonnumber(addDataBinding.etPhonenumber.getText().toString());
                            realDbdatabase.setTaskname(addDataBinding.etTitle.getText().toString());
                            realDbdatabase.setTaskDescreption(addDataBinding.etDescreptiuon.getText().toString());
                            realDbdatabase.setLat(latLng.latitude);
                            realDbdatabase.setLon(latLng.longitude);

                            // insert data in database
                            realm.insert(realDbdatabase);


                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {

                            addDataBinding.etName.getText().clear();
                            addDataBinding.etPhonenumber.getText().clear();
                            addDataBinding.etTitle.getText().clear();
                            addDataBinding.etDescreptiuon.getText().clear();

                            Uttile.Showmassage(requireContext(), addDataBinding.btnAdd, "Data Inserted Successfully");

                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            Log.e("TAG", "onError: "+error.getMessage() );
                            Uttile.Showmassage(getContext(), addDataBinding.btnAdd, ""+error.getMessage().toString());

                        }
                    });
                }
            }
        });
    }
// validation for edit text box to check it is filed or not
    private boolean validation() {
        if (TextUtils.isEmpty(addDataBinding.etName.toString())) {
            Uttile.Showmassage(requireActivity().getBaseContext(), addDataBinding.etName, "Please Enter Name");
            return false;
        } else if (TextUtils.isEmpty(addDataBinding.etPhonenumber.toString())) {
            Uttile.Showmassage(requireActivity().getBaseContext(), addDataBinding.etPhonenumber, "Please Enter Phone Number");
            return false;
        } else if (TextUtils.isEmpty(addDataBinding.etTitle.toString())) {
            Uttile.Showmassage(requireActivity().getBaseContext(), addDataBinding.etTitle, "Please Enter Title of Task");
            return false;
        } else if (TextUtils.isEmpty(addDataBinding.etDescreptiuon.toString())) {
            Uttile.Showmassage(requireActivity().getBaseContext(), addDataBinding.etDescreptiuon, "Please Enter Description of Task");
            return false;
        }
        return true;
    }

}