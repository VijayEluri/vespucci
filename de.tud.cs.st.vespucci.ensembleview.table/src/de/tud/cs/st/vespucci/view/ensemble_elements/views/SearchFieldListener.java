/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Technology Group
 *   Department of Computer Science
 *   Technische Universitiät Darmstadt
 *   All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Technology Group Group or Technische
 *     Universität Darmstadt nor the names of its contributors may be used to
 *     endorse or promote products derived from this software without specific
 *     prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.st.vespucci.view.ensemble_elements.views;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

/**
 * Listener which add and remove an SearchFilter to an tableView
 * when the modifyText event is notified
 * 
 * @author Olav Lenz
 * @author Patrick Gottschaemmer
 */
public class SearchFieldListener implements ModifyListener {

	private TableViewer tableViewer;
	private Display display;
	private SearchFilter filter = null;
	private String text = null;
	private int column;

	public SearchFieldListener(TableViewer tableView, int column, Display display){
		this.tableViewer = tableView;
		this.column = column;
		this.display = display;
	}

	private Timer timer;

	@Override
	public void modifyText(final ModifyEvent e) {
		if (timer != null){
			timer.cancel();
		}

		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				display.syncExec(new Runnable() {

					@Override
					public void run() {
						if (filter != null) {
							tableViewer.removeFilter(filter);
						}

						text = ((Text) e.getSource()).getText();
						if (!text.equals("")){
							filter = new SearchFilter(column, text);
							tableViewer.addFilter(filter);
						}
					}
				});
			}
		}, 250);

	}
}
