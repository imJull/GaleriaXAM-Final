package net.unadeca.galeria.Activities.database;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

public class GaleriaApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //inicializar la base de datos
        FlowManager.init(this);
    }
}
