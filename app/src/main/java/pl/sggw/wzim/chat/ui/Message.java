package pl.sggw.wzim.chat.ui;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Michal on 2016-05-18.
 */
public class Message implements Parcelable {
    private String messageContent;
    private String timestamp;
    private String sender;

    public Message(String messageContent, String timestamp, String sender) {
        this.messageContent = messageContent;
        this.timestamp = timestamp;
        this.sender = sender;
    }

    public Message(Parcel parcel){
        String[] data = new String[3];

        parcel.readStringArray(data);

        messageContent = data[0];
        timestamp = data[1];
        sender = data[2];
    }

    public String getSender() {
        return sender;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getTimestamp() {
        return timestamp;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{messageContent,timestamp,sender});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
