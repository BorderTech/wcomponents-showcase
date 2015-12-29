package com.github.bordertech.wcomponents.showcase;

import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WLink;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.showcase.widgets.Showcase;

/**
 * This component displays info about the widget.
 *
 * @author Jonathan Austin
 */
public class InfoPanel extends WPanel {

	private final WText source = new WText();

	private final WLink linkAPI = new WLink("API", "");

	/**
	 * Creates a SourcePanel.
	 */
	public InfoPanel() {
		setBeanProperty(".");

		source.setEncodeText(false);
		add(source);
		add(linkAPI);

	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			Showcase item = getShowcase();
			// Info Text
			source.setText(item.getInfo());
			String htmlName = '/' + item.getWidgetClass().getName().replace('.', '/') + ".html";
			// API
			linkAPI.setUrl("http://bordertech.github.io/wcomponents/apidocs" + htmlName);
			setInitialised(true);
		}
	}

	private Showcase getShowcase() {
		return (Showcase) getBean();
	}

}
