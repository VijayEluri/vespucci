/******************************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/
package de.tud.cs.st.vespucci.vespucci_model.diagram.sheet;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.util.StringStatics;
import org.eclipse.gmf.runtime.common.ui.util.StatusLineUtil;
import org.eclipse.gmf.runtime.diagram.ui.properties.sections.AbstractModelerPropertySection;
import org.eclipse.gmf.runtime.diagram.ui.properties.views.TextChangeHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * A Changed Copy of AbstractBasicTextPropertySection
 * (org.eclipse.gmf.runtime.diagram
 * .ui.properties.sections.AbstractBasicTextPropertySection)
 * 
 * 
 * 
 * Original version by:
 * 
 * @author natalia balaba
 * 
 *         Changed by:
 * @author MalteV
 * @author BenjaminL
 * @author DominicS
 */
public abstract class ChangedAbstractBasicTextPropertySection extends
		AbstractModelerPropertySection {

	private final int QUERY_TAB_HEIGHT_SHIFT = 35;

	private final int QUERY_TAB_WIDTH_SHIFT = 45;

	// styled text widget to display and set value of the property
	private StyledText textWidget;

	// parent parent ... parent composite for the size of the textfield
	private Composite scrolledParent;

	private Composite sectionComposite;

	private int startHeight = 15;

	public void setScrolledParent(Composite scrolledParent) {
		this.scrolledParent = scrolledParent;
	}

	Listener resizeLinstener = new Listener() {
		public void handleEvent(Event e) {
			updateHeight();
		}
	};

	/**
	 * @return - name of the property to place in the label widget
	 */
	abstract protected String getPropertyNameLabel();

	/**
	 * Set property value for the given object
	 * 
	 * @param object
	 *            - owner of the property
	 * @param value
	 *            - new value
	 */
	abstract protected void setPropertyValue(EObject object, Object value);

	/**
	 * @return - string representation of the property value
	 */
	abstract protected String getPropertyValueString();

	/**
	 * @return - title of the command which will be executed to set the property
	 */
	protected abstract String getPropertyChangeCommandName();

	/**
	 * A helper to listen for events that indicate that a text field has been
	 * changed.
	 */
	private TextChangeHelper listener = new TextChangeHelper() {
		boolean textModified = false;

		/**
		 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
		 */
		public void handleEvent(Event event) {
			switch (event.type) {
			case SWT.KeyDown:
				doSyntaxHighlighting();
				
				textModified = true;
				if (event.character == SWT.CR) {
					getPropertyValueString();
				}
				break;
			case SWT.FocusOut:
				textChanged((Control) event.widget);
				break;
			}
		}

		public void textChanged(Control control) {
			if (textModified) {
				// clear error message
				IWorkbenchPart part = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getActivePart();
				StatusLineUtil.outputErrorMessage(part, StringStatics.BLANK);
				
				setPropertyValue(control);
				textModified = false;
			}
		}
		
		/**
		 * Returns an array of StyleRange that makes the characters at
		 * the positions first and second of a StyledText appear in bold
		 * print.
		 * 
		 * @author DominicS
		 * @param first First bold character
		 * @param second Second bold character
		 * @return Array of StyleRanges that makes characters at first and second bold
		 */
		private StyleRange[] getBoldStyleRanges(int first, int second)
		{
			if (first > second)
			{
				int temp = second;
				second = first;
				first = temp;
			}
			
			// Emphasis style:
			// blue, bold and underlined
			
			Color blue = Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
			StyleRange r1 = new StyleRange(first, 1, blue, null, SWT.BOLD);
			StyleRange r2 = new StyleRange(second, 1, blue, null, SWT.BOLD);
			r1.underline = true;
			r2.underline = true;
			
			return new StyleRange[] {r1, r2};
		}
		
		/**
		 * Resets the whole text style in the StyledText component to
		 * its "normal" state.
		 */
		private void resetStyle()
		{
			Color black = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
			textWidget.setStyleRange(new StyleRange(0, textWidget.getCharCount(), black, null, SWT.NORMAL));
		}
		
		/**
		 * Performs syntax highlighting of the query properties tab.
		 * So far, bracket emphasis is implemented.
		 * 
		 * @author DominicS
		 */
		private void doSyntaxHighlighting()
		{
			// first, set everything to black and normal
			resetStyle();
			
			int offset = textWidget.getCaretOffset();
			// Check if caret is at first position
			// => no syntax highlighting
			if (offset == 0)
			{
				// do not highlight anything:
				return;
			}
			
			int size = textWidget.getCharCount();
			char currentChar = textWidget.getText().charAt(offset-1);
			
			if (currentChar == '(')
			{
				int intend = 0;
				for (int i = offset; i < size; i++)
				{
					if (textWidget.getText().charAt(i) == '(') {
						intend++;
					}
					
					if (textWidget.getText().charAt(i) == ')'
						&& intend == 0)
					{
						// Highlight both brackets						
						textWidget.setStyleRanges(getBoldStyleRanges(offset-1, i));
						return;
					}					

					if (textWidget.getText().charAt(i) == ')') {
						intend--;
					}
				}
			}
			
			else if (currentChar == ')' && offset > 1)
			{
				int intend = 0;
				for (int i = offset-2; i >= 0; i--)
				{
					if (textWidget.getText().charAt(i) == ')') {
						intend++;
					}
					
					if (textWidget.getText().charAt(i) == '('
						&& intend == 0)
					{
						// Highlight both brackets	
						textWidget.setStyleRanges(getBoldStyleRanges(offset-1, i));
						return;
					}					

					if (textWidget.getText().charAt(i) == '(') {
						intend--;
					}
				}
			}
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.views.properties.tabbed.ISection#createControls(org.eclipse
	 * .swt.widgets.Composite,
	 * org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent,
			TabbedPropertySheetPage aTabbedPropertySheetPage) {
		doCreateControls(parent, aTabbedPropertySheetPage);
	}

	/**
	 * Creates the GUI <code>Control</code> for this text property section
	 * 
	 * @param parent
	 *            parent <code>Composite</code>
	 * @param aTabbedPropertySheetPage
	 *            <code>TabbedPropertySheetPage</code>
	 * @see org.eclipse.gmf.runtime.common.ui.properties.ISection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.gmf.runtime.common.ui.properties.TabbedPropertySheetPage)
	 */
	public void doCreateControls(Composite parent,
			TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		sectionComposite = getWidgetFactory().createFlatFormComposite(parent);
		textWidget = createTextWidget(sectionComposite);
		scrolledParent = parent;
		for (;;) {
			// TODO: There must be a nicer way for redraw the text widget then
			// to say the first scrolledcomposite .layout()
			// the scrolledParent should be "main" composite for the query tab
			if (scrolledParent instanceof ScrolledComposite) {
				break;
			}
			if (scrolledParent.getParent() == null)
				break;
			scrolledParent = scrolledParent.getParent();

		}

		scrolledParent.addListener(SWT.Resize, resizeLinstener);
		updateHeight();
		startTextWidgetEventListener();
	}

	/**
	 * Start listening to the text widget events
	 */
	protected void startTextWidgetEventListener() {
		if (!isReadOnly()) {
			getListener().startListeningTo(getTextWidget());
			getListener().startListeningForEnter(getTextWidget());
		}
	}

	/**
	 * Stop listening to text widget events
	 */
	protected void stopTextWidgetEventListener() {
		if (!isReadOnly())
			getListener().stopListeningTo(getTextWidget());
	}

	/**
	 * Instantiate a text widget
	 * 
	 * @param parent
	 *            - parent composite
	 * @return - a text widget to display and edit the property
	 */
	protected StyledText createTextWidget(Composite parent) {
		getSectionComposite().getSize();
		
		StyledText st = new StyledText(
			parent,
			SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL
		);
		
		// Use a monospaced font
		Font mono = new Font(parent.getDisplay(), "Courier New", 10, SWT.NONE);
		st.setFont(mono);
		
		FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		data.height = startHeight;
		st.setLayoutData(data);
		
		if (isReadOnly())
			st.setEditable(false);
		
		return st;

	}

	private int getHeight() {
		return this.scrolledParent.getSize().y - QUERY_TAB_HEIGHT_SHIFT;
	}

	private int getWidth() {
		return this.scrolledParent.getSize().x - QUERY_TAB_WIDTH_SHIFT;
	}

	/**
	 * calculates the new size of the widget and updates it
	 */
	private void updateHeight() {
		if (getTextWidget() != null && !getTextWidget().isDisposed()) {
			getTextWidget().setVisible(false);
			scrolledParent.setVisible(false);
			FormData data = new FormData();
			data.left = new FormAttachment(0, 0);
			data.right = new FormAttachment(100, 0);
			data.top = new FormAttachment(0, 0);
			data.height = getHeight();
			data.width = getWidth();
			getTextWidget().setLayoutData(data);
			int HEIGHTS_SCROLLLINE = 30;
			Composite com = getSectionComposite();
			// TODO: there must be a nice way to update the heights and widths
			// of the textwidget and its parents
			for (;;) {

				if (com instanceof ScrolledComposite) {
					break;
				}
				if (com.getParent() == null)
					break;
				com.setSize(getWidth(), getHeight() + HEIGHTS_SCROLLLINE);
				com = com.getParent();

			}
			com.layout();
			getTextWidget().setVisible(true);
			scrolledParent.setVisible(true);
		}
	}

	/**
	 * returns as an array the property name
	 * 
	 * @return - array of strings where each describes a property name one per
	 *         property. The strings will be used to calculate common indent
	 *         from the left
	 */
	protected String[] getPropertyNameStringsArray() {
		return new String[] { getPropertyNameLabel() };
	}

	/**
	 * User pressed Enter key after editing text field - update the model
	 * 
	 * @param control
	 *            <code>Control</code>
	 */
	protected synchronized void setPropertyValue(Control control) {
		final Object value = computeNewPropertyValue();
		ArrayList<ICommand> commands = new ArrayList<ICommand>();
		for (Iterator<?> it = getEObjectList().iterator(); it.hasNext();) {
			final EObject next = (EObject) it.next();
			commands.add(createCommand(getPropertyChangeCommandName(), next,
					new Runnable() {

						public void run() {
							setPropertyValue(next, value);
						}

					}));
		}
		executeAsCompositeCommand(getPropertyChangeCommandName(), commands);
		refresh();
	}

	/**
	 * @return - the default implementation returns contents of the text widget
	 *         as a new value for the property. Subclasses can could be
	 *         override.
	 */
	protected Object computeNewPropertyValue() {
		return getTextWidget().getText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#dispose()
	 */
	public void dispose() {
		stopTextWidgetEventListener();
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	public void refresh() {
		getListener().startNonUserChange();
		try {
			executeAsReadAction(new Runnable() {

				public void run() {
					refreshUI();
				}
			});
		} finally {
			getListener().finishNonUserChange();
		}
	}

	/**
	 * Refresh UI body - refresh will surround this with read action block
	 */
	protected void refreshUI() {
		getTextWidget().setText(getPropertyValueString());
	}

	/**
	 * @return Returns the listener.
	 */
	protected TextChangeHelper getListener() {
		return listener;
	}

	/**
	 * @return Returns the textWidget.
	 */
	protected StyledText getTextWidget() {
		return textWidget;
	}

	/**
	 * @return Returns the sectionComposite.
	 */
	public Composite getSectionComposite() {
		return sectionComposite;
	}
}