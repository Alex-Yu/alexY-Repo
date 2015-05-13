package com.javarush.test.level28.lesson15.big01.view;

import com.javarush.test.level28.lesson15.big01.Controller;
import com.javarush.test.level28.lesson15.big01.vo.Vacancy;

import java.util.List;

/**
 * Created by Алла on 31.12.2014.
 */
public interface View
{
    void update(List<Vacancy> vacancies);
    void setController(Controller controller);
}
