package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WPartialDateField;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.layout.BorderLayout;
import com.github.bordertech.wcomponents.showcase.common.PropertyContainer;
import com.github.bordertech.wcomponents.showcase.common.SampleContainer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class BorderLayoutShowcase extends AbstractShowcase<BorderLayout> {

	private static final List<Class> RELATED = Collections.unmodifiableList(Arrays.asList((Class) WPartialDateField.class));

	public BorderLayoutShowcase() {
		super(BorderLayout.class);
	}

	@Override
	public SampleContainer getSampleContainerInstance() {
		return new SamplePanel();
	}

	@Override
	public PropertyContainer getPropertyContainerInstance(final SampleContainer<BorderLayout> itemPanel) {
		SamplePanel sample = (SamplePanel) itemPanel;
		return new PropertiesPanel(sample, sample.getPanel());
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends AbstractLayoutSample<BorderLayout> {

		private final WPanel panel;

		public SamplePanel() {

			// SAMPLE-START
			panel = new WPanel();
			panel.setLayout(new BorderLayout());
			for (BorderLayout.BorderLayoutConstraint location : BorderLayout.BorderLayoutConstraint.values()) {
				WPanel child = new WPanel(Type.BOX);
				child.add(new WText(location.name()));
				panel.add(child, location);
			}
			// SAMPLE-FINISH

			add(panel);
		}

		@Override
		public BorderLayout getWidget() {
			return (BorderLayout) panel.getLayout();
		}

		@Override
		public AjaxTarget getAjaxTarget() {
			return panel;
		}

		public WPanel getPanel() {
			return panel;
		}
	}

	public static class PropertiesPanel extends AbstractLayoutPropertyContainer<BorderLayout> {

		public PropertiesPanel(final SampleContainer<BorderLayout> sampleContainer, final WPanel panel) {
			super(sampleContainer, panel);
		}

		@Override
		protected void configWidgetProperties(final BorderLayout widget) {
			super.configWidgetProperties(widget);
			int hgap = getHGap();
			int vgap = getVGap();
			BorderLayout layout = new BorderLayout(hgap, vgap);
			getPanel().setLayout(layout);
		}

	}

}
