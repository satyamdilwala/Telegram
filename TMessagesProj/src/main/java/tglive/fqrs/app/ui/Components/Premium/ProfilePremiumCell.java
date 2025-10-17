package tglive.fqrs.app.ui.Components.Premium;

import static tglive.fqrs.app.AndroidUtilities.dp;

import android.content.Context;
import android.graphics.Canvas;

import tglive.fqrs.app.AndroidUtilities;
import tglive.fqrs.app.ui.ActionBar.Theme;
import tglive.fqrs.app.ui.Cells.TextCell;
import tglive.fqrs.app.ui.Stars.StarsReactionsSheet;

public class ProfilePremiumCell extends TextCell {

    private final StarsReactionsSheet.Particles particles = new StarsReactionsSheet.Particles(StarsReactionsSheet.Particles.TYPE_RADIAL, 30);
    private final int colorKey;

    public ProfilePremiumCell(Context context, int type, Theme.ResourcesProvider resourcesProvider) {
        super(context, resourcesProvider);
        colorKey = type == 1 ? Theme.key_starsGradient1 : Theme.key_premiumGradient2;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        float cx = imageView.getX() + imageView.getWidth() / 2f;
        float cy = imageView.getPaddingTop() + imageView.getY() + imageView.getHeight() / 2f - dp(3);
        AndroidUtilities.rectTmp.set(
            cx - dp(16), cy - dp(16),
            cx + dp(16), cy + dp(16)
        );
        particles.setBounds(AndroidUtilities.rectTmp);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        particles.process();
        particles.draw(canvas, Theme.getColor(colorKey));
        invalidate();
        super.dispatchDraw(canvas);
    }
}
