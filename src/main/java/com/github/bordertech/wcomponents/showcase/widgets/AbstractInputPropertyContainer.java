package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Input;
import com.github.bordertech.wcomponents.WCheckBox;

/**
 *
 * @author jonathan
 */
public abstract class AbstractInputPropertyContainer<T extends Input> extends AbstractPropertyContainer<T> {

	private final WCheckBox chbReadonly = new WCheckBox();
	private final WCheckBox chbDisabled = new WCheckBox();
	private final WCheckBox chbMandatory = new WCheckBox();

	public AbstractInputPropertyContainer(final T widget, final AjaxTarget target) {
		super(widget, target);

		// Properties
		addPropertyWidget("Readonly", chbReadonly);
		addPropertyWidget("Disabled", chbDisabled);
		addPropertyWidget("Mandatory", chbMandatory);
	}

	@Override
	protected void configWidgetProperties(final T widget) {
		super.configWidgetProperties(widget);

		widget.setReadOnly(chbReadonly.isSelected());
		widget.setDisabled(chbDisabled.isSelected());
		widget.setMandatory(chbMandatory.isSelected());
	}

}
