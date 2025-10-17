package org.webrtc;

import android.content.Context;

/**
 * Stub implementation of SurfaceTextureHelper for compilation purposes.
 * This is a placeholder - VoIP functionality will not work properly.
 */
public class SurfaceTextureHelper {
    
    public static SurfaceTextureHelper create(String threadName, EglBase.Context eglContext) {
        return new SurfaceTextureHelper();
    }
    
    public void dispose() {
        // Stub implementation
    }
}
