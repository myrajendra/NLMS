package com.jeevasamruddhi.telangana.nlms.android.model;

import java.util.ArrayList;

/**
 * Created by jayalakshmi on 4/2/2018.
 */

public class MessageDTOFILTER extends AbstractBaseDTO {
    ArrayList<BasicCard> messages;
    //  String content;



    public void setBasicCards(ArrayList<BasicCard> messages) {
        this.messages = messages;
    }


    public ArrayList<BasicCard> getBasicCards() {
        return messages;
    }
}

