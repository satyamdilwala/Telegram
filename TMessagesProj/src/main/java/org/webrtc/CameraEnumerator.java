package org.webrtc;

/**
 * Stub implementation of CameraEnumerator for compilation purposes.
 * This is a placeholder - VoIP functionality will not work properly.
 */
public interface CameraEnumerator {
    String[] getDeviceNames();
    boolean isFrontFacing(String deviceName);
    boolean isBackFacing(String deviceName);
    VideoCapturer createCapturer(String deviceName, VideoCapturer.CapturerObserver observer);
}
