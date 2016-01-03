package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.AjaxTrigger;
import com.github.bordertech.wcomponents.Input;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.Request;
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

	private final WMessages messages = new WMessages() {
		@Override
		public boolean isHidden() {
			return !hasMessages();
		}
	};

	private final T widget;

	private final AjaxTarget ajaxTarget;

	private final WFieldLayout fieldLayout = new WFieldLayout();

	private final WContainer ajaxContainer = new WContainer();

	// Tooltip
	private final WTextField txtTooltip = new WTextField();

	public AbstractPropertyContainer(final T widget, final AjaxTarget target) {
		this.widget = widget;
		this.ajaxTarget = target;

		add(messages);
		add(fieldLayout);
		add(ajaxContainer);

		// Tooltip
		addPropertyWidget("Tooltip", txtTooltip);

		// IdName - Not such a good idea to change IDs on the fly
		//	TODO HtmlClass
	}

	protected void configWidgetProperties(final T widget) {
		widget.setToolTip(txtTooltip.getValue());
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		T widget = getWidget();
		configWidgetProperties(widget);
	}

	protected void addPropertyWidget(final String label, final AjaxTrigger propertyTrigger) {

		// Add to field layout
		WFieldLayout layout = getFieldLayout();
		layout.addField(label, propertyTrigger);

		// Setup AJAX
		WAjaxControl ajax = new WAjaxControl(propertyTrigger, getAjaxTarget());
		ajax.addTarget(getMessages());
		getAjaxContainer().add(ajax);

		// Validation action
		Action validation = new ValidatingAction(getMessages().getValidationErrors(), propertyTrigger) {
			@Override
			public void executeOnValid(ActionEvent event) {
				T widget = getWidget();
				configWidgetProperties(widget);
			}
		};
		((Input) propertyTrigger).setActionOnChange(validation);
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
