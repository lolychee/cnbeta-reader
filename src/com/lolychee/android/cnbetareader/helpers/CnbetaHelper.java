package com.lolychee.android.cnbetareader.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.xmlpull.v1.XmlPullParser;

import android.util.Log;
import android.util.Xml;

import com.lolychee.android.cnbetareader.models.*;

public class CnbetaHelper {

	final static String feedUrl = "http://www.cnbeta.com/backend.php";

	final static String articleUrl = "http://m.cnbeta.com/marticle.php?sid=%s";

	final static String articleDigUrl = "";

	final static String commentsUrl = "http://www.cnbeta.com/comment/normal/%s.html";

	final static String hotCommentsUrl = "http://www.cnbeta.com/comment/g_content/%s.html";

	final static String commentNewUrl = "http://www.cnbeta.com/Ajax.comment.php?ver=new";

	final static String commentVoteUpUrl = "http://www.cnbeta.com/Ajax.vote.php?tid=%s&support=1";

	final static String commentVoteDownUrl = "http://www.cnbeta.com/Ajax.vote.php?tid=%s&against=1";

	final static String validateCodeUrl = "http://www.cnbeta.com/validate1.php";

	private volatile static CnbetaHelper uniqueInstance;

	public static CnbetaHelper getInstance() {
		if (uniqueInstance == null) {
			synchronized (CnbetaHelper.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new CnbetaHelper();
				}
			}
		}
		return uniqueInstance;
	}

	protected String getSource(String url, String charset) throws IOException {
		URL u = new URL(url);
		URLConnection conn = u.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(conn
				.getInputStream(), charset));
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}

	protected InputStream getInputStream(String url) throws IOException {
		URL u = new URL(url);
		return u.openStream();
	}

	public List<Article> getArticlesList() throws IOException {

		List<Article> articles = new ArrayList<Article>();
		Article article = null;

		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss z", Locale.US);

		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(getInputStream(feedUrl), "UTF-8");

			int event = parser.getEventType();// 产生第一个事件
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:// 判断当前事件是否是文档开始事件
					break;
				case XmlPullParser.START_TAG:// 判断当前事件是否是标签元素开始事件
					if ("item".equals(parser.getName())) {
						article = new Article();
					}
					if (article != null) {
						if ("title".equals(parser.getName())) {
							article.setTitle(parser.nextText());
						} else if ("link".equals(parser.getName())) {
							Pattern pattern = Pattern
									.compile("http://www\\.cnbeta\\.com/articles/(\\d+)\\.htm");
							Matcher matcher = pattern
									.matcher(parser.nextText());
							matcher.find();

							article.setId(Integer.parseInt(matcher.group(1)));
						} else if ("category".equals(parser.getName())) {
							article.setTopic(parser.nextText());
						} else if ("description".equals(parser.getName())) {
							article.setSummary(parser.nextText());
						} else if ("pubDate".equals(parser.getName())) {
							Date date = sdf.parse(parser.nextText());
							article.setDate(date);
						}
					}

					break;
				case XmlPullParser.END_TAG:// 判断当前事件是否是标签元素结束事件
					if ("item".equals(parser.getName())) {
						articles.add(article);
						article = null;
					}
					break;
				}
				event = parser.next();// 进入下一个元素并触发相应事件
			}// end while

		} catch (Exception e) {
			e.printStackTrace();

		}

		return articles;
	}

	public Article getArticle(long id) {
		String url = articleUrl.replaceAll("%s", Long.toString(id));

		String source = "";
		try {
			source = getSource(url, "utf-8");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Article article = null;

		String pattern = "(?s)<b>([^<]*)</b>.*<br/>.*<b>新闻发布日期：</b>([^<]*)<br/>.*<b>新闻主题：</b>([^<]+)<br/><br/>(.*)<br/><a[^>]+>查看评论</a>";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);
		if (m.find()) {
			article = new Article();
			article.setId(id);
			article.setTitle(m.group(1).trim());
			article.setTopic(m.group(3).trim());
			article.setContent(m.group(4).trim());

			try {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss", Locale.CHINA);
				Date date = sdf.parse(m.group(2).trim());
				article.setDate(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Log.d("cbhelper", "not match");
		}

		return article;

	}

	// public boolean articleDig(int id) throws IOException {}

	public List<Comment> getCommentsList(long article_id) throws IOException {

		String url = commentsUrl.replaceAll("%s", Long.toString(article_id));
		
		String source = getSource(url, "utf-8");

		List<Comment> comments = new ArrayList<Comment>();

		Comment comment;

		try {

			Pattern pattern = Pattern
					.compile("(?s)<dl>.*?第(\\d+)楼[^>]*?>(.*?)发表于([^<]*?)<.*?<dd\\sclass=\"quotecomment\">(.*?)</dd>.*?<dd\\sclass=\"re_detail\">(.*?)</dd>.*?ShowReply\\((\\d+),.*?支持</a>\\(<[^>]*>(\\d)*</span>\\).*?反对</a>\\(<[^>]*>(\\d)*</span>\\).*?</dl>");
			Matcher matcher = pattern.matcher(source);
			while(matcher.find()){
				comment = new Comment();
				comment.setId(Long.parseLong(matcher.group(6)));
				comment.setFloorId(Integer.parseInt(matcher.group(1)));
				comment.setName(matcher.group(2));
				comment.setContent(matcher.group(5));
				comment.setVoteUp(Integer.parseInt(matcher.group(7)));
				comment.setVoteDown(Integer.parseInt(matcher.group(8)));

				Log.d("cnbeta", comment.getContent());

				comments.add(comment);
				comment = null;
			}

			//comment.setId(Integer.parseInt(matcher.group(1)));

		} catch (Exception e) {
			e.printStackTrace();

		}

		return comments;

	}

	// public List<Comment> getHotComments(int article_id) {}

	// public boolean commentNew() {}

	// public boolean commentVoteUp() {}

	// public boolean commentVoteDown() {}

}
