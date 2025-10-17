package tglive.fqrs.app.ui.Cells;

import static tglive.fqrs.app.AndroidUtilities.dp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import tglive.fqrs.app.LocaleController;
import tglive.fqrs.app.R;
import tglive.fqrs.app.ui.ActionBar.Theme;
import tglive.fqrs.app.ui.Components.AnimatedTextView;
import tglive.fqrs.app.ui.Components.CubicBezierInterpolator;
import tglive.fqrs.app.ui.Components.LayoutHelper;

@SuppressLint("ViewConstructor")
public class CollapseTextCell extends FrameLayout {

    public final AnimatedTextView textView;
    private View collapsedArrow;
    private Theme.ResourcesProvider resourcesProvider;

    @SuppressLint("UseCompatLoadingForDrawables")
    public CollapseTextCell(Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.resourcesProvider = resourcesProvider;

        textView = new AnimatedTextView(context);
        textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, resourcesProvider));
        textView.setTextSize(dp(14));
        textView.setGravity(LocaleController.isRTL ? Gravity.RIGHT : Gravity.LEFT);
        textView.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);
        textView.setOnWidthUpdatedListener(this::updateCollapseArrowTranslation);
        addView(textView, LayoutHelper.createFrameRelatively(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 21, 0, 38, 3));

        collapsedArrow = new View(context);
        Drawable drawable = getContext().getResources().getDrawable(R.drawable.arrow_more).mutate();
        drawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, resourcesProvider), PorterDuff.Mode.MULTIPLY));
        collapsedArrow.setBackground(drawable);
        addView(collapsedArrow, LayoutHelper.createFrameRelatively(14, 14, Gravity.START | Gravity.CENTER_VERTICAL, 21, 1, 0, 3));
    }

    public void set(CharSequence text, boolean collapsed) {
        textView.setText(text);
        collapsedArrow.animate().cancel();
        collapsedArrow.animate().rotation(collapsed ? 0 : 180).setDuration(340).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
    }

    public void setColor(int colorKey) {
        int color = Theme.getColor(colorKey, resourcesProvider);
        textView.setTextColor(color);
        collapsedArrow.getBackground().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(dp(46), MeasureSpec.EXACTLY));
        updateCollapseArrowTranslation();
    }

    private void updateCollapseArrowTranslation() {
        float textWidth = textView.getDrawable().getCurrentWidth();

        float translateX = textWidth + dp(1);
        if (LocaleController.isRTL) {
            collapsedArrow.setTranslationX(-translateX);
        } else {
            collapsedArrow.setTranslationX(translateX);
        }
    }
}
