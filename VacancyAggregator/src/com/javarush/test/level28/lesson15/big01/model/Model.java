package com.javarush.test.level28.lesson15.big01.model;

import com.javarush.test.level28.lesson15.big01.view.View;
import com.javarush.test.level28.lesson15.big01.vo.Vacancy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Алла on 31.12.2014.
 */
public class Model
{
    private View view;
    private Provider[] providers;

    public Model(View view, Provider[] providers)
    {
        if (view == null || providers == null || providers.length == 0) {
            throw new IllegalArgumentException();
        }
        this.view = view;
        this.providers = providers;
    }

    public void selectSearchString(String[] searchString) {
        List<Vacancy> allVacancies = new ArrayList<>();
        for (Provider provider : providers)
        {
            allVacancies.addAll(provider.getJavaVacancies(searchString));
        }
        removeDuplicates(allVacancies);
        Collections.sort(allVacancies);
        view.update(allVacancies);
    }

    private void removeDuplicates(List<Vacancy> vacancies){
        for (int i = 0; i < vacancies.size(); i++) {
            Vacancy original = vacancies.get(i);
            for (int j = i + 1; j < vacancies.size(); j++) {
                if (original.equals(vacancies.get(j)))
                {
                    vacancies.remove(j);
                    j--;
                }
            }
        }
    }
}
