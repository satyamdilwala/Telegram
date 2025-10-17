package tglive.fqrs.app.ui.bots;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import androidx.core.graphics.ColorUtils;

import tglive.fqrs.app.LocaleController;
import tglive.fqrs.app.NotificationCenter;
import tglive.fqrs.app.R;
import tglive.fqrs.app.tgnet.tl.TL_payments;
import tglive.fqrs.app.ui.ActionBar.ActionBar;
import tglive.fqrs.app.ui.ActionBar.BackDrawable;
import tglive.fqrs.app.ui.ActionBar.BaseFragment;
import tglive.fqrs.app.ui.ActionBar.Theme;
import tglive.fqrs.app.ui.Components.FlickerLoadingView;
import tglive.fqrs.app.ui.Components.LayoutHelper;
import tglive.fqrs.app.ui.Components.SizeNotifierFrameLayout;
import tglive.fqrs.app.ui.Components.UItem;
import tglive.fqrs.app.ui.Components.UniversalAdapter;
import tglive.fqrs.app.ui.Components.UniversalRecyclerView;
import tglive.fqrs.app.ui.Stars.BotStarsController;

import java.util.ArrayList;

public class SuggestedAffiliateProgramsFragment extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {

    private final long dialogId;

    public SuggestedAffiliateProgramsFragment(long dialogId) {
        this.dialogId = dialogId;
    }

    @Override
    public boolean onFragmentCreate() {
        NotificationCenter.getInstance(currentAccount).addObserver(this, NotificationCenter.channelSuggestedBotsUpdate);
        return super.onFragmentCreate();
    }

    @Override
    public void onFragmentDestroy() {
        NotificationCenter.getInstance(currentAccount).removeObserver(this, NotificationCenter.channelSuggestedBotsUpdate);
        super.onFragmentDestroy();
    }

    private BackDrawable backDrawable;
    private UniversalRecyclerView listView;

    @Override
    public View createView(Context context) {
        actionBar.setBackButtonDrawable(backDrawable = new BackDrawable(false));
        backDrawable.setAnimationTime(240);
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1) {
                    finishFragment();
                }
            }
        });
        actionBar.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        actionBar.setItemsColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText), false);
        actionBar.setItemsColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText), true);
        actionBar.setItemsBackgroundColor(Theme.getColor(Theme.key_actionBarActionModeDefaultSelector), false);
        actionBar.setTitleColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        actionBar.setTitle(LocaleController.getString(R.string.ChannelAffiliatePrograms));

        final SizeNotifierFrameLayout contentView = new SizeNotifierFrameLayout(context);
        contentView.addView(
            listView = new UniversalRecyclerView(this, this::fillItems, this::onItemClick, null),
            LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, Gravity.FILL)
        );
        fragmentView = contentView;

        return fragmentView;
    }

    public void fillItems(ArrayList<UItem> items, UniversalAdapter adapter) {
        BotStarsController.ChannelSuggestedBots suggestedBots = BotStarsController.getInstance(currentAccount).getChannelSuggestedBots(dialogId);
        for (int i = 0; i < suggestedBots.bots.size(); ++i) {
            items.add(ChannelAffiliateProgramsFragment.BotCell.Factory.as(suggestedBots.bots.get(i), false));
        }
        if (suggestedBots.isLoading()) {
            items.add(UItem.asFlicker(FlickerLoadingView.PROFILE_SEARCH_CELL));
            items.add(UItem.asFlicker(FlickerLoadingView.PROFILE_SEARCH_CELL));
            items.add(UItem.asFlicker(FlickerLoadingView.PROFILE_SEARCH_CELL));
        }
    }

    public void onItemClick(UItem item, View view, int position, float x, float y) {
        if (item.object instanceof TL_payments.starRefProgram) {
            TL_payments.starRefProgram bot = (TL_payments.starRefProgram) item.object;
            ChannelAffiliateProgramsFragment.showConnectAffiliateAlert(getContext(), currentAccount, bot, dialogId, resourceProvider, false);
        }
    }

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
        if (id == NotificationCenter.channelSuggestedBotsUpdate) {
            Long did = (Long) args[0];
            if (did == dialogId) {
                if (listView != null && listView.getAdapter() instanceof UniversalAdapter) {
                    ((UniversalAdapter) listView.getAdapter()).update(true);
                }
            }
        }
    }

    @Override
    public boolean isLightStatusBar() {
        if (getLastStoryViewer() != null && getLastStoryViewer().isShown()) {
            return false;
        }
        int color = Theme.getColor(Theme.key_windowBackgroundWhite);
        if (actionBar.isActionModeShowed()) {
            color = Theme.getColor(Theme.key_actionBarActionModeDefault);
        }
        return ColorUtils.calculateLuminance(color) > 0.7f;
    }
}
