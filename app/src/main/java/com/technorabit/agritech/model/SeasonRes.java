package com.technorabit.agritech.model;

import java.util.ArrayList;

/**
 * Created by raja on 02/06/18.
 */

public class SeasonRes extends BaseRes {

    public ArrayList<Season> Content;

    public static class Season {
        public String seasonId, seasonName;
    }

}
