package darkdata.web.api.datavariable;

import java.util.Collections;

/**
 * @author szednik
 */

public class DataVariableTestHarness {

    public static DataVariable createVariable_MYD08_D3_6_Cirrus_Reflectance_Mean() {
        return new DataVariable("MYD08_D3_6_Cirrus_Reflectance_Mean", "MYD08_D3","6","Cirrus_Reflectance_Mean", Collections.singletonList("ATMOSPHERE->ATMOSPHERIC RADIATION->REFLECTANCE"));
    }

    public static DataVariable createVariable_MYD08_D3_6_Cloud_Optical_Thickness_Liquid_Mean() {
        return new DataVariable("MYD08_D3_6_Cloud_Optical_Thickness_Liquid_Mean","MYD08_D3","6","Cloud_Optical_Thickness_Liquid_Mean",Collections.singletonList("ATMOSPHERE->CLOUDS->CLOUD LIQUIDWATER/ICE"));
    }
}
