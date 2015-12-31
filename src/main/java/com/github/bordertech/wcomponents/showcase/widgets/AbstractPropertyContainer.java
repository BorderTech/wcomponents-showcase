package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.showcase.PropertyContainer;
import com.github.bordertech.wcomponents.validation.ValidatingAction;

/**
 *
 * @author jonathan
 */
public abstract class AbstractPropertyContainer<T extends WComponent> extends WPanel implements PropertyContainer, MessageContainer {

	private final WMessages messages = new WMessages();

	private final T widget;

	private final AjaxTarget ajaxTarget;

	private final WFieldLayout fieldLayout = new WFieldLayout();

	private final WContainer ajaxContainer = new WContainer();

	public AbstractPropertyContainer(final T widget, final AjaxTarget target) {
		this.widget = widget;
		this.ajaxTarget = target;

		add(messages);
		add(fieldLayout);
		add(ajaxContainer);

		// IdName
		final WTextField txtIdName = new WTextField();
		txtIdName.setMaxLength(2);
		fieldLayout.addField("Id name", txtIdName);
		ajaxContainer.add(new WAjaxControl(txtIdName, new AjaxTarget[]{messages, target}));
		txtIdName.setActionOnChange(new ValidatingAction(messages.getValidationErrors(), txtIdName) {
			@Override
			public void executeOnValid(final ActionEvent event) {
				getWidget().setIdName(txtIdName.getValue());
			}
		});

		// Tooltip
		final WTextField txtTooltip = new WTextField();
		fieldLayout.addField("Tooltip", txtTooltip);
		ajaxContainer.add(new WAjaxControl(txtTooltip, target));
		txtTooltip.setActionOnChange(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				getWidget().setToolTip(txtTooltip.getValue());
			}
		});

		//	TODO HtmlClass
	}

	protected WFieldLayout getFieldLayout() {
		return fieldLayout;
	}

	protected WContainer getAjaxContainer() {
		return ajaxContainer;
	}

	protected T getWidget() {
		return widget;
	}

	protected AjaxTarget getAjaxTarget() {
		return ajaxTarget;
	}

	@Override
	public WMessages getMessages() {
		return messages;
	}

}
