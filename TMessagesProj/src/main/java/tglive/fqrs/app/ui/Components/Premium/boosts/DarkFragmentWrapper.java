package tglive.fqrs.app.ui.Components.Premium.boosts;

import android.app.Activity;

import tglive.fqrs.app.ui.ActionBar.BaseFragment;
import tglive.fqrs.app.ui.ActionBar.Theme;
import tglive.fqrs.app.ui.Stories.DarkThemeResourceProvider;
import tglive.fqrs.app.ui.WrappedResourceProvider;

public class DarkFragmentWrapper extends BaseFragment {

    private final BaseFragment parentFragment;

    DarkFragmentWrapper(BaseFragment parentFragment) {
        this.parentFragment = parentFragment;
    }

    @Override
    public boolean isLightStatusBar() {
        return false;
    }

    @Override
    public Activity getParentActivity() {
        return parentFragment.getParentActivity();
    }

    @Override
    public Theme.ResourcesProvider getResourceProvider() {
        return new WrappedResourceProvider(new DarkThemeResourceProvider());
    }

    @Override
    public boolean presentFragment(BaseFragment fragment) {
        return false;
    }
}
