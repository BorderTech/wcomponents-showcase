package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.Container;
import com.github.bordertech.wcomponents.WComponent;

/**
 *
 * @author jonathan
 */
public interface WidgetContainer<T extends WComponent> extends Container {

	T getWidget();
}
