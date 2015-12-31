package com.github.bordertech.wcomponents.showcase;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Container;
import com.github.bordertech.wcomponents.WComponent;

/**
 *
 * @author jonathan
 */
public interface SampleContainer<T extends WComponent> extends Container {

	T getWidget();

	AjaxTarget getAjaxTarget();
}
