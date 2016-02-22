package com.github.bordertech.wcomponents.showcase;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WLink;
import com.github.bordertech.wcomponents.WList;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.showcase.common.Showcase;
import com.github.bordertech.wcomponents.showcase.util.SourceUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * This component displays info about the widget.
 *
 * @author Jonathan Austin
 */
public class InfoPanel extends WPanel {

	private final WText txtInfo = new WText();

	private final WList relatedShowcases = new WList(WList.Type.STACKED);
	private final WList apiSuperLinks = new WList(WList.Type.STACKED);
	private final WList apiInterfaceLinks = new WList(WList.Type.STACKED);

	/**
	 * Creates a SourcePanel.
	 */
	public InfoPanel() {
		setBeanProperty(".");

		txtInfo.setEncodeText(false);
		add(txtInfo);
		add(new WHeading(HeadingLevel.H2, "Related"));
		add(relatedShowcases);
		relatedShowcases.setRepeatedComponent(new RelatedButtonPanel());
		relatedShowcases.setBeanProperty("relatedWidgets");

		add(new WHeading(HeadingLevel.H2, "API"));
		add(apiSuperLinks);

		add(new WHeading(HeadingLevel.H2, "Interfaces"));
		add(apiInterfaceLinks);

		apiSuperLinks.setRepeatedComponent(new ApiLink());
		apiInterfaceLinks.setRepeatedComponent(new ApiLink());

		apiSuperLinks.setSearchAncestors(false);
		apiInterfaceLinks.setSearchAncestors(false);

		relatedShowcases.setRowIdProperty("name");
		apiSuperLinks.setRowIdProperty("name");
		apiInterfaceLinks.setRowIdProperty("name");

	}

	public void addAjaxTargets(final AjaxTarget... targets) {
		RelatedButtonPanel panel = (RelatedButtonPanel) relatedShowcases.getRepeatedComponent();
		panel.addAjaxTargets(targets);
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			Showcase<?> item = getShowcase();
			// Javadoc for Info Text
			String rawSource = SourceUtil.getSourceByClassName(item.getWidgetClass().getName());
			String info = SourceUtil.extractJavaDoc(rawSource);
			txtInfo.setText(info);
			// API Links
			setupApiLinks(item.getWidgetClass());
			setInitialised(true);
		}
	}

	private Showcase<?> getShowcase() {
		return (Showcase) getBean();
	}

	private void setupApiLinks(final Class clazz) {
		List<Class> superClasses = new ArrayList<>();
		List<Class> interfaceClasses = new ArrayList<>();

		getApiClasses(superClasses, interfaceClasses, clazz);

		apiSuperLinks.setBean(superClasses);
		apiInterfaceLinks.setBean(interfaceClasses);
	}

	private void getApiClasses(final List<Class> superClasses, List<Class> interfaceClasses, final Class clazz) {
		if (clazz == null) {
			return;
		}

		if (!clazz.getPackage().getName().startsWith("com.github.bordertech.wcomponents")) {
			return;
		}

		// Classes
		superClasses.add(clazz);

		// Interfaces
		for (Class inter : clazz.getInterfaces()) {
			if (inter.getPackage().getName().startsWith("com.github.bordertech.wcomponents")) {
				interfaceClasses.add(inter);
			}
		}

		// Recurse up super classes
		getApiClasses(superClasses, interfaceClasses, clazz.getSuperclass());
	}

	public static class ApiLink extends WLink {

		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			if (!isInitialised()) {
				Class clazz = getApiClass();
				setText(clazz.getSimpleName());
				String htmlName = '/' + clazz.getName().replace('.', '/') + ".html";
				setUrl("http://bordertech.github.io/wcomponents/apidocs" + htmlName);
				setInitialised(true);
			}
		}

		private Class getApiClass() {
			return (Class) getBean();
		}

	}

	public static class RelatedButtonPanel extends WContainer {

		private final WButton button = new WButton();

		private final WAjaxControl ajax = new WAjaxControl(button);

		public RelatedButtonPanel() {
			button.setRenderAsLink(true);
			button.setBeanProperty("simpleName");
			add(button);
			add(ajax);

			button.setAction(new Action() {
				@Override
				public void execute(ActionEvent event) {
					ShowcaseApp.getInstance(RelatedButtonPanel.this).doDisplayShowcaseByWidgetClass(getRelatedWidget());
				}
			});
		}

		public void addAjaxTargets(final AjaxTarget... targets) {
			ajax.addTargets(targets);
		}

		private Class getRelatedWidget() {
			return (Class) getBean();
		}

	}

}
