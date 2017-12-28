package com.cleartv.controller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.cleartv.controller.common.util.KeyEventUtil;
import com.cleartv.controller.common.util.Logger;
import com.cleartv.controller.common.util.PackageUtils;
import com.cleartv.controller.common.util.ShellUtils;
import com.cleartv.controller.jpush.PushManager;
import com.cleartv.controller.localserver.ControllerServer;

import java.util.List;

public class ControllerService extends Service {

    private static final String TAG = "ControllerService";

    public ControllerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        switch (intent.getPackage()){
            case "com.clearcrane.vod":
                ControllerServer.getInstance().startWork();
                return stub;
        }
        return null;
    }

    private ControllerInterface.Stub stub = new ControllerInterface.Stub() {

        @Override
        public int getVersion() throws RemoteException {
            return PackageUtils.getAppVersionCode(ControllerService.this);
        }

        @Override
        public String getDeviceUid() throws RemoteException {
            String deviceUid =  ControllerManager.getDeviceUid();
            Logger.d(TAG,"getDeviceUid:"+deviceUid);
            return deviceUid;
        }

        @Override
        public String getRegID() throws RemoteException {
            String regid = PushManager.getRegId();
            Logger.d(TAG,"getRegID:"+regid);
            return regid;
        }

        @Override
        public void setAlias(String alias) throws RemoteException {
            Logger.d(TAG,"setAlias:"+alias);
            PushManager.setAlias(alias);
        }

        @Override
        public void setTags(List<String> tags) throws RemoteException {
            Logger.d(TAG,"setTags");
            PushManager.setTags(tags);
        }

        @Override
        public void sendKeyUpDown(int keyCode) throws RemoteException {
            Logger.d(TAG,"sendKeyUpDown:"+keyCode);
            KeyEventUtil.sendKeyUpDown(keyCode);
        }

        @Override
        public void installSilent(String filePath) throws RemoteException {
            Logger.d(TAG,"installSilent:"+filePath);
            PackageUtils.installSilent(filePath);
        }

        @Override
        public void execCommand(String cmd) throws RemoteException {
            Logger.d(TAG,"execCommand:"+cmd);
            ShellUtils.execCommand(cmd,ShellUtils.checkRootPermission(),true);
        }

        @Override
        public void reboot() throws RemoteException {
            Logger.d(TAG,"reboot");
            Intent i = new Intent(Intent.ACTION_REBOOT);
            i.putExtra("nowait", 1);
            i.putExtra("interval", 1);
            i.putExtra("window", 0);
            sendBroadcast(i);
        }

        @Override
        public int getServerPort() throws RemoteException {
            return ControllerServer.getInstance().getPort();
        }

        @Override
        public boolean isLocalServerAlive() throws RemoteException {
            return ControllerServer.getInstance().isAlive();
        }
    };


}
