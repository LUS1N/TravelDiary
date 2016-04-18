package Model;

import java.util.Date;

public class Note
{
    private String title;
    private String description;
    private String address;
    private Date dateOfVisit;
    private String imageURL;
    private boolean visitAgain;

    public Note(String title, String description, String address, Date dateOfVisit, String imageURL, boolean visitAgain)
    {
        this.title = title;
        this.description = description;
        this.address = address;
        this.dateOfVisit = dateOfVisit;
        this.imageURL = imageURL;
        this.visitAgain = visitAgain;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getImageURL()
    {
        return imageURL;
    }

    public void setImageURL(String imageURL)
    {
        this.imageURL = imageURL;
    }

    public Date getDateOfVisit()
    {
        return dateOfVisit;
    }

    public void setDateOfVisit(Date dateOfVisit)
    {
        this.dateOfVisit = dateOfVisit;
    }

    public boolean isVisitAgain()
    {
        return visitAgain;
    }

    public void setVisitAgain(boolean visitAgain)
    {
        this.visitAgain = visitAgain;
    }
}
