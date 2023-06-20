package com.nic.simpleappproject.UI.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nic.simpleappproject.Adopter.TaskAdopterList;
import com.nic.simpleappproject.Model.RealDbdatabase;

import com.nic.simpleappproject.R;
import com.nic.simpleappproject.Uttile;
import com.nic.simpleappproject.databinding.FragmentViewAllDataBinding;

import io.realm.Realm;
import io.realm.RealmResults;

public class ViewAllDataFragment extends Fragment  {

    private ViewAllDataViewModel mViewModel;
    private FragmentViewAllDataBinding binding;
private RealmResults<RealDbdatabase> realDbdatabases;
private TaskAdopterList taskAdopterList;
private Realm realm;
    public FragmentViewAllDataBinding getBinding() {
        return binding;
    }

    public static ViewAllDataFragment newInstance() {
        return new ViewAllDataFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       binding= FragmentViewAllDataBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot() ;
    }



    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ViewAllDataViewModel.class);
        realm=Realm.getDefaultInstance();
        // get All data from database
        realDbdatabases=mViewModel.getAlldatafromdb(realm);

       if(realDbdatabases.size()>0){
           // add all data in list or Recycleview
           getBinding().recycleview.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext(),LinearLayoutManager.VERTICAL,false));
          getBinding().recycleview.setHasFixedSize(false);
          // recycleview attached with adopter
           taskAdopterList=new TaskAdopterList(getContext().getApplicationContext(),realDbdatabases, new TaskAdopterList.Itemonclick() {
               @Override
               public void OnDeleteItem(RealDbdatabase realDbdatabase) {
                   // delete item from data base
                   RealDbdatabase dbdatabase=realm.where(RealDbdatabase.class).equalTo("id",realDbdatabase.getId()).findFirst();
                   realm.beginTransaction();
                   dbdatabase.deleteFromRealm();
                   realm.commitTransaction();




               }

               @Override
               public void OnViewMapItem(RealDbdatabase realDbdatabase) {

               }
           });
           getBinding().recycleview.setAdapter(taskAdopterList);


       }else {
           Uttile.Showmassage(getActivity().getBaseContext(),view,"Data Not Found");
       }
    }
}