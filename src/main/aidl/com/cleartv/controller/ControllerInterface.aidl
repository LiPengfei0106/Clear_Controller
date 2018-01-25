// ControllerAidlInterface.aidl
package com.cleartv.controller;

// Declare any non-default types here with import statements

interface ControllerInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int getVersion();

    String getRegID();

    void setAlias(String alias);

    void setTags(in List<String> tags);

    void sendKeyUpDown(int keyCode);

    void installSilent(String filePath);

    void execCommand(String cmd);

    void reboot();

    int getServerPort();

    String getDeviceUid();

    boolean isLocalServerAlive();

    // Vod中的操作通过此方法传递到Controller
    void notifyStatusChanged(String status);

    void createChatGroup(long id);
}
