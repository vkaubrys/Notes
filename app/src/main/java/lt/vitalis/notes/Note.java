package lt.vitalis.notes;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Note {

    private int id;
    private String title;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

    public Note(int id, String title, String description, LocalDateTime creationDate, LocalDateTime updateDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }


    public int
    public void setTitle(String title) {
        this.title = title;
        this.updateDate = LocalDateTime.now();
    }




    public Note(){



    }
    @Override
    public String toString(){
        return String.format(
                "Title: %s\nDescription:\n\t%s\n\t%s\n\t%s",
                this.title,
                this.description,
                this.creationDate,
                this.updateDate
        );
    }


}
