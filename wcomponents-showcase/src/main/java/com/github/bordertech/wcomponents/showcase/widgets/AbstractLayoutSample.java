package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WText;

/**
 *
 * @author jonathan
 */
public abstract class AbstractLayoutSample<T> extends AbstractSample<T> {

	private final WMessages messages = new WMessages();

	public AbstractLayoutSample() {

		add(messages);

		WText css = new WText("<style type='text/css'> .mydotted {border: dashed 1px #ccc;}</style>");
		css.setEncodeText(false);
		setHtmlClass("mydotted");
		add(css);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WMessages getMessages() {
		return messages;
	}
}
