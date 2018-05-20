package com.jeevasamruddhi.telangana.nlms.android.model;

import java.util.ArrayList;

/**
 * Created by jayalakshmi on 4/2/2018.
 */

public class MessageDTO extends AbstractBaseDTO {
    ArrayList<BasicCard> content;
  //  String content;



    public void setBasicCards(ArrayList<BasicCard> messages) {
        this.content = messages;
    }


    public ArrayList<BasicCard> getBasicCards() {
        return content;
    }
}
