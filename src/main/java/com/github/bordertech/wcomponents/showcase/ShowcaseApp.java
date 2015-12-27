package com.github.bordertech.wcomponents.showcase;

import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.InternalResource;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.Message;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.RenderContext;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.UIContext;
import com.github.bordertech.wcomponents.UIContextHolder;
import com.github.bordertech.wcomponents.WApplication;
import com.github.bordertech.wcomponents.WColumn;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WContent;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WLink;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WRow;
import com.github.bordertech.wcomponents.WTabSet;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.servlet.WebXmlRenderContext;
import com.github.bordertech.wcomponents.showcase.item.ShowcaseItem;
import com.github.bordertech.wcomponents.util.Config;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Showcase tope level applciation class.
 *
 * @author Jonathan Austin
 * @since 1.0.3
 */
public class ShowcaseApp extends WApplication implements MessageContainer {

	private final WMessages messages = new WMessages();

	private final WColumn left = new WColumn(75);
	private final WColumn right = new WColumn(25);

	private final PickerPanel picker = new PickerPanel(this);
	private final WPanel properties = new WPanel();
	private final WPanel info = new WPanel();
	private final WPanel source = new WPanel() {
		/**
		 * Override afterPaint in order to render the additional mark-up required for client-side syntax highligthing.
		 *
		 * @param renderContext the renderContext to send output to.
		 */
		@Override
		protected void afterPaint(final RenderContext renderContext) {
			super.afterPaint(renderContext);
			PrintWriter writer = ((WebXmlRenderContext) renderContext).getWriter();
			// Kick of the syntax highlighting
			writer.write(
					"<script type='text/javascript'>if (window.doHighlighting) doHighlighting('" + getId() + "');</script>");
		}

	};

	private final WText txtSource = new WText();
	private final WText txtInfo = new WText();
	private final WLink linkAPI = new WLink("API", "");

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

		WPanel root = new WPanel(WPanel.Type.CHROME);
		root.setTitleText("Example");
		root.setMargin(new Margin(18));
		add(root);

		WRow row = new WRow();
		root.add(row);
		row.add(left);
		row.add(right);

		WPanel footer = new WPanel(WPanel.Type.FOOTER);
		footer.add(new WText(new Date().toString()));
		add(footer);

		setupLeft();
		setupRight();

		picker.setAjaxTargets(left, properties, info, source);

		String version = Config.getInstance().getString("wcomponents-examples.version");
		javascript.setCacheKey("wc.showcase.js." + version);
		css.setCacheKey("wc.showcase.css." + version);

		javascript.setContentAccess(new InternalResource("/js/syntaxHighlight.js", "syntaxHighlight.js"));
		css.setContentAccess(new InternalResource("/css/syntaxHighlight.css", "syntaxHighlight.css"));
		add(javascript);
		add(css);
	}

	private void setupLeft() {
		// Nothing yet
	}

	private void setupRight() {

		WTabSet tabs = new WTabSet();
		right.add(tabs);

		tabs.addTab(picker, "Picker", WTabSet.TabMode.CLIENT);
		tabs.addTab(properties, "Properties", WTabSet.TabMode.CLIENT);
		tabs.addTab(info, "Info", WTabSet.TabMode.CLIENT);
		tabs.addTab(source, "Source", WTabSet.TabMode.CLIENT);

		source.add(txtSource);

		info.add(txtInfo);
		info.add(linkAPI);

		txtSource.setEncodeText(false);
		txtInfo.setEncodeText(false);
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

	@Override
	public WMessages getMessages() {
		return messages;
	}

	public void doShowItem(final ShowcaseItem showcaseItem) {
		left.reset();
		properties.reset();
		info.reset();
		source.reset();

		ItemPanel itemPanel = showcaseItem.getItemInstance();
		left.add(itemPanel);

		WPanel propertiesPanel = showcaseItem.getPropertiesInstance(itemPanel);
		properties.add(propertiesPanel);

		WComponent widget = itemPanel.getItem();
		String source = SourceUtil.getSource(widget.getClass().getName());
		txtSource.setText(SourceUtil.formatSource(source));
		txtInfo.setText(SourceUtil.getJavaDoc(source));

		String htmlName = '/' + widget.getClass().getName().replace('.', '/') + ".html";
		linkAPI.setUrl("http://bordertech.github.io/wcomponents/apidocs" + htmlName);

	}

	/**
	 * If a step error has occurred, then display an error message to the user.
	 */
	@Override
	public void handleStepError() {
		messages.addMessage(new Message(Message.WARNING_MESSAGE,
				"A request was made that is not in the expected sequence and the application has been refreshed to its current state."));
	}

	public static ShowcaseApp getInstance(final WComponent component) {
		return WebUtilities.getAncestorOfClass(ShowcaseApp.class, component);
	}

}
