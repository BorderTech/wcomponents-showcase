package com.github.bordertech.wcomponents.showcase;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Container;

/**
 *
 * @author jonathan
 */
public interface SampleContainer<T> extends Container {

	T getWidget();

	AjaxTarget getAjaxTarget();
}
