package com.github.bordertech.wcomponents.showcase;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.Message;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WApplication;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WColumn;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WRow;
import com.github.bordertech.wcomponents.WTabSet;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.layout.FlowLayout;
import com.github.bordertech.wcomponents.showcase.common.PropertyContainer;
import com.github.bordertech.wcomponents.showcase.common.SampleContainer;
import com.github.bordertech.wcomponents.showcase.common.Showcase;
import java.util.Date;

/**
 * Showcase tope level applciation class.
 *
 * @author Jonathan Austin
 * @since 1.0.3
 */
public class ShowcaseApp extends WApplication implements MessageContainer {

	private final WMessages messages = new WMessages() {
		@Override
		public boolean isHidden() {
			return !hasMessages();
		}
	};

	private final WPanel root = new WPanel();

	private final WHeading sampleHeading = new WHeading(HeadingLevel.H2, "Sample");
	private final WPanel samplePanel = new WPanel();
	private final PickerPanel pickerPanel = new PickerPanel(this);
	private final WPanel propertiesPanel = new WPanel();
	private final InfoPanel infoPanel = new InfoPanel();
	private final SourcePanel sourcePanel = new SourcePanel();

	private final WContainer sampleHolder = new WContainer();
	private final WContainer propertiesHolder = new WContainer();

	/**
	 * Construct the controller.
	 */
	public ShowcaseApp() {

		// Source syntax highlighting
		addJsFile("/js/syntaxHighlight.js");
		addCssFile("/css/syntaxHighlight.css");

		setTitle("WComponents showcase");

		WPanel header = new WPanel(WPanel.Type.HEADER);
		header.add(new WHeading(HeadingLevel.H1, "WComponents showcase"));
		add(header);

		add(messages);

		root.setMargin(new Margin(18));
		root.setSearchAncestors(false);
		add(root);

		WRow row = new WRow(12);
		root.add(row);

		WColumn colPrev = new WColumn(5);
		WColumn colSample = new WColumn(60);
		WColumn colNext = new WColumn(5);
		WColumn colConfig = new WColumn(30);

		row.add(colPrev);
		row.add(colSample);
		row.add(colNext);
		row.add(colConfig);

		// Reset Button
		WButton resetButton = new WButton("reset");
		resetButton.setRenderAsLink(true);
		resetButton.setImage("/images/ic_refresh_black_18dp.png");
		resetButton.setToolTip("reset");
		resetButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doResetShowcase();
			}
		});

		// Refresh Button
		WButton refreshButton = new WButton("refresh");
		refreshButton.setRenderAsLink(true);
		refreshButton.setImage("/images/ic_sync_black_18dp.png");
		refreshButton.setToolTip("refresh");

		// Previous Button
		WButton prevButton = new WButton("prev");
		prevButton.setRenderAsLink(true);
		prevButton.setImage("/images/ic_chevron_left_black_18dp.png");
		prevButton.setToolTip("previous");
		prevButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doPreviousShowcase();
			}
		});

		// Next button
		WButton nextButton = new WButton("next");
		nextButton.setRenderAsLink(true);
		nextButton.setImage("/images/ic_chevron_right_black_18dp.png");
		nextButton.setToolTip("next");
		nextButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doNextShowcase();
			}
		});

		// Prev
		colPrev.setAlignment(WColumn.Alignment.CENTER);
		colPrev.add(prevButton);

		// Next
		colNext.setAlignment(WColumn.Alignment.CENTER);
		colNext.add(nextButton);

		// Sample
		colSample.add(samplePanel);
		samplePanel.add(sampleHeading);
		samplePanel.add(sampleHolder);

		sampleHeading.setMargin(new Margin(0, 0, 12, 0));

		// Button Panel
		WPanel buttonPanel = new WPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.Alignment.CENTER, 6, 0));
		buttonPanel.setMargin(new Margin(36, 0, 12, 0));
		buttonPanel.add(refreshButton);
		buttonPanel.add(resetButton);
		colSample.add(buttonPanel);

		// Config
		WTabSet tabs = new WTabSet();
		colConfig.add(tabs);

		tabs.addTab(pickerPanel, "Widgets", WTabSet.TabMode.CLIENT);
		tabs.addTab(propertiesPanel, "Properties", WTabSet.TabMode.CLIENT);
		tabs.addTab(infoPanel, "Info", WTabSet.TabMode.CLIENT);
		tabs.addTab(sourcePanel, "Source", WTabSet.TabMode.CLIENT);

		propertiesPanel.add(propertiesHolder);

		// Footer
		WPanel footer = new WPanel(WPanel.Type.FOOTER);
		footer.add(new WText(new Date().toString()));
		add(footer);

		// AJAX
		WAjaxControl prevAjax = new WAjaxControl(prevButton);
		WAjaxControl nextAjax = new WAjaxControl(nextButton);
		add(prevAjax);
		add(nextAjax);

		prevAjax.addTargets(new AjaxTarget[]{pickerPanel, samplePanel, propertiesPanel, infoPanel, sourcePanel});
		nextAjax.addTargets(new AjaxTarget[]{pickerPanel, samplePanel, propertiesPanel, infoPanel, sourcePanel});
		infoPanel.addAjaxTargets(pickerPanel, samplePanel, propertiesPanel, infoPanel, sourcePanel);

		pickerPanel.addAjaxTargets(samplePanel, propertiesPanel, infoPanel, sourcePanel);
	}

	@Override
	public WMessages getMessages() {
		return messages;
	}

	public void doPreviousShowcase() {
		Showcase<?> showcase = pickerPanel.getPrevShowcase();
		if (showcase != null) {
			doDisplayShowcase(showcase);
		}
	}

	public void doNextShowcase() {
		Showcase<?> showcase = pickerPanel.getNextShowcase();
		if (showcase != null) {
			doDisplayShowcase(showcase);
		}

	}

	public void doDisplayShowcaseByWidgetClass(final Class clazz) {
		Showcase<?> showcase = pickerPanel.selectShowcaseByWidgetClass(clazz);
		if (showcase != null) {
			doDisplayShowcase(showcase);
		}
	}

	public void doConfigWidget() {
		PropertyContainer<?> prop = getPropertyContainer();
		if (prop != null) {
			prop.configWidget();
		}
	}

	public void doResetShowcase() {
		SampleContainer<?> sample = getSampleContainer();
		if (sample != null) {
			sample.reset();
		}
		PropertyContainer<?> prop = getPropertyContainer();
		if (prop != null) {
			prop.reset();
		}
	}

	public void doDisplayShowcase(final Showcase<?> showcase) {

		removeShowcase();

		SampleContainer widgetContainer = showcase.getSampleContainerInstance();
		sampleHolder.add(widgetContainer);

		PropertyContainer propertyContainer = showcase.getPropertyContainerInstance(widgetContainer);
		propertiesHolder.add(propertyContainer);

		root.setBean(showcase);

		sampleHeading.setText(showcase.getWidgetClass().getSimpleName());

	}

	/**
	 * Override preparePaint in order to set up the resources on first access by a user.
	 *
	 * @param request the request being responded to.
	 */
	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			Showcase<?> showcase = pickerPanel.selectDefaultShowcase();
			if (showcase != null) {
				doDisplayShowcase(showcase);
			}
			setInitialised(true);
		}
	}

	private void removeShowcase() {
		samplePanel.reset();
		propertiesPanel.reset();
		infoPanel.reset();
		sourcePanel.reset();
	}

	private SampleContainer<?> getSampleContainer() {
		if (sampleHolder.getChildCount() > 0) {
			return (SampleContainer) sampleHolder.getChildAt(0);
		}
		return null;
	}

	private PropertyContainer<?> getPropertyContainer() {
		if (propertiesHolder.getChildCount() > 0) {
			return (PropertyContainer) propertiesHolder.getChildAt(0);
		}
		return null;
	}

	/**
	 * If a step error has occurred, then display an error message to the user.
	 */
	@Override
	public void handleStepError() {
		getMessages().addMessage(new Message(Message.WARNING_MESSAGE,
				"A request was made that is not in the expected sequence and the application has been refreshed to its current state."));
	}

	public static ShowcaseApp getInstance(final WComponent component) {
		return WebUtilities.getAncestorOfClass(ShowcaseApp.class, component);
	}

}
