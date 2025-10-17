package tglive.fqrs.app.ui;

import static tglive.fqrs.app.AndroidUtilities.dp;
import static tglive.fqrs.app.LocaleController.getString;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import tglive.fqrs.app.PhoneFormat.PhoneFormat;
import tglive.fqrs.app.AndroidUtilities;
import tglive.fqrs.app.BillingController;
import tglive.fqrs.app.Emoji;
import tglive.fqrs.app.LocaleController;
import tglive.fqrs.app.MessagesController;
import tglive.fqrs.app.R;
import tglive.fqrs.app.UserConfig;
import tglive.fqrs.app.UserObject;
import tglive.fqrs.app.browser.Browser;
import tglive.fqrs.app.tgnet.TLObject;
import tglive.fqrs.app.tgnet.TLRPC;
import tglive.fqrs.app.tgnet.tl.TL_fragment;
import tglive.fqrs.app.ui.ActionBar.BottomSheet;
import tglive.fqrs.app.ui.ActionBar.Theme;
import tglive.fqrs.app.ui.Components.AvatarDrawable;
import tglive.fqrs.app.ui.Components.BackupImageView;
import tglive.fqrs.app.ui.Components.BulletinFactory;
import tglive.fqrs.app.ui.Components.ColoredImageSpan;
import tglive.fqrs.app.ui.Components.LayoutHelper;
import tglive.fqrs.app.ui.Components.LinkSpanDrawable;
import tglive.fqrs.app.ui.Components.RLottieImageView;
import tglive.fqrs.app.ui.Stories.recorder.ButtonWithCounterView;

public class FragmentUsernameBottomSheet {

    public static final int TYPE_USERNAME = 0;
    public static final int TYPE_PHONE = 1;

    public static void open(
        Context context,
        int type,
        String name,
        TLObject owner,
        TL_fragment.TL_collectibleInfo info,
        Theme.ResourcesProvider resourcesProvider
    ) {
        BottomSheet sheet = new BottomSheet(context, false, resourcesProvider);
        sheet.fixNavigationBar(Theme.getColor(Theme.key_dialogBackground, resourcesProvider));

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dp(16), 0, dp(16), 0);

        FrameLayout imageContainerView = new FrameLayout(context);
        imageContainerView.setBackground(Theme.createCircleDrawable(dp(80), Theme.getColor(Theme.key_featuredStickers_addButton, resourcesProvider)));
        layout.addView(imageContainerView, LayoutHelper.createLinear(80, 80, Gravity.CENTER_HORIZONTAL, 0, 16, 0, 16));

        RLottieImageView imageView = new RLottieImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        int sz = type == TYPE_USERNAME ? 70 : 78;
        imageView.setAnimation(type == TYPE_USERNAME ? R.raw.fragment_username : R.raw.fragment, sz, sz);
        imageView.playAnimation();
        imageView.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN));
        if (type == TYPE_USERNAME) {
            imageView.setScaleX(0.86f);
            imageView.setScaleY(0.86f);
        } else {
            imageView.setTranslationY(dp(2));
        }
        imageContainerView.addView(imageView, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, Gravity.CENTER));

        final String ownerName;
        if (owner instanceof TLRPC.User) {
            ownerName = UserObject.getUserName((TLRPC.User) owner);
        } else if (owner instanceof TLRPC.Chat) {
            ownerName = ((TLRPC.Chat) owner).title;
        } else {
            ownerName = "";
        }

        final String money = BillingController.getInstance().formatCurrency(info.amount, info.currency);
        final String crypto_money = BillingController.getInstance().formatCurrency(info.crypto_amount, info.crypto_currency);

        String title;
        String message;
        String link;
        if (type == TYPE_USERNAME) {
            title = LocaleController.formatString(R.string.FragmentUsernameTitle, "@" + name);
            message = LocaleController.formatString(R.string.FragmentUsernameMessage, LocaleController.formatShortDateTime(info.purchase_date), crypto_money, TextUtils.isEmpty(money) ? "" : "(" + money + ")");
            link = MessagesController.getInstance(UserConfig.selectedAccount).linkPrefix + "/" + name;
        } else if (type == TYPE_PHONE) {
            title = LocaleController.formatString(R.string.FragmentPhoneTitle, PhoneFormat.getInstance().format("+" + name));
            message = LocaleController.formatString(R.string.FragmentPhoneMessage, LocaleController.formatShortDateTime(info.purchase_date), crypto_money, TextUtils.isEmpty(money) ? "" : "(" + money + ")");
            link = PhoneFormat.getInstance().format("+" + name);
        } else {
            return;
        }

        Runnable copy = link != null ? () -> {
            AndroidUtilities.addToClipboard(link);
            if (type == TYPE_PHONE) {
                BulletinFactory.of(sheet.getContainer(), resourcesProvider).createCopyBulletin(getString(R.string.PhoneCopied)).show();
            } else {
                BulletinFactory.of(sheet.getContainer(), resourcesProvider).createCopyLinkBulletin().show();
            }
        } : null;

        CharSequence titleSpanned = AndroidUtilities.replaceSingleTag(title, copy);

        final SpannableString tonIcon = new SpannableString("TON");
        ColoredImageSpan span = new ColoredImageSpan(R.drawable.mini_ton);
        span.setWidth(dp(13));
        tonIcon.setSpan(span, 0, tonIcon.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        CharSequence messageSpanned = AndroidUtilities.replaceCharSequence("TON", AndroidUtilities.replaceTags(message), tonIcon);

        TextView headerView = new LinkSpanDrawable.LinksTextView(context);
        headerView.setTypeface(AndroidUtilities.bold());
        headerView.setGravity(Gravity.CENTER);
        headerView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, resourcesProvider));
        headerView.setLinkTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText2, resourcesProvider));
        headerView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        headerView.setText(titleSpanned);
        layout.addView(headerView, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL, 42, 0, 42, 0));

        FrameLayout chipLayout = new FrameLayout(context);
        chipLayout.setBackground(Theme.createRoundRectDrawable(dp(28), dp(28), Theme.getColor(Theme.key_groupcreate_spanBackground, resourcesProvider)));

        BackupImageView chipAvatar = new BackupImageView(context);
        chipAvatar.setRoundRadius(dp(28));
        AvatarDrawable avatarDrawable = new AvatarDrawable();
        avatarDrawable.setInfo(owner);
        chipAvatar.setForUserOrChat(owner, avatarDrawable);
        chipLayout.addView(chipAvatar, LayoutHelper.createFrame(28, 28, Gravity.LEFT | Gravity.TOP));

        TextView chipText = new TextView(context);
        chipText.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, resourcesProvider));
        chipText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        chipText.setSingleLine();
        chipText.setText(Emoji.replaceEmoji(ownerName, chipText.getPaint().getFontMetricsInt(), false));
        chipLayout.addView(chipText, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_VERTICAL | Gravity.LEFT, 37, 0, 10, 0));

        layout.addView(chipLayout, LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT, 28, Gravity.CENTER_HORIZONTAL, 42, 10, 42, 18));

        TextView descriptionView = new TextView(context);
        descriptionView.setGravity(Gravity.CENTER);
        descriptionView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, resourcesProvider));
        descriptionView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        descriptionView.setText(messageSpanned);
        layout.addView(descriptionView, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL, 32, 0, 32, 19));

        ButtonWithCounterView button = new ButtonWithCounterView(context, resourcesProvider);
        button.setText(getString(R.string.FragmentUsernameOpen), false);
        button.setOnClickListener(v -> {
            Browser.openUrl(context, info.url);
        });
        layout.addView(button, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 48, 6, 0, 6, 0));

        if (copy != null) {
            ButtonWithCounterView button2 = new ButtonWithCounterView(context, false, resourcesProvider);
            button2.setText(getString(type == TYPE_USERNAME ? R.string.FragmentUsernameCopy : R.string.FragmentPhoneCopy), false);
            button2.setOnClickListener(v -> {
                copy.run();
                sheet.dismiss();
            });
            layout.addView(button2, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 48, 6, 6, 6, 0));
        }

        sheet.setCustomView(layout);
        sheet.show();
    }

}
