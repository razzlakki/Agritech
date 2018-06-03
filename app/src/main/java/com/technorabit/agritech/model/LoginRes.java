package com.technorabit.agritech.model;

import java.util.ArrayList;

/**
 * Created by raja on 02/06/18.
 */

public class LoginRes extends BaseRes{

    public ArrayList<LoginData> Content;

    public static class LoginData {
        public String displayName, mobileNumber, userId, ownerId;
    }


}
