package com.lolychee.android.cnbetareader.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.lolychee.android.cnbetareader.R;
import com.lolychee.android.cnbetareader.helpers.CnbetaHelper;
import com.lolychee.android.cnbetareader.models.*;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ArticleActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article);
		Bundle bundle = getIntent().getExtras();
		

		String charset = "utf-8";
		String template = "";
		try {
			AssetManager am = getAssets();
			InputStream is = am.open("article.html");
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					charset));
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			template = sb.toString();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CnbetaHelper cbhelper = CnbetaHelper.getInstance();
		WebView webView = (WebView) findViewById(R.id.article_webview);

		Article article = cbhelper.getArticle(bundle.getLong("id"));
		if (article != null) {
			String html = template
					.replaceAll("%id%", Long.toString(article.getId()))
					.replaceAll("%title%", article.getTitle())
					.replaceAll("%topic%", article.getTopic())
					.replaceAll("%pubdate%", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(article.getDate()).toString())
					.replaceAll("%content%", article.getContent());

			webView.setScrollBarStyle(0);// 滚动条风格，为0就是不给滚动条留空间，滚动条覆盖在网页上

			WebSettings settings = webView.getSettings();
			settings.setJavaScriptEnabled(true);
			settings.setDefaultTextEncodingName(charset);
			settings.setPluginsEnabled(true);

			// webView.loadData(html, "text/html", charset); //有时会出现当作网址解析的情况
			webView.loadDataWithBaseURL(null, html, "text/html", charset, null);
		}

	}
}