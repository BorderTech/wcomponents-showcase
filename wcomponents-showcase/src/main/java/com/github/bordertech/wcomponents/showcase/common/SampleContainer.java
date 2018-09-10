package com.github.bordertech.wcomponents.showcase.common;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Container;
import com.github.bordertech.wcomponents.MessageContainer;

/**
 *
 * @author jonathan
 */
public interface SampleContainer<T> extends Container, MessageContainer {

	T getWidget();

	AjaxTarget getAjaxTarget();
}
