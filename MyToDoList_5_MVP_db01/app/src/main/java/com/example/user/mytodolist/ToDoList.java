package com.example.user.mytodolist;

import java.util.ArrayList;

public class ToDoList {

    private ArrayList<ToDo> list;

    public ToDoList (){
        list = new ArrayList<ToDo>();
        list.add(new ToDo(1, "Go to Doctor"));
        list.add(new ToDo(2, "Pay the Rent"));
        list.add(new ToDo(3, "Shopping at Tesco"));
        list.add(new ToDo(4, "Xmas Presents"));
        list.add(new ToDo(5, "Go to Gym"));
        list.add(new ToDo(6, "Party Hard"));
        list.add(new ToDo(7, "Book Flights"));
        list.add(new ToDo(8, "Finish CodeClan Homework"));
        list.add(new ToDo(9, "Feed the Cat"));
        list.add(new ToDo(10, "Feed the Goldfishes"));
        list.add(new ToDo(11, "Feed the Goldfishes to the Cat"));
        list.add(new ToDo(12, "Pay Dance class"));
        list.add(new ToDo(13, "Pay codeClan fees"));
        list.add(new ToDo(14, "Meet up with George"));

    }

    public ArrayList<ToDo> getList() {

        return new ArrayList<ToDo>(list);
    }


}



