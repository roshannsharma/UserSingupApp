package com.fleeca.userregistrationapplication.utils;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.Window;
import android.widget.VideoView;

import com.fleeca.userregistrationapplication.R;

public class CustomProgressBar {

        private Dialog dialog;

    public CustomProgressBar(Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(false);

        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        VideoView videoView = dialog.findViewById(R.id.loaderVideo);
        Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.loader);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.start();
        });
    }


    public void show() {
            if (!dialog.isShowing()) dialog.show();
        }

        public void hide() {
            if (dialog.isShowing()) dialog.dismiss();
        }


}
