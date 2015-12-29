package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.WComponent;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jonathan
 */
public abstract class AbstractShowcase<T extends WComponent> implements Showcase<T> {

	private final Class<T> widget;

	public AbstractShowcase(final Class<T> widget) {
		this.widget = widget;
	}

	@Override
	public Class<T> getWidgetClass() {
		return widget;
	}

	@Override
	public String getWidgetName() {
		return widget.getSimpleName();
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return Collections.EMPTY_LIST;
	}

}
