package CoBo.ItFarm.Data.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WarningCategoryEnum {

    WATER_LEVEL_TOO_HIGH(false),
    WATER_LEVEL_TOO_HIGH_OVER(true),
    WATER_LEVEL_TOO_LOW(false),
    FIELD_TOO_HOT_ON(false),
    FIELD_TOO_HOT_OFF(false),
    FIELD_TOO_HOT_OVER(true),
    FIELD_TOO_COLD_ON(false),
    FIELD_TOO_COLD_OFF(false),
    FIELD_TOO_COD_OVER(true),
    PH_TOO_HIGH(true),
    PH_TOO_LOW(true),
    EC_TOO_HIGH(true),
    EC_TOO_LOW(true),
    WATER_TOO_HOT(true),
    WATER_TOO_COLD(true)
    ;
    private final boolean sendMail;
}
