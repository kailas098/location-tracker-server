package com.kailasnath.locationtracker.Model;

import java.util.List;

public class Route {
    int id;
    int bus_id;
    List<BusLocation> path;


    public Route() {
    }
    public Route(int id,int bus_id, List<BusLocation> path) {
        this.id = id;
        this.path = path;
        this.bus_id = bus_id;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public List<BusLocation> getPath() {
        return path;
    }
    public void setPath(List<BusLocation> path) {
        this.path = path;
    }
    public int getBus_id() {
        return bus_id;
    }
    public void setBus_id(int bus_id) {
        this.bus_id = bus_id;
    }

    @Override
    public String toString() {
        return "Route [id=" + id + ", path=" + path + "]";
    }
}
