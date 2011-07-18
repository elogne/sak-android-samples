package sak.samples.dialogintransition;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class Main extends Activity {
    private Handler handler = new Handler();
    private Dialog dialog = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        WebView wv = (WebView)findViewById(R.id.webview);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new MyWebViewClient());
        wv.loadUrl("http://sakplus.jp/blog");
    }
    
    private class MyWebViewClient extends WebViewClient {
        private AnimationDrawable animation;
        
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("debug", "onPageStarted: " + url);
            if (dialog != null) {
                dialog.dismiss();
            }
            dialog = new Dialog(Main.this, R.style.MyProgressTheme);
            dialog.setContentView(R.layout.my_progress_dialog);
            dialog.show();

            // アニメーションの動作
            ImageView iv = (ImageView)dialog.findViewById(R.id.image);
            iv.setBackgroundResource(R.drawable.ic_spinner);
            animation = (AnimationDrawable)iv.getBackground();
            startAnimation();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d("debug", "onPageFinished: " + url);
            stopAnimation();
            dialog.dismiss();
            dialog = null;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.d("debug", "onReceivedError: " + failingUrl + " (code: " + errorCode + ")");
            stopAnimation();
            dialog.dismiss();
            dialog = null;
        }
        
        private void startAnimation() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    animation.start();
                }
            });
        }
        
        private void stopAnimation() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    animation.stop();
                }
            });
        }
    }
}