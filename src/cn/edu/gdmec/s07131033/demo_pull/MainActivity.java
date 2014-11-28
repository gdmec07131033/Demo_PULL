package cn.edu.gdmec.s07131033.demo_pull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			List<User> users = parse(getAssets().open("users.xml"));

			for (User user : users) {
				Log.i("info", user.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<User> parse(InputStream in) {
		List<User> users = null;
		User user = null;
		String tagName = null;
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(in, "UTF-8");

			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					users = new ArrayList<User>();
					break;
				case XmlPullParser.START_TAG:
					tagName = parser.getName();
					if (tagName.equals("user")) {
						user = new User();
						user.setId(Integer.parseInt(parser.getAttributeValue(
								null, "id")));

					}
					if (tagName.equals("name")) {
						try {
							eventType = parser.next();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						user.setName(parser.getText());

					}
					if (tagName.equals("password")) {
						try {
							eventType = parser.next();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						user.setPassword(parser.getText());

					}
					break;
				case XmlPullParser.END_TAG:
					if (parser.getName().equals("user")) {
						users.add(user);
					}

					break;
				
				}
				try {
					eventType = parser.next();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

}
