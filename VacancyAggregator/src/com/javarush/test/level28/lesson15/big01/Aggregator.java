package com.javarush.test.level28.lesson15.big01;

import com.javarush.test.level28.lesson15.big01.model.*;
import com.javarush.test.level28.lesson15.big01.view.HtmlView;


/**
 * Created by Алла on 28.12.2014.
 */
public class Aggregator
{
    public static void main(String[] args)
    {
        Provider HHProvider = new Provider(new HHStrategy());
        Provider rabotaProvider = new Provider(new RabotaStrategy());

        HtmlView view = new HtmlView();
        Model model = new Model(view, new Provider[]{HHProvider, rabotaProvider});
        Controller controller = new Controller(model);
        view.setController(controller);
        view.searchStringSelectEmulationMethod();

        ConsoleHelper.close();

    }
}
