package pl.skorpjdk.walletproject.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import pl.skorpjdk.walletproject.cost.Cost;

import javax.persistence.*;

//Model bazy danych do zdjęć
@Entity
@NoArgsConstructor
@Getter
@Setter
public class FileDB {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private String type;
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] data;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "file_cost",
            joinColumns = @JoinColumn(name = "file_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "cost_id", referencedColumnName = "id")
    )
    private Cost cost;

    public FileDB(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public FileDB(String name, String type, byte[] data, Cost cost) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.cost = cost;
    }
}
