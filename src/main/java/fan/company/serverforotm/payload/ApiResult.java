package fan.company.serverforotm.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResult {

    private String message;

    private boolean success;

    private Object object;

    public ApiResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
