package tglive.fqrs.app.ui;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import tglive.fqrs.app.ui.ActionBar.BaseFragment;
import tglive.fqrs.app.ui.Components.SizeNotifierFrameLayout;

public class EmptyBaseFragment extends BaseFragment {

    @Override
    public View createView(Context context) {
        return fragmentView = new SizeNotifierFrameLayout(context);
    }

}
