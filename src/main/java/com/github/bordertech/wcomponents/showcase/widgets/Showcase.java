package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.showcase.widgets.WidgetContainer;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.util.Duplet;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author jonathan
 */
public interface Showcase<T extends WComponent> extends Serializable {

	String getWidgetName();

	Class<T> getWidgetClass();

	PropertyContainer getPropertyContainerInstance(WidgetContainer<T> showcasePanel);

	WidgetContainer<T> getWidgetContainerInstance();

	List<Duplet<String, String>> getRelatedLinks();

	List<Duplet<String, String>> getApiLinks();

	String getPseudoCode();

	String getInfo();

}
