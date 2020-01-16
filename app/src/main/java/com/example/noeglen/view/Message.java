package com.example.noeglen.view;

class Message {
    private String text; // message body
    private ChatActivity.MemberData memberData; // data of the user that sent this message
    private boolean belongsToCurrentUser; // is this message sent by us?

    public Message(String text, ChatActivity.MemberData memberData, boolean belongsToCurrentUser) {
        this.text = text;
        this.memberData = memberData;
        this.belongsToCurrentUser = belongsToCurrentUser;
    }

    public String getText() {
        return text;
    }

    public ChatActivity.MemberData getMemberData() {
        return memberData;
    }

    public boolean isBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }
}
