package tglive.fqrs.app.voip;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import tglive.fqrs.app.AccountInstance;
import tglive.fqrs.app.MessagesController;
import tglive.fqrs.app.NotificationCenter;
import tglive.fqrs.app.UserConfig;
import tglive.fqrs.app.tgnet.ConnectionsManager;
import tglive.fqrs.app.tgnet.TLRPC;
import tglive.fqrs.app.ui.Components.voip.VoIPHelper;

public final class VoIPPendingCall {

    public static VoIPPendingCall startOrSchedule(Activity activity, long userId, boolean video, AccountInstance accountInstance) {
        return new VoIPPendingCall(activity, userId, video, 1000, accountInstance);
    }

    private final NotificationCenter.NotificationCenterDelegate observer = (id, account, args) -> {
        if (id == NotificationCenter.didUpdateConnectionState) {
            onConnectionStateUpdated(false);
        }
    };

    private final Runnable releaseRunnable = () -> onConnectionStateUpdated(true);

    private final long userId;
    private final boolean video;
    private final Activity activity;

    private Handler handler;
    private NotificationCenter notificationCenter;
    private boolean released;
    private AccountInstance accountInstance;

    private VoIPPendingCall(Activity activity, long userId, boolean video, long expirationTime, AccountInstance accountInstance) {
        this.activity = activity;
        this.userId = userId;
        this.video = video;
        this.accountInstance = accountInstance;
        if (!onConnectionStateUpdated(false)) {
            notificationCenter = NotificationCenter.getInstance(UserConfig.selectedAccount);
            notificationCenter.addObserver(observer, NotificationCenter.didUpdateConnectionState);
            handler = new Handler(Looper.myLooper());
            handler.postDelayed(releaseRunnable, expirationTime);
        }
    }

    private boolean onConnectionStateUpdated(boolean force) {
        if (!released && (force || isConnected(accountInstance) || isAirplaneMode())) {
            final MessagesController messagesController = accountInstance.getMessagesController();
            final TLRPC.User user = messagesController.getUser(userId);
            if (user != null) {
                final TLRPC.UserFull userFull = messagesController.getUserFull(user.id);
                VoIPHelper.startCall(user, video, userFull != null && userFull.video_calls_available, activity, userFull, accountInstance);
            } else if (isAirplaneMode()) {
                VoIPHelper.startCall(null, video, false, activity, null, accountInstance);
            }
            release();
            return true;
        }
        return false;
    }

    private boolean isConnected(AccountInstance accountInstance) {
        return accountInstance.getConnectionsManager().getConnectionState() == ConnectionsManager.ConnectionStateConnected;
    }

    private boolean isAirplaneMode() {
        return Settings.System.getInt(activity.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }

    public void release() {
        if (!released) {
            if (notificationCenter != null) {
                notificationCenter.removeObserver(observer, NotificationCenter.didUpdateConnectionState);
            }
            if (handler != null) {
                handler.removeCallbacks(releaseRunnable);
            }
            released = true;
        }
    }
}
