package com.hfad.faceclassifier.HelperClasses;

public interface CameraGrabberListener {
    void onCameraInitialized();
    void onCameraError(String errorMsg);
}