package com.technorabit.agritech.model;

import java.util.ArrayList;

/**
 * Created by raja on 02/06/18.
 */

public class CropsRes extends BaseRes {

    public ArrayList<Crop> Content;

    public static class Crop {
        public String cropId, cropName;
    }
}
