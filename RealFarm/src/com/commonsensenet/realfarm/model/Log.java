package com.commonsensenet.realfarm.model;

public class Log {

	public static final String MAIN_OPENED = "main_opened";
	public static final String MAIN_ACTIONBAR_HOME_CLICKED = "main_actionbar_home_clicked";
	public static final String MAIN_ACTIONBAR_PLOTS_OPEN = "main_actionbar_plots_open";
	public static final String MAIN_ACTIONBAR_NEWS_OPEN = "main_actionbar_news_open";
	public static final String MAIN_ACTIONBAR_PLOTS_PLOT_CLICKED = "main_actionbar_plots_plot_clicked";
	public static final String MAIN_ACTIONBAR_NEWS_NEWS_CLICKED = "main_actionbar_news_news_clicked";
	public static final String MAIN_MAP_PLOT_CLICKED = "main_map_plot_clicked";
	public static final String MAIN_MAP_SCROLLED = "main_map_scrolled"; // not
																		// implemented.
	public static final String MAIN_MENU_SETTINGS_CLICKED = "main_menu_settings_clicked";
	public static final String MAIN_MENU_HELP_CLICKED = "main_menu_help_clicked";
	public static final String PLOTINFO_DISMISS = "plotinfo_dismiss";

	private String mDate;
	private int mId;
	private String mName;
	private String mValue;

	public Log(int id, String name, String value, String date) {
		mId = id;
		mName = name;
		mValue = value;
		mDate = date;
	}

	public String getDate() {
		return mDate;
	}

	public int getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public String getValue() {
		return mValue;
	}
}
