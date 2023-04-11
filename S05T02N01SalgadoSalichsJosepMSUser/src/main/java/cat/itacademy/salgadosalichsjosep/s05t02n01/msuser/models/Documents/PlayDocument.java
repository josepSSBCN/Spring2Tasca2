package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.models.Documents;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Plays")
public class PlayDocument {
    //region ATTRIBUTES
    @Getter
    @Id
    @Setter
    private String id;

    @Getter
    @Setter
    private int userId;

    @Getter
    @Setter
    private int playNum;

    @Getter
    @Setter
    private byte dice1Value;

    @Getter
    @Setter
    private byte dice2Value;

    //endregion ATTRIBUTES

}
