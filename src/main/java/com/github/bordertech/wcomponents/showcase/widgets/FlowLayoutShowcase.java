package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WPartialDateField;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.layout.FlowLayout;
import com.github.bordertech.wcomponents.showcase.common.PropertyContainer;
import com.github.bordertech.wcomponents.showcase.common.SampleContainer;
import com.github.bordertech.wcomponents.showcase.common.TypeWDropdown;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class FlowLayoutShowcase extends AbstractShowcase<FlowLayout> {

	private static final List<Class> RELATED = Collections.unmodifiableList(Arrays.asList((Class) WPartialDateField.class));

	public FlowLayoutShowcase() {
		super(FlowLayout.class);
	}

	@Override
	public SampleContainer<FlowLayout> getSampleContainerInstance() {
		return new SamplePanel();
	}

	@Override
	public PropertyContainer<FlowLayout> getPropertyContainerInstance(final SampleContainer<FlowLayout> itemPanel) {
		SamplePanel sample = (SamplePanel) itemPanel;
		return new PropertiesPanel(itemPanel, sample.getPanel());
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends AbstractLayoutSample<FlowLayout> {

		private final WPanel panel;

		public SamplePanel() {

			// SAMPLE-START
			panel = new WPanel();
			panel.setLayout(new FlowLayout(FlowLayout.Alignment.LEFT));
			for (int i = 1; i < 4; i++) {
				WPanel child = new WPanel(Type.BOX);
				child.add(new WText("Child " + i));
				panel.add(child);
			}
			// SAMPLE-FINISH

			add(panel);
		}

		@Override
		public FlowLayout getWidget() {
			return (FlowLayout) panel.getLayout();
		}

		@Override
		public AjaxTarget getAjaxTarget() {
			return panel;
		}

		public WPanel getPanel() {
			return panel;
		}
	}

	public static class PropertiesPanel extends AbstractLayoutPropertyContainer<FlowLayout> {

		private final TypeWDropdown<FlowLayout.Alignment> drpAlignment = new TypeWDropdown<>(FlowLayout.Alignment.values());
		private final TypeWDropdown<FlowLayout.ContentAlignment> drpContentAlignment = new TypeWDropdown<>(FlowLayout.ContentAlignment.values());

		public PropertiesPanel(final SampleContainer<FlowLayout> sampleContainer, final WPanel panel) {
			super(sampleContainer, panel);

			drpAlignment.setSelected(FlowLayout.Alignment.LEFT);
			drpContentAlignment.setSelected(FlowLayout.ContentAlignment.MIDDLE);

			addPropertyWidget("Alignment", drpAlignment);
			addPropertyWidget("Content alignment", drpContentAlignment);
		}

		@Override
		protected void configWidgetProperties(final FlowLayout widget) {
			super.configWidgetProperties(widget);
			int hgap = getHGap();
			int vgap = getVGap();

			FlowLayout layout = new FlowLayout(drpAlignment.getSelected(), hgap, vgap, drpContentAlignment.getSelected());
			getPanel().setLayout(layout);
		}

	}

}
