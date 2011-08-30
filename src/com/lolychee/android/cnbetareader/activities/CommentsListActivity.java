package com.lolychee.android.cnbetareader.activities;

import java.io.IOException;
import java.util.List;

import com.lolychee.android.cnbetareader.R;
import com.lolychee.android.cnbetareader.helpers.CnbetaHelper;
import com.lolychee.android.cnbetareader.models.*;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;


public class CommentsListActivity extends ListActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comments_list);
		Bundle bundle = getIntent().getExtras();
		
		new PullNormalTask().execute(bundle);
	}
	
	class PullNormalTask extends AsyncTask<Bundle, Void, List<Comment>> {

		@Override
		protected void onPreExecute() {
			
		}

		@Override
		protected List<Comment> doInBackground(Bundle... params) {
			// TODO Auto-generated method stub
			CnbetaHelper cbhelper = CnbetaHelper.getInstance();
			List<Comment> list = null;
			try {
				list = cbhelper.getCommentsList(params[0].getLong("id"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return list;
		}

		@Override
		protected void onPostExecute(List<Comment> result) {
			setListAdapter(new CommentsListAdapter(CommentsListActivity.this,
					result, R.layout.comments_list_item));
		}

	}

}
