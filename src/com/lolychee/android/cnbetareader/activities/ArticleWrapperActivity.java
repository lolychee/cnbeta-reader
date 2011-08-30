package com.lolychee.android.cnbetareader.activities;

import com.lolychee.android.cnbetareader.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class ArticleWrapperActivity extends TabActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_wrapper);
		Bundle bundle = getIntent().getExtras();
		
		Intent intent = new Intent();
		intent.putExtras(bundle);

		intent.setClass(ArticleWrapperActivity.this, ArticleActivity.class);

		TabHost tabHost = this.getTabHost();
		tabHost.addTab(tabHost.newTabSpec("article").setIndicator("原文",
				getResources().getDrawable(android.R.drawable.ic_menu_info_details))
				.setContent(intent));
		
		intent.setClass(ArticleWrapperActivity.this, CommentsListActivity.class);
		
		tabHost.addTab(tabHost.newTabSpec("comments").setIndicator("评论",
				getResources().getDrawable(android.R.drawable.ic_menu_add))
				.setContent(intent));

	}
}
