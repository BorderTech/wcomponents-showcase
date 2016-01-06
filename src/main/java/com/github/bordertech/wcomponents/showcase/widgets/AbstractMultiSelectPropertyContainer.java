package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.AbstractWMultiSelectList;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WNumberField;
import com.github.bordertech.wcomponents.showcase.util.PropertyUtil;

/**
 *
 * @author jonathan
 */
public abstract class AbstractMultiSelectPropertyContainer<T extends AbstractWMultiSelectList> extends AbstractInputPropertyContainer<T> {

	private final WNumberField minSelect = new WNumberField();
	private final WNumberField maxSelect = new WNumberField();

	public AbstractMultiSelectPropertyContainer(final T widget, final AjaxTarget target) {
		super(widget, target);

		minSelect.setMinValue(1);
		maxSelect.setMinValue(1);

		minSelect.setMaxValue(3);
		maxSelect.setMaxValue(3);

		addPropertyWidget("Min select", minSelect);
		addPropertyWidget("Max select", maxSelect);
	}

	@Override
	protected void configWidgetProperties(final T widget) {
		super.configWidgetProperties(widget);

		widget.setMinSelect(PropertyUtil.getPropertyIntValue(minSelect));
		widget.setMaxSelect(PropertyUtil.getPropertyIntValue(maxSelect));
	}

}
