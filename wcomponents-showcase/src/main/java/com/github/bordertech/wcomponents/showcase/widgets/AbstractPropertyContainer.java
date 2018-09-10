package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.AjaxTrigger;
import com.github.bordertech.wcomponents.Input;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WField;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.showcase.common.PropertyContainer;
import com.github.bordertech.wcomponents.showcase.common.SampleContainer;
import com.github.bordertech.wcomponents.validation.ValidatingAction;

/**
 *
 * @author jonathan
 */
public abstract class AbstractPropertyContainer<T> extends WPanel implements PropertyContainer<T>, MessageContainer {

	private final WMessages messages = new WMessages() {
		@Override
		public boolean isHidden() {
			return !hasMessages();
		}
	};

	private final SampleContainer<T> sampleContainer;

	private final WFieldLayout fieldLayout = new WFieldLayout();

	private final WContainer ajaxContainer = new WContainer();

	public AbstractPropertyContainer(final SampleContainer<T> sampleContainer) {
		this.sampleContainer = sampleContainer;

		add(messages);
		add(fieldLayout);
		add(ajaxContainer);
		//	TODO HtmlClass
	}

	@Override
	public SampleContainer<T> getSampleContainer() {
		return sampleContainer;
	}

	@Override
	public void configWidget() {
		T sampleWidget = getSampleWidget();
		configWidgetProperties(sampleWidget);
	}

	abstract protected void configWidgetProperties(final T widget);

	protected WField addPropertyWidget(final String label, final AjaxTrigger propertyTrigger) {

		// Add to field layout
		WFieldLayout layout = getFieldLayout();
		WField field = layout.addField(label, propertyTrigger);
		field.setInputWidth(100);

		// Setup AJAX
		WAjaxControl ajax = new WAjaxControl(propertyTrigger, getSampleAjaxTarget());
		ajax.addTarget(getMessages());
		getAjaxContainer().add(ajax);

		// Validation action
		Action validation = new ValidatingAction(getMessages().getValidationErrors(), propertyTrigger) {
			@Override
			public void executeOnValid(final ActionEvent event) {
				configWidget();
			}
		};
		((Input) propertyTrigger).setActionOnChange(validation);

		return field;
	}

	protected WFieldLayout getFieldLayout() {
		return fieldLayout;
	}

	protected WContainer getAjaxContainer() {
		return ajaxContainer;
	}

	protected T getSampleWidget() {
		return getSampleContainer().getWidget();
	}

	protected AjaxTarget getSampleAjaxTarget() {
		return getSampleContainer().getAjaxTarget();
	}

	@Override
	public WMessages getMessages() {
		return messages;
	}

}
