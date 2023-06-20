package com.nic.simpleappproject.Model;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
// realm database created using RealmObject
public class RealDbdatabase extends RealmObject {

    @PrimaryKey
    int id=0;
    String name;
    String phonnumber;
    String Taskname;
    String TaskDescreption;
    double lat;
    double lon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonnumber() {
        return phonnumber;
    }

    public void setPhonnumber(String phonnumber) {
        this.phonnumber = phonnumber;
    }

    public String getTaskname() {
        return Taskname;
    }

    public void setTaskname(String taskname) {
        Taskname = taskname;
    }

    public String getTaskDescreption() {
        return TaskDescreption;
    }

    public void setTaskDescreption(String taskDescreption) {
        TaskDescreption = taskDescreption;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
