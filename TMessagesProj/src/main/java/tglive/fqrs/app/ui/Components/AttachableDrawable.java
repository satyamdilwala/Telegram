package tglive.fqrs.app.ui.Components;

import android.view.View;

import tglive.fqrs.app.ImageReceiver;

public interface AttachableDrawable {
    void onAttachedToWindow(ImageReceiver parent);
    void onDetachedFromWindow(ImageReceiver parent);

    default void setParent(View view) {}
}
