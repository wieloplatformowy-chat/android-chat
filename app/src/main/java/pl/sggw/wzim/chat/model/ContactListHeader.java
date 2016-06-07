package pl.sggw.wzim.chat.model;

public class ContactListHeader implements ContactListItem {

    private String text;

    public ContactListHeader(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

    @Override
    public boolean isSectionHeader() {
        return true;
    }
}
