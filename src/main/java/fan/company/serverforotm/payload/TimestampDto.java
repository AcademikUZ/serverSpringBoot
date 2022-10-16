package fan.company.serverforotm.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
public class TimestampDto {

    @NotNull
    private Timestamp start;

    @NotNull
    private Timestamp end;

    private Long divisionId;


}
