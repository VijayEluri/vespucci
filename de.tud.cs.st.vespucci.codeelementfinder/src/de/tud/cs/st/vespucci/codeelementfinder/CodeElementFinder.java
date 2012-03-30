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
 *     Universitiät Darmstadt nor the names of its contributors may be used to
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
package de.tud.cs.st.vespucci.codeelementfinder;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IStatement;

/**
 * Class that is able to find ICodeElements in workspace
 * 
 * @author Olav Lenz
 */
public class CodeElementFinder {

	private static String PLUGIN_ID = "de.tud.cs.st.vespucci.codeelementfinder";

	protected static void processException(Exception e) {
		final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(),
				e);
		StatusManager.getManager().handle(is, StatusManager.LOG);
	}

	private static Map<ICodeElement, IMember> cache = new HashMap<ICodeElement, IMember>();

	private ICodeElementFoundProcessor codeElementFoundProcessor;
	private CodeElementSearchRequestor codeElementSearchRequestor;

	private Stack<ICodeElement> searchItems = new Stack<ICodeElement>();

	private ICodeElement currentSearchItem;
	private ICodeElement actualSearchItem;

	private Boolean found = false;
	private IMember foundMatch = null;

	/**
	 * Search an ICodeElement
	 * 
	 * @param sourceElement ICodeElement looking for
	 * @param project IProject searching in
	 * @param processor Processor that define what should be done when ICodeElement
	 *        is found or is not found
	 */
	public static void startSearch(ICodeElement sourceElement,
			IProject project, ICodeElementFoundProcessor processor) {

		if (cache.containsKey(sourceElement)) {
			IMember member = cache.get(sourceElement);
			if(member.exists()){
				processor.processFoundCodeElement(member);
				return;
			}
		}

		CodeElementFinder cef = new CodeElementFinder(sourceElement, project, processor);

		cef.nextStep();
	}

	private CodeElementFinder(ICodeElement codeElement, IProject project,
			ICodeElementFoundProcessor codeElementFoundProcessor) {
		this.codeElementFoundProcessor = codeElementFoundProcessor;
		actualSearchItem = codeElement;
		searchItems = Util.createSearchTryStack(codeElement);
	}

	/**
	 * Process the next step in the search process
	 */
	private void nextStep() {
		if (codeElementSearchRequestor != null) {
			found = codeElementSearchRequestor.getSearchResult();
			foundMatch = codeElementSearchRequestor.getSearchMatch();
		}
		if (found) {
			foundCodeElement();
		} else {
			nothingFoundYet();
		}
	}

	/**
	 * Is called when currentSearchItem was not found. Starts an new search with
	 * the next best search item
	 */
	private void nothingFoundYet() {
		if (searchItems.isEmpty()) {
			codeElementFoundProcessor.noMatchFound(currentSearchItem);
		} else {
			currentSearchItem = searchItems.pop();

			// checks if CodeElement was already found before
			if (cache.containsKey(currentSearchItem)) {
				IMember match = cache.get(currentSearchItem);
				if (match.exists()) {
					found = true;
					foundMatch = match;
					nextStep();
					return;
				}
			}
			searchForNextItem();
		}
	}

	/**
	 * Is called when a ICodeElement was found and delegate the found match to
	 * the correct CodeElementFoundProcessor method
	 */
	private void foundCodeElement() {
		if (!cache.containsKey(actualSearchItem)) {
			cache.put(actualSearchItem, foundMatch);
		}
		if (currentSearchItem instanceof IStatement) {
			IStatement statement = (IStatement) currentSearchItem;
			codeElementFoundProcessor.processFoundCodeElement(foundMatch,
					statement.getLineNumber());
		} else {
			codeElementFoundProcessor.processFoundCodeElement(foundMatch);
		}
	}

	/**
	 * Start searching for the currentSearchItem
	 * 
	 */
	private void searchForNextItem() {

		codeElementSearchRequestor = new CodeElementSearchRequestor(
				currentSearchItem, new IAction() {

					@Override
					public void run() {
						// When the search process is finished nextSearchStep();
						// is called
						nextStep();
					}
				});

		SearchEngine searchEngine = new SearchEngine();
		try {
			SearchPattern searchPattern = SearchPattern.createPattern(
					Util.createStringPattern(currentSearchItem),
					Util.createSearchFor(currentSearchItem),
					IJavaSearchConstants.DECLARATIONS,
					SearchPattern.R_EXACT_MATCH);
			IJavaSearchScope javaSearchScope = SearchEngine
					.createWorkspaceScope();
			searchEngine.search(searchPattern,
					new SearchParticipant[] { SearchEngine
					.getDefaultSearchParticipant() }, javaSearchScope,
					codeElementSearchRequestor, null);
		} catch (CoreException e) {
			processException(e);
		}
	}
}