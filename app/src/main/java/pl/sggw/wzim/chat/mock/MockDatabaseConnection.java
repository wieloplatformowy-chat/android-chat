package pl.sggw.wzim.chat.mock;

import java.util.ArrayList;

import pl.sggw.wzim.chat.model.Message;

public class MockDatabaseConnection {
    public static ArrayList<Message> getConversation(){
        ArrayList<Message> mMessageList = new ArrayList<>();
        mMessageList.add(new Message("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec placerat purus at ex auctor aliquam.","18:26","Lorem ipsum"));
        mMessageList.add(new Message("Mauris condimentum arcu eu ipsum pellentesque, consequat consectetur ligula pharetra. Nam ultricies a nibh sed faucibus.","18:27","john smith"));
        mMessageList.add(new Message("Aenean nec nunc nulla. Ut quis fringilla diam.","18:29","john smith"));

        return mMessageList;
    }
}
