package com.javarush.test.level28.lesson15.big01.model;

import com.javarush.test.level28.lesson15.big01.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Алла on 04.01.2015.
 */
public class RabotaStrategy implements Strategy
{
    private static final String URL_FORMAT = "http://rabota.ua/jobsearch/vacancy_list?regionid=%d&keywords=%S&pg=%d";

    protected Document getDocument(String[] searchString, int page) throws IOException
    {
        int encodedSearchString = 0;
        String city = searchString[1];
        switch (city) {
            case "Киев":
                encodedSearchString = 1;
                break;
            case "Львов":
                encodedSearchString = 2;
                break;
            default:
                encodedSearchString = 1;
                break;
        }

        String vacancy = searchString[0];
        String url = String.format(URL_FORMAT, encodedSearchString, vacancy, page);

        String agent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0";
        String ref = "http://google.com.ua";
        Document doc = Jsoup.connect(url).userAgent(agent).referrer(ref).get();

        return doc;
    }


    @Override
    public List<Vacancy> getVacancies(String[] searchString)
    {
        try
        {
            List<Vacancy> vacancies = new ArrayList<>();

            for (int pageNumber = 1; ;pageNumber++)
            {
                Document doc = getDocument(searchString, pageNumber);
                //Get vacancies tags
                Elements elements = doc.getElementsByClass("v");
                //if no vacancies tags on page -> there are no more pages
                if (elements.size() == 0) {
                    break;
                }
                //parse and fill vacancies list
                for (int i = 0; i < elements.size(); i++)
                {
                    Element element = elements.get(i);
                    Vacancy vacancy = new Vacancy();
                    Element title = element.getElementsByClass("t").get(0);
                    vacancy.setTitle(title.text().trim());
                    vacancy.setUrl("http://rabota.ua" + title.attr("href"));
                    Element companyCitySalaryElement = element.getElementsByClass("s").get(0);
                    String[] companyCitySalaryString = companyCitySalaryElement.text().split("•");
                    vacancy.setCity(companyCitySalaryString[1].trim());
                    vacancy.setCompanyName(companyCitySalaryString[0].trim());
                    String salaryString = companyCitySalaryString.length <= 2 ? "" : companyCitySalaryString[2].trim();
                    vacancy.setSalary(salaryString);
                    vacancy.setSiteName("http://rabota.ua");
                    vacancies.add(vacancy);
                }
            }

            return vacancies;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();

    }
}
