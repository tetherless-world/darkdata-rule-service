package darkdata.web.api.datavariable;

/**
 * @author szednik
 */

public class DataVariableTestHarness {

    public static DataVariable createVariable_MYD08_D3_51_Cirrus_Reflectance_Mean() {
        return new DataVariable("MYD08_D3","51","Cirrus_Reflectance_Mean","ATMOSPHERE->ATMOSPHERIC RADIATION->REFLECTANCE");
    }

    public static DataVariable createVariable_MYD08_D3_51_Cloud_Optical_Thickness_Liquid_Mean() {
        return new DataVariable("MYD08_D3","51","Cloud_Optical_Thickness_Liquid_Mean","ATMOSPHERE->CLOUDS->CLOUD LIQUIDWATER/ICE");
    }
}
