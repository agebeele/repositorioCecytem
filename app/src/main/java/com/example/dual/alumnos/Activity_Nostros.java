package com.example.dual.alumnos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.dual.R;

public class Activity_Nostros extends AppCompatActivity {

    VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nostros);

        mVideoView= findViewById(R.id.surfaceView);
        inicia();
    }

    private void inicia() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            mVideoView.setVideoPath("/storage/emulated/0/DCIM/Camera/661112530cef760a6c414323ace002fc.mp4");
            mVideoView.setMediaController(new MediaController(this));
            mVideoView.requestFocus();
            mVideoView.start();
            //Toast.makeText(this, "Acepta permiso para ver el video", Toast.LENGTH_SHORT).show();
        } else {


            ActivityCompat.requestPermissions(Activity_Nostros.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0001);
        }
    }
    public void onRequestPermissionResult (int requestcode, @NonNull String[] permissions, @NonNull int[]granResults){
        super.onRequestPermissionsResult(requestcode, permissions, granResults);

        if (requestcode == 0001) {
            if (granResults.length == 1 && granResults[0] == PackageManager.PERMISSION_GRANTED) {
                mVideoView.setVideoPath("/storage/sdcard0/DCIM/Camera/VID_20230530_213317883.mp4");
                mVideoView.start();
                mVideoView.requestFocus();
                mVideoView.setMediaController(new MediaController(this));
            } else {

                Toast.makeText(this, "Los permisos por favor", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void streetview(View view) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/TecNM+-+Tecnológico+de+Estudios+Superiores+de+Coacalco+-+TESCo/@19.6305033,-99.1143025,13z/data=!4m10!1m2!2m1!1sTESCo!3m6!1s0x85d1f41b204bbead:0x174544a53d86fb50!8m2!3d19.6305033!4d-99.1143025!15sCgVURVNDbyIDiAEBkgERcHVibGljX3VuaXZlcnNpdHngAQA!16s%2Fg%2F1tm0thgr?entry=ttu"));
        startActivity(intent);
    }
    public void Correo(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Buenas tardes necesito ayuda");
        intent.putExtra(Intent.EXTRA_TEXT, "Mi aplicacion tiene una mala sincronizacion con el caletador, supuestamente la temperatura" +
                "alta, sin embargo, se encuentra baja");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"brandon_adrian.itic@tesco.edu.mx"});
        startActivity(intent);
    }
    public void llamar (View view){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: 313 706 5253"));
            startActivity(intent);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},0001);
        }
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0001){
            if (grantResults.length == 1 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: 313 706 5253"));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Sin permiso", Toast.LENGTH_SHORT).show();
            }
        }
    }
}