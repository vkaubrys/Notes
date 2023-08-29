package lt.vitalis.notes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Note {

    private int id;
    private String title;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private final DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Note(int id,
                String title,
                String description
    ) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }


    //    public int
    public void setTitle(String title) {
        this.title = title;
        this.updateDate = LocalDateTime.now();
    }


    public Note() {


    }

    @Override
    public String toString() {
        return String.format(
                "Title: %s\nDescription:\n\t%s\n\t%s\n\t%s",
                this.title,
                this.description,
                this.creationDate,
                this.updateDate
        );
    }

}
