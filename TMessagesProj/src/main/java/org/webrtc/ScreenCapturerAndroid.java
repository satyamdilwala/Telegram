package org.webrtc;

import android.content.Context;

/**
 * Stub implementation of ScreenCapturerAndroid for compilation purposes.
 * This is a placeholder - VoIP functionality will not work properly.
 */
public class ScreenCapturerAndroid implements VideoCapturer {
    
    public ScreenCapturerAndroid(Object mediaProjectionPermissionResultData, Object mediaProjectionCallback) {
        // Stub implementation
    }
    
    @Override
    public void initialize(SurfaceTextureHelper surfaceTextureHelper, Context context, CapturerObserver capturerObserver) {
        // Stub implementation
    }
    
    @Override
    public void startCapture(int width, int height, int framerate) {
        // Stub implementation
    }
    
    @Override
    public void stopCapture() throws InterruptedException {
        // Stub implementation
    }
    
    @Override
    public void changeCaptureFormat(int width, int height, int framerate) {
        // Stub implementation
    }
    
    @Override
    public void dispose() {
        // Stub implementation
    }
    
    @Override
    public boolean isScreencast() {
        return true;
    }
    
    public Object getMediaProjection() {
        return new MediaProjection();
    }
    
    public static class MediaProjection {
        public interface Callback {
            void onMediaProjectionCreated(MediaProjection mediaProjection);
        }
    }
}
