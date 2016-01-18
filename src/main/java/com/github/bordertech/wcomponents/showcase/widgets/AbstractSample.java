package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.showcase.ShowcaseApp;
import com.github.bordertech.wcomponents.showcase.common.SampleContainer;

/**
 *
 * @author jonathan
 */
public abstract class AbstractSample<T> extends WPanel implements SampleContainer<T> {

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			// Make sample same the same as the default properties
			ShowcaseApp.getInstance(this).doConfigWidget();
			setInitialised(true);
		}
		if (getMessages().hasMessages()) {
			getMessages().setMargin(new Margin(0, 0, 12, 0));
		}
	}

}
