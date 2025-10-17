package org.webrtc;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.TextureView;

/**
 * Stub implementation of TextureViewRenderer for compilation purposes.
 * This is a placeholder - VoIP functionality will not work properly.
 */
public class TextureViewRenderer extends TextureView implements VideoSink {
    
    public int rotatedFrameWidth = 0;
    public int rotatedFrameHeight = 0;
    
    public TextureViewRenderer(Context context) {
        super(context);
    }
    
    public void init(EglBase.Context eglContext, RendererCommon.RendererEvents rendererEvents) {
        // Stub implementation
    }
    
    public void init(EglBase.Context eglContext, RendererCommon.RendererEvents rendererEvents, EglBase.Config config, GlRectDrawer drawer) {
        // Stub implementation
    }
    
    public void setScalingType(RendererCommon.ScalingType scalingType) {
        // Stub implementation
    }
    
    public void onFirstFrameRendered() {
        // Stub implementation
    }
    
    public void onFrameResolutionChanged(int videoWidth, int videoHeight, int rotation) {
        // Stub implementation
    }
    
    public boolean isFirstFrameRendered() {
        return false;
    }
    
    public void setFpsReduction(int fps) {
        // Stub implementation
    }
    
    public void setEnableHardwareScaler(boolean enable) {
        // Stub implementation
    }
    
    public void setIsCamera(boolean isCamera) {
        // Stub implementation
    }
    
    public void setScreenRotation(int rotation) {
        // Stub implementation
    }
    
    public void updateRotation() {
        // Stub implementation
    }
    
    public void setBackgroundRenderer(TextureView background) {
        // Stub implementation
    }
    
    public void getRenderBufferBitmap(GlGenericDrawer.TextureCallback callback) {
        // Stub implementation
        callback.onTextureReady(null, 0);
    }
    
    public void setMirror(boolean mirror) {
        // Stub implementation
    }
    
    public void setRotateTextureWithScreen(boolean rotate) {
        // Stub implementation
    }
    
    public void setUseCameraRotation(boolean use) {
        // Stub implementation
    }
    
    public void setMaxTextureSize(int size) {
        // Stub implementation
    }
    
    public void clearFirstFrame() {
        // Stub implementation
    }
    
    public void clearImage() {
        // Stub implementation
    }
    
    public void release() {
        // Stub implementation
    }
    
    @Override
    public void onFrame(VideoFrame frame) {
        // Stub implementation
    }
    
    @Override
    public void setParentSink(VideoSink parent) {
        // Stub implementation
    }
}
