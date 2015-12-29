package com.github.bordertech.wcomponents.showcase;

import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.InternalResource;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.Message;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.UIContext;
import com.github.bordertech.wcomponents.UIContextHolder;
import com.github.bordertech.wcomponents.WApplication;
import com.github.bordertech.wcomponents.WColumn;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WContent;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WRow;
import com.github.bordertech.wcomponents.WTabSet;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.showcase.widgets.PropertyContainer;
import com.github.bordertech.wcomponents.showcase.widgets.Showcase;
import com.github.bordertech.wcomponents.showcase.widgets.WidgetContainer;
import com.github.bordertech.wcomponents.util.Config;
import java.util.Date;

/**
 * Showcase tope level applciation class.
 *
 * @author Jonathan Austin
 * @since 1.0.3
 */
public class ShowcaseApp extends WApplication implements MessageContainer {

	private final WMessages messages = new WMessages();

	private final WPanel root = new WPanel(WPanel.Type.CHROME);

	private final WPanel demo = new WPanel();
	private final PickerPanel picker = new PickerPanel(this);
	private final WPanel properties = new WPanel();
	private final InfoPanel info = new InfoPanel();
	private final SourcePanel source = new SourcePanel();
	/**
	 * Additional Javascript used to provide syntax-highlighting client-side.
	 */
	private final WContent javascript = new WContent();

	/**
	 * Additional CSS used to provide syntax-highlighting client-side.
	 */
	private final WContent css = new WContent();

	/**
	 * Construct the controller.
	 */
	public ShowcaseApp() {
		WPanel header = new WPanel(WPanel.Type.HEADER);
		header.add(new WHeading(HeadingLevel.H1, "Showcase"));
		add(header);

		add(messages);

		root.setTitleText("Example");
		root.setMargin(new Margin(18));
		root.setSearchAncestors(false);
		add(root);

		WRow row = new WRow();
		root.add(row);

		WColumn left = new WColumn(70);
		WColumn right = new WColumn(30);
		row.add(left);
		row.add(right);

		// Left
		left.add(demo);

		// Right
		WTabSet tabs = new WTabSet();
		right.add(tabs);

		WPanel holderProperties = new WPanel();
		WPanel holderInfo = new WPanel();
		WPanel holderSource = new WPanel();

		holderProperties.add(properties);
		holderInfo.add(info);
		holderSource.add(source);

		tabs.addTab(picker, "Picker", WTabSet.TabMode.CLIENT);
		tabs.addTab(holderProperties, "Properties", WTabSet.TabMode.CLIENT);
		tabs.addTab(holderInfo, "Info", WTabSet.TabMode.CLIENT);
		tabs.addTab(holderSource, "Source", WTabSet.TabMode.CLIENT);

		// Footer
		WPanel footer = new WPanel(WPanel.Type.FOOTER);
		footer.add(new WText(new Date().toString()));
		add(footer);

		picker.addAjaxTargets(left, holderProperties, holderInfo, holderSource);
		info.addAjaxTargets(picker, left, holderProperties, holderInfo, holderSource);

		setShowcaseVisible(false);

		String version = Config.getInstance().getString("wcomponents-examples.version");
		javascript.setCacheKey("wc.showcase.js." + version);
		css.setCacheKey("wc.showcase.css." + version);

		javascript.setContentAccess(new InternalResource("/js/syntaxHighlight.js", "syntaxHighlight.js"));
		css.setContentAccess(new InternalResource("/css/syntaxHighlight.css", "syntaxHighlight.css"));
		add(javascript);
		add(css);

	}

	@Override
	public WMessages getMessages() {
		return messages;
	}

	public void doShowItem(final String widget) {
		Showcase showcase = picker.selectShowcase(widget);
		if (showcase != null) {
			doShowItem(showcase);
		}
	}

	public void doShowItem(final Showcase showcase) {

		resetShowcase();

		WidgetContainer widgetContainer = showcase.getWidgetContainerInstance();
		demo.add(widgetContainer);

		PropertyContainer propertyContainer = showcase.getPropertyContainerInstance(widgetContainer);
		properties.add(propertyContainer);

		root.setBean(showcase);

		setShowcaseVisible(true);
	}

	/**
	 * Override preparePaint in order to set up the resources on first access by a user.
	 *
	 * @param request the request being responded to.
	 */
	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		UIContext uic = UIContextHolder.getCurrent();
		uic.getHeaders().addUniqueHeadLine("<script type='text/javascript' src='" + WebUtilities.
				encode(javascript.getUrl()) + "'></script>");
		uic.getHeaders().addUniqueHeadLine(
				"<link type='text/css' rel='stylesheet' href='" + WebUtilities.encode(css.getUrl()) + "'></link>");
	}

	private void resetShowcase() {
		demo.reset();
		properties.reset();
		info.reset();
		source.reset();
	}

	private void setShowcaseVisible(final boolean visible) {
		demo.setVisible(visible);
		info.setVisible(visible);
		source.setVisible(visible);
		properties.setVisible(visible);
	}

	/**
	 * If a step error has occurred, then display an error message to the user.
	 */
	@Override
	public void handleStepError() {
		getMessages().addMessage(new Message(Message.WARNING_MESSAGE,
				"A request was made that is not in the expected sequence and the application has been refreshed to its current state."));
	}

	public static ShowcaseApp getInstance(final WComponent component) {
		return WebUtilities.getAncestorOfClass(ShowcaseApp.class, component);
	}

}
