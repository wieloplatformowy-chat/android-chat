package pl.sggw.wzim.chat.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Michal on 2016-05-27.
 */
public class Contact implements ContactListItem{

    private Bitmap profilePicture;
    private String name;
    private boolean isAvailable;

    public Contact(Bitmap profilePicture, String name, boolean isAvailable) {
        this.profilePicture = profilePicture;
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean isSectionHeader() {
        return false;
    }
}
