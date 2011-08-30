package com.lolychee.android.cnbetareader.activities;

import java.util.*;

import com.lolychee.android.cnbetareader.R;
import com.lolychee.android.cnbetareader.models.Comment;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentsListAdapter extends BaseAdapter {

	private Context context;

	private List<Comment> list;

	private int view;

	public CommentsListAdapter(Context context, List<Comment> list, int view) {
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
			holder.name = (TextView) convertView
					.findViewById(R.id.comments_list_name);
			holder.content = (TextView) convertView
					.findViewById(R.id.comments_list_content);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Comment comment = (Comment) getItem(position);
		if (holder.name != null) {
			holder.name.setText(Html.fromHtml(comment.getName()));
		}
		if (holder.content != null) {
			holder.content.setText(Html.fromHtml(comment.getContent()));
		}

		return convertView;
	}

	static class ViewHolder {
		TextView name;
		TextView content;
	}

}
