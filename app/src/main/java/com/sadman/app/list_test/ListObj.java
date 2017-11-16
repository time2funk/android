package com.sadman.app.list_test;

/**
 * Created by root on 11/1/17.
 */

public class ListObj {
    String name;
    String position;
    int salary;
    int id;

    ListObj(String name, String position, int salary, int id){
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.id = id;
    }
    public int getId(){
        return id;
    }
}
