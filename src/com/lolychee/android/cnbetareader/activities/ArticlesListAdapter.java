package com.lolychee.android.cnbetareader.activities;

import java.util.*;

import com.lolychee.android.cnbetareader.R;
import com.lolychee.android.cnbetareader.helpers.DateHelper;
import com.lolychee.android.cnbetareader.models.Article;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArticlesListAdapter extends BaseAdapter {

	private Context context;

	private List<Article> list;

	private int view;

	public ArticlesListAdapter(Context context, List<Article> list, int view) {
		this.context = context;
		this.list = list;
		this.view = view;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return list != null ? list.size() : 0;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if ((list != null && list.size() > 0)
				&& (position >= 0 && position < list.size())) {
			return list.get(position);
		}
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if ((list != null && list.size() > 0)
				&& (position >= 0 && position < list.size())) {
			return list.get(position).getId();
		}
		return position;
	}

	/*
	 * @Override public View getView(int position, View convertView, ViewGroup
	 * parent) { LayoutInflater li = (LayoutInflater) context
	 * .getSystemService(Context.LAYOUT_INFLATER_SERVICE); View view =
	 * li.inflate(this.view, null); Article article = (Article)
	 * getItem(position);
	 * 
	 * TextView title = (TextView) view.findViewById(R.id.entries_list_title);
	 * title.setText(article.getTitle()); TextView summary = (TextView) view
	 * .findViewById(R.id.entries_list_summary);
	 * summary.setText(article.getSummary());
	 * 
	 * return view; }
	 */

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(this.view, null);

			holder = new ViewHolder();
			holder.title = (TextView) convertView
			.findViewById(R.id.articlesListTitle);
			holder.topic = (TextView) convertView
			.findViewById(R.id.articlesListTopic);
			holder.date = (TextView) convertView
			.findViewById(R.id.articlesListDate);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Article article = (Article) getItem(position);
		if (holder.title != null) {
			holder.title.setText(Html.fromHtml(article.getTitle()));
		}
		if (holder.topic != null) {
			holder.topic.setText(article.getTopic());
		}
		if (holder.date != null) {
			holder.date.setText(DateHelper.xTimesAgo(article.getDate()));
		}
		return convertView;
	}

	static class ViewHolder {
		TextView title;
		TextView topic;
		TextView date;
	}

}
