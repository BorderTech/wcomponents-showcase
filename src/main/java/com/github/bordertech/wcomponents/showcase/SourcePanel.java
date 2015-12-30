package com.github.bordertech.wcomponents.showcase;

import com.github.bordertech.wcomponents.RenderContext;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.servlet.WebXmlRenderContext;
import com.github.bordertech.wcomponents.showcase.util.SourceUtil;
import com.github.bordertech.wcomponents.showcase.widgets.Showcase;
import java.io.PrintWriter;

/**
 * This component displays java source code.
 *
 * @author Jonathan Austin
 */
public class SourcePanel extends WPanel {

	/**
	 * The source code.
	 */
	private final WText source = new WText();

	/**
	 * Creates a SourcePanel.
	 */
	public SourcePanel() {

		setBeanProperty(".");

		source.setEncodeText(false);
		add(source);
	}

	/**
	 * Override preparePaint in order to set up the resources on first access by a user.
	 *
	 * @param request the request being responded to.
	 */
	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			Showcase item = getShowcase();
			String rawSource = SourceUtil.getSampleSource(item.getClass().getName());
			source.setText(SourceUtil.formatSource(rawSource));
		}
	}

	/**
	 * Override afterPaint in order to render the additional mark-up required for client-side syntax highlighting.
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

	private Showcase getShowcase() {
		return (Showcase) getBean();
	}

}
