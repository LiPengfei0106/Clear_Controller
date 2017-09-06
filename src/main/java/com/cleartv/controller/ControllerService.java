package com.cleartv.controller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class ControllerService extends Service {


    public ControllerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        switch (intent.getPackage()){
            case "com.clearcrane.vod":
                return stub;
        }
        return null;
    }

    private ControllerInterface.Stub stub = new ControllerInterface.Stub() {

        @Override
        public int getVersion() throws RemoteException {
            return 1;
        }

        @Override
        public String getRegID() throws RemoteException {
            return PushManager.getRegId();
        }

        @Override
        public void sendKeyUpDown(int keyCode) throws RemoteException {
            CommonUtils.sendKeyUpDown(keyCode);
        }

    };


}
