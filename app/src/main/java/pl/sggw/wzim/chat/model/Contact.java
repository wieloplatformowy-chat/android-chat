package pl.sggw.wzim.chat.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Michal on 2016-05-27.
 */
public class Contact implements ContactListItem, Parcelable{

    private Bitmap profilePicture;
    private String name;
    private boolean isAvailable;
    private boolean isNewMessage;

    public boolean isNewMessage() {
        return isNewMessage;
    }

    public void setNewMessage(boolean newMessage) {
        isNewMessage = newMessage;
    }

    private long id;
    private long conversationID; //conversation with logged user


    public long getConversationID() {
        return conversationID;
    }

    public void setConversationID(long conversationID) {
        this.conversationID = conversationID;
    }

    public Contact(Bitmap profilePicture, String name, boolean isAvailable) {
        this.profilePicture = profilePicture;
        this.name = name;
        this.isAvailable = isAvailable;
        isNewMessage = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Contact(Parcel parcel){
        name = parcel.readString();
        isAvailable = (parcel.readInt() == 1) ? true : false;
        id = parcel.readLong();
        profilePicture = null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /*
     * Temporarily avatar data is not going to be passed in a Parcel.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(isAvailable ? 1 : 0);
        dest.writeLong(id);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
