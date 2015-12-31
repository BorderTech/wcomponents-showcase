package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Input;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WCheckBox;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WFieldLayout;

/**
 *
 * @author jonathan
 */
public abstract class AbstractInputPropertyContainer<T extends Input> extends AbstractPropertyContainer<T> {

	public AbstractInputPropertyContainer(final T widget, final AjaxTarget target) {
		super(widget, target);

		WFieldLayout layout = getFieldLayout();
		WContainer ajaxContainer = getAjaxContainer();

		// Readonly
		final WCheckBox chb = new WCheckBox();
		layout.addField("Readonly", chb);
		ajaxContainer.add(new WAjaxControl(chb, target));
		chb.setActionOnChange(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				widget.setReadOnly(chb.isSelected());
			}
		});

		// Disabled
		final WCheckBox chb2 = new WCheckBox();
		layout.addField("Disabled", chb2);
		ajaxContainer.add(new WAjaxControl(chb2, target));
		chb2.setActionOnChange(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				widget.setDisabled(chb2.isSelected());
			}
		});

		// Mandatory
		final WCheckBox chb3 = new WCheckBox();
		layout.addField("Mandatory", chb3);
		ajaxContainer.add(new WAjaxControl(chb3, target));
		chb3.setActionOnChange(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				widget.setMandatory(chb3.isSelected());
			}
		});

	}

}
