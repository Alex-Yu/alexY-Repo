package com.javarush.test.level28.lesson15.big01.view;

import com.javarush.test.level28.lesson15.big01.ConsoleHelper;
import com.javarush.test.level28.lesson15.big01.Controller;
import com.javarush.test.level28.lesson15.big01.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Алла on 31.12.2014.
 */
public class HtmlView implements View
{
    private Controller controller;
    private final String filePath = "./src/" + (this.getClass().getPackage().getName()).replaceAll("\\.", "/")
                                    + "/vacancies.html";

    public void searchStringSelectEmulationMethod()
    {
        ConsoleHelper.output("Enter the city to find in (in Russian): ");
        String city = ConsoleHelper.input();
        ConsoleHelper.output("Enter the vacancy title: ");
        String vacancy = ConsoleHelper.input();
        String[] searchString = {vacancy, city};
        controller.onSearchStringSelect(searchString);
    }

    @Override
    public void update(List<Vacancy> vacancies)
    {
        try
        {
            String html = getUpdatedFileContent(vacancies);
            updateFile(html);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private String getUpdatedFileContent(List<Vacancy> vacancies)  {
        try
        {
            Document doc = getDocument();
            Element elementTemplateClass = doc.getElementsByClass("template").get(0);
            Element template = elementTemplateClass.clone();
            template.removeAttr("style");
            template.removeClass("template");

            //removing all tags <tr> with class="vacancy"
            doc.getElementsByAttributeValue("class", "vacancy").remove();

            //vacancies to html
            int pos = 1;
            for (Vacancy vacancy : vacancies)
            {
                Element element = template.clone();
                element.getElementsByClass("pos").first().text(String.valueOf(pos++));
                element.getElementsByClass("city").first().text(vacancy.getCity());
                element.getElementsByClass("companyName").first().text(vacancy.getCompanyName());
                element.getElementsByClass("salary").first().text(vacancy.getSalary());
                Element title = element.getElementsByTag("a").first();
                title.text(vacancy.getTitle());
                title.attr("href", vacancy.getUrl());
                //vacancy to doc
                elementTemplateClass.before(element);

            }

            return doc.html();
        } catch (IOException e)
        {
            e.printStackTrace();
            return "Some exception occurred";
        }
    }

    protected Document getDocument() throws IOException {
        File htmlFile = new File(filePath);
        Document doc = Jsoup.parse(htmlFile, "UTF-8");

        return doc;
    }

    private void updateFile(String html) throws IOException
    {
        FileWriter writer = new FileWriter(filePath);
        writer.write(html);
        writer.close();
    }

    @Override
    public void setController(Controller controller)
    {
        this.controller = controller;
    }
}
