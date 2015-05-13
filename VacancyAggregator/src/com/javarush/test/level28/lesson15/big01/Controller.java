package com.javarush.test.level28.lesson15.big01;

import com.javarush.test.level28.lesson15.big01.model.Model;


/**
 * Created by Алла on 28.12.2014.
 */
public class Controller
{
    private Model model;

    public Controller(Model model) {
        if (model == null)
            throw new IllegalArgumentException();
        this.model = model;
    }

    public void onSearchStringSelect(String[] searchString) {
        model.selectSearchString(searchString);
    }


}
