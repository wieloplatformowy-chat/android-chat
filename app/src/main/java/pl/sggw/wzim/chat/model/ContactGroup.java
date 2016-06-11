package pl.sggw.wzim.chat.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Michal on 11.06.2016.
 */
public class ContactGroup extends Contact {

    private ArrayList<Contact> participants;


    public ContactGroup(Bitmap profilePicture, String name, ArrayList<Contact> participants ,boolean isAvailable) {
        super(profilePicture, name, isAvailable);
        this.participants = participants;
    }

    public ArrayList<Contact> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Contact> participants) {
        this.participants = participants;
    }
}
