package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WDropdown;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WPartialDateField;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.layout.FlowLayout;
import com.github.bordertech.wcomponents.showcase.PropertyContainer;
import com.github.bordertech.wcomponents.showcase.SampleContainer;
import java.util.ArrayList;
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
	public SampleContainer getSampleContainerInstance() {
		return new SamplePanel();
	}

	@Override
	public PropertyContainer getPropertyContainerInstance(final SampleContainer<FlowLayout> itemPanel) {
		SamplePanel sample = (SamplePanel) itemPanel;
		return new PropertiesPanel(sample.getPanel());
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends WPanel implements SampleContainer<FlowLayout> {

		private final WPanel panel;

		public SamplePanel() {

			WPanel box = new WPanel(Type.FEATURE);
			add(box);

			// SAMPLE-START
			panel = new WPanel();
			panel.setLayout(new FlowLayout(FlowLayout.Alignment.LEFT, 6, 6));
			for (int i = 1; i < 4; i++) {
				WPanel child = new WPanel(Type.BOX);
				child.add(new WText("Child " + i));
				panel.add(child);
			}
			// SAMPLE-FINISH

			box.add(panel);
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

		private final WDropdown drpAlignment = new WDropdown(FlowLayout.Alignment.values());
		private final WDropdown drpContentAlignment = new WDropdown();

		public PropertiesPanel(final WPanel panel) {
			super(panel);

			drpAlignment.setSelected(FlowLayout.Alignment.LEFT);

			List<FlowLayout.ContentAlignment> alignments = new ArrayList<>(Arrays.asList(FlowLayout.ContentAlignment.values()));
			alignments.add(0, null);
			drpContentAlignment.setOptions(alignments);
			drpContentAlignment.setSelected(FlowLayout.ContentAlignment.MIDDLE);

			addPropertyWidget("Alignment", drpAlignment);
			addPropertyWidget("Content alignment", drpContentAlignment);
		}

		@Override
		protected void configWidgetProperties(final FlowLayout widget) {
			super.configWidgetProperties(widget);
			int hgap = getHGap();
			int vgap = getVGap();
			FlowLayout.Alignment alignment = (FlowLayout.Alignment) drpAlignment.getSelected();
			FlowLayout.ContentAlignment contentAlignment = (FlowLayout.ContentAlignment) drpContentAlignment.getSelected();

			FlowLayout layout = new FlowLayout(alignment, hgap, vgap, contentAlignment);
			getPanel().setLayout(layout);
		}

	}

}
