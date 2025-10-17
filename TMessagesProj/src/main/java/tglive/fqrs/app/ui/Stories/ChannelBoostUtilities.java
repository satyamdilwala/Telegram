package tglive.fqrs.app.ui.Stories;

import android.text.TextUtils;

import tglive.fqrs.app.ChatObject;
import tglive.fqrs.app.MessagesController;
import tglive.fqrs.app.tgnet.TLRPC;

public class ChannelBoostUtilities {
    public static String createLink(int currentAccount, long dialogId) {
        TLRPC.Chat chat = MessagesController.getInstance(currentAccount).getChat(-dialogId);
        String username = ChatObject.getPublicUsername(chat);
        if (!TextUtils.isEmpty(username)) {
            return "https://t.me/boost/" + ChatObject.getPublicUsername(chat);
        } else {
            return "https://t.me/boost/?c=" + -dialogId;
        }
    }
}
