package com.technorabit.agritech.model;

import java.util.ArrayList;

/**
 * Created by raja on 02/06/18.
 */

public class StatesRes extends BaseRes {


    public ArrayList<State> Content;

    public static class State {
        public String stateName;
        public String stateId;
    }
}
