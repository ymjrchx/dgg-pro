package net.dgg.core.utils;

import java.util.Objects;
import java.util.regex.Pattern;

public class DggHtmlUtil {

	private static final Pattern REG_SCRIPT = Pattern.compile("<script[^>]*?>[\\s\\S]*?<\\/script>");
	private static final Pattern REG_STYLE = Pattern.compile("<style[^>]*?>[\\s\\S]*?<\\/style>");
	private static final Pattern REG_HTML = Pattern.compile("<[^>]+>");

	public static String filterHTML(String html) {
		return filterHTML(html, true, true, true);
	}

	public static String filterScript(String html) {
		return filterHTML(html, true, false, false);
	}

	public static String filterStyle(String html) {
		return filterHTML(html, false, true, false);
	}

	private static String filterHTML(String html, boolean isScript, boolean isStyle, boolean isHtml) {
		Objects.requireNonNull(html, "html");

		if (isScript) {
			html = REG_SCRIPT.matcher(html).replaceAll("");
		}
		if (isStyle) {
			html = REG_STYLE.matcher(html).replaceAll("");
		}
		if (isHtml) {
			html = REG_HTML.matcher(html).replaceAll("");
		}
		return html.trim();
	}

}
