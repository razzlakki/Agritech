package com.technorabit.agritech.model;

import java.util.ArrayList;

/**
 * Created by raja on 03/06/18.
 */

public class FarmerRes {

    public ArrayList<Farmer> Content;

    public static class Farmer {
        String firstName, middleName, lastName, phNumber, districtId, districtName,
                mandalId, mandalName, villageId, villageName,seasonId, seasonName, landArea, cropDetails, remarks;
    }
}
