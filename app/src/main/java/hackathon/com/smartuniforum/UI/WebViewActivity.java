package hackathon.com.smartuniforum.UI;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import hackathon.com.smartuniforum.R;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        try {
            progressBar = findViewById(R.id.progressBar);
            String URL = getIntent().getStringExtra("url");
            webView = findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(true);
            webView.setWebViewClient(new WebClient());
            webView.loadUrl(URL);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
        }
    }

    private class WebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(String.valueOf(request.getUrl()));
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }
}
