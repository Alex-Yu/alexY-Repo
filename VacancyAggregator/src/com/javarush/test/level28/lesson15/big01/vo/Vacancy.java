package com.javarush.test.level28.lesson15.big01.vo;

/**
 * Created by Алла on 28.12.2014.
 */
public class Vacancy implements Comparable<Vacancy>
{
    private String title;
    private String salary;
    private String city;
    private String companyName;
    private String siteName;
    private String url;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSalary()
    {
        return salary;
    }

    public void setSalary(String salary)
    {
        this.salary = salary;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public String getSiteName()
    {
        return siteName;
    }

    public void setSiteName(String siteName)
    {
        this.siteName = siteName;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vacancy vacancy = (Vacancy) o;

        if (!city.equals(vacancy.city)) return false;
        if (!companyName.equals(vacancy.companyName)) return false;
        if (salary != null ? !salary.equals(vacancy.salary) : vacancy.salary != null) return false;
        //if (!siteName.equals(vacancy.siteName)) return false;
        if (!title.equals(vacancy.title)) return false;
        //if (!url.equals(vacancy.url)) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = title.hashCode();
        result = 31 * result + (salary != null ? salary.hashCode() : 0);
        result = 31 * result + city.hashCode();
        result = 31 * result + companyName.hashCode();
        //result = 31 * result + siteName.hashCode();
        //result = 31 * result + url.hashCode();
        return result;
    }

    @Override
    public int compareTo(Vacancy o)
    {
        return companyName.compareTo(o.companyName);
    }
}
