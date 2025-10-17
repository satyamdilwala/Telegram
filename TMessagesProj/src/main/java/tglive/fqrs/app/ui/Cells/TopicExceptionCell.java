package tglive.fqrs.app.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import tglive.fqrs.app.AndroidUtilities;
import tglive.fqrs.app.MessagesController;
import tglive.fqrs.app.UserConfig;
import tglive.fqrs.app.tgnet.TLRPC;
import tglive.fqrs.app.ui.ActionBar.Theme;
import tglive.fqrs.app.ui.Components.BackupImageView;
import tglive.fqrs.app.ui.Components.Forum.ForumUtilities;
import tglive.fqrs.app.ui.Components.LayoutHelper;

public class TopicExceptionCell extends FrameLayout {

    public boolean drawDivider;
    BackupImageView backupImageView;
    TextView title;
    TextView subtitle;

    public TopicExceptionCell(Context context) {
        super(context);
        backupImageView = new BackupImageView(context);
        addView(backupImageView, LayoutHelper.createFrame(30, 30, Gravity.CENTER_VERTICAL, 20, 0, 0, 0));

        title = new TextView(context);
        title.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        title.setTypeface(AndroidUtilities.bold());
        title.setMaxLines(1);
        addView(title, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 72, 8, 12, 0));


        subtitle = new TextView(context);
        subtitle.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        subtitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        addView(subtitle, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 72, 32, 12, 0));

    }

    public void setTopic(long dialogId, TLRPC.TL_forumTopic topic) {
        ForumUtilities.setTopicIcon(backupImageView, topic);
        if (backupImageView != null && backupImageView.getImageReceiver() != null && backupImageView.getImageReceiver().getDrawable() instanceof ForumUtilities.GeneralTopicDrawable) {
            ((ForumUtilities.GeneralTopicDrawable) backupImageView.getImageReceiver().getDrawable()).setColor(Theme.getColor(Theme.key_chats_archiveBackground));
        }
        title.setText(topic.title);
        subtitle.setText(MessagesController.getInstance(UserConfig.selectedAccount).getMutedString(dialogId, topic.id));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(56), MeasureSpec.EXACTLY));
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (drawDivider) {
            canvas.drawLine(AndroidUtilities.dp(72), getMeasuredHeight() - 1, getMeasuredWidth(), getMeasuredHeight() - 1, Theme.dividerPaint);
        }
    }
}
