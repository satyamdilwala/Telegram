package org.webrtc;

import android.graphics.Bitmap;

/**
 * Stub implementation of GlGenericDrawer for compilation purposes.
 * This is a placeholder - VoIP functionality will not work properly.
 */
public class GlGenericDrawer {
    
    public interface TextureCallback {
        void onTextureReady(Bitmap bitmap, int rotation);
    }
}
