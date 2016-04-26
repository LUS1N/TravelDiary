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

    @Override
    public String toString()
    {
        return "Note{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", dateOfVisit=" + dateOfVisit +
                ", imageURL='" + imageURL + '\'' +
                ", visitAgain=" + visitAgain +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;

        Note note = (Note) o;

        if (isVisitAgain() != note.isVisitAgain()) return false;
        if (getTitle() != null ? !getTitle().equals(note.getTitle()) : note.getTitle() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(
                note.getDescription()) : note.getDescription() != null) return false;
        if (getAddress() != null ? !getAddress().equals(
                note.getAddress()) : note.getAddress() != null)
            return false;
        if (getDateOfVisit() != null ? !getDateOfVisit().equals(
                note.getDateOfVisit()) : note.getDateOfVisit() != null) return false;
        return getImageURL() != null ? getImageURL().equals(
                note.getImageURL()) : note.getImageURL() == null;

    }

    @Override
    public int hashCode()
    {
        int result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getDateOfVisit() != null ? getDateOfVisit().hashCode() : 0);
        result = 31 * result + (getImageURL() != null ? getImageURL().hashCode() : 0);
        result = 31 * result + (isVisitAgain() ? 1 : 0);
        return result;
    }
}
