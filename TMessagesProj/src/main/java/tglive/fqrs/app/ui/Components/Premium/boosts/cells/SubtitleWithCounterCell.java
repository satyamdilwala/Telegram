package tglive.fqrs.app.ui.Components.Premium.boosts.cells;

import static tglive.fqrs.app.AndroidUtilities.dp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;

import androidx.annotation.NonNull;

import tglive.fqrs.app.AndroidUtilities;
import tglive.fqrs.app.LocaleController;
import tglive.fqrs.app.ui.ActionBar.Theme;
import tglive.fqrs.app.ui.Components.AnimatedTextView;
import tglive.fqrs.app.ui.Components.CubicBezierInterpolator;
import tglive.fqrs.app.ui.Components.LayoutHelper;

@SuppressLint("ViewConstructor")
public class SubtitleWithCounterCell extends tglive.fqrs.app.ui.Cells.HeaderCell {

    private final AnimatedTextView counterTextView;

    public SubtitleWithCounterCell(@NonNull Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context, resourcesProvider);

        counterTextView = new AnimatedTextView(context, true, true, true);
        counterTextView.setAnimationProperties(.45f, 0, 240, CubicBezierInterpolator.EASE_OUT_QUINT);
        counterTextView.setGravity(LocaleController.isRTL ? Gravity.LEFT : Gravity.RIGHT);
        counterTextView.setTextSize(dp(15));
        counterTextView.setTypeface(AndroidUtilities.bold());
        counterTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueHeader, resourcesProvider));
        addView(counterTextView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, 24, (LocaleController.isRTL ? Gravity.LEFT : Gravity.RIGHT) | Gravity.BOTTOM, 24, 0, 24, 0));
        setBackgroundColor(Theme.getColor(Theme.key_dialogBackground, resourcesProvider));
    }

    public void updateCounter(boolean animated, int count) {
        CharSequence text = count <= 0 ? "" : LocaleController.formatPluralString("BoostingBoostsCountTitle", count, count);
        counterTextView.cancelAnimation();
        counterTextView.setText(text, animated);
    }
}
