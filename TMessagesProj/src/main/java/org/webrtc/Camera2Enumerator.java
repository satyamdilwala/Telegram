package org.webrtc;

import android.content.Context;

/**
 * Stub implementation of Camera2Enumerator for compilation purposes.
 * This is a placeholder - VoIP functionality will not work properly.
 */
public class Camera2Enumerator implements CameraEnumerator {
    
    public Camera2Enumerator(Context context) {
        // Stub implementation
    }
    
    public static boolean isSupported(Context context) {
        return false;
    }
    
    @Override
    public String[] getDeviceNames() {
        return new String[0];
    }
    
    @Override
    public boolean isFrontFacing(String deviceName) {
        return false;
    }
    
    @Override
    public boolean isBackFacing(String deviceName) {
        return false;
    }
    
    @Override
    public VideoCapturer createCapturer(String deviceName, VideoCapturer.CapturerObserver observer) {
        return null;
    }
}
