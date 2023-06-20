package com.nic.simpleappproject.UI.Fragment;

import androidx.lifecycle.ViewModel;

import com.nic.simpleappproject.Model.RealDbdatabase;

import io.realm.Realm;
import io.realm.RealmResults;

public class ViewAllDataViewModel extends ViewModel {
// get data from database
    public RealmResults<RealDbdatabase> getAlldatafromdb(Realm realm){

        return realm.where(RealDbdatabase.class).findAll();
    }

}