package com.javarush.test.level28.lesson15.big01.model;

import com.javarush.test.level28.lesson15.big01.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Алла on 28.12.2014.
 */
public class HHStrategy implements Strategy
{
    private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=%s+%s&page=%d";

    protected Document getDocument(String[] searchString, int page) throws IOException {
        String encodedVacancy = URLEncoder.encode(searchString[0], "UTF-8");
        String encodedCity = URLEncoder.encode(searchString[1], "UTF-8");
        String url = String.format(URL_FORMAT, encodedVacancy, encodedCity, page);

        String agent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0";
        String ref = "http://google.com.ua";
        Document doc = Jsoup.connect(url).userAgent(agent).referrer(ref).get();

        return doc;
    }

    private List<Vacancy> getVacanciesOnPage(Document doc)  {
        Elements elements = doc.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
        List<Vacancy> vacancies = new ArrayList<>();
        for(int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            Vacancy vacancy = new Vacancy();
            Element title = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").get(0);
            vacancy.setTitle(title.text().trim());
            vacancy.setUrl(title.attr("href"));
            Element address = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").get(0);
            vacancy.setCity(address.text().trim());
            Element company = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").get(0);
            vacancy.setCompanyName(company.text().trim());
            Elements salary = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation");
            String salaryString = salary.size() == 0 ? "" : salary.get(0).text().trim();
            vacancy.setSalary(salaryString);
            vacancy.setSiteName("http://hh.ua");
            vacancies.add(vacancy);
        }

        return vacancies;
    }
    @Override
    public List<Vacancy> getVacancies(String[] searchString)
    {
        try
        {
            Document firstPage = getDocument(searchString, 0);
            //getting number of pages
            int pagesQuantity = firstPage.getElementsByAttributeValue("data-qa", "pager-page").size();
            //fill Vacancies of first page
            List<Vacancy> vacancies = getVacanciesOnPage(firstPage);
            //fill Vacancies of all pages
            for (int i = 1; i < pagesQuantity; i++) {
                Document document = getDocument(searchString, i);
                vacancies.addAll(getVacanciesOnPage(document));
            }

            return vacancies;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();

    }
}
