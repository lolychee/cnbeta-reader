package com.lolychee.android.cnbetareader.activities;

import java.io.IOException;
import java.util.*;
import com.lolychee.android.cnbetareader.R;
import com.lolychee.android.cnbetareader.helpers.CnbetaHelper;
import com.lolychee.android.cnbetareader.models.Article;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ArticlesListActivity extends ListActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.articles_list);
		new PullTask().execute();
	}

	class PullTask extends AsyncTask<Void, Void, List<Article>> {

		protected ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(ArticlesListActivity.this,
					null, "正在读取数据,请稍等……", true, false);
		}

		@Override
		protected List<Article> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			CnbetaHelper cbhelper = CnbetaHelper.getInstance();
			List<Article> list = null;
			try {
				list = cbhelper.getArticlesList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return list;
		}

		@Override
		protected void onPostExecute(List<Article> result) {
			progressDialog.dismiss();
			setListAdapter(new ArticlesListAdapter(ArticlesListActivity.this,
					result, R.layout.articles_list_item)); 
		}

	}

	public void onListItemClick(ListView listView, View view, int position,
			long id) {

		Intent intent = new Intent();
		intent.setClass(ArticlesListActivity.this,
						ArticleWrapperActivity.class);// 指明要跳转的Activity类

		Bundle bundle = new Bundle();
		bundle.putLong("id", (long) id);

		intent.putExtras(bundle);
		startActivity(intent);
		
	}
	
	
}