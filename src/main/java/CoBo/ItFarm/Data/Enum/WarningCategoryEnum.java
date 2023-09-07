package CoBo.ItFarm.Data.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WarningCategoryEnum {

    DUMMY(false, ""),
    DUMMY2(false, ""),
    TOO_HOT(true, "");

    private final boolean sendMail;

    private final String message;
}
