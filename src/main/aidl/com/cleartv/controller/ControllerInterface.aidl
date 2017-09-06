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

    void sendKeyUpDown(int keyCode);
}
