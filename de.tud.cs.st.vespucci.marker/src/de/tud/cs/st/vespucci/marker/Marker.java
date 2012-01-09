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
package de.tud.cs.st.vespucci.marker;

import java.util.Set;

import org.eclipse.core.resources.IProject;

import de.tud.cs.st.vespucci.diagram.processing.IResultProcessor;
import de.tud.cs.st.vespucci.interfaces.IViolation;
import de.tud.cs.st.vespucci.interfaces.IViolationReport;
import de.tud.cs.st.vespucci.utilities.Util;

/**
 * Mark Violations in a given project.
 */
public class Marker implements IResultProcessor {

	private IProject project;
	private Set<IViolation> violations;

	@Override
	public void processResult(Object result, IProject project) {
		this.project = project;
		IViolationReport violationReport = Util.adapt(result, IViolationReport.class);
		if (violationReport != null){
			this.violations = violationReport.getViolations();
			if (violations != null){
				markViolations();
			}
		}
	}

	private void markViolations() {
		for (IViolation violation : violations) {
			if (violation.getSourceElement() != null){
				CodeElementFinder.search(violation.getSourceElement(), violation.getDescription(), project);
			}
			if (violation.getTargetElement() != null){
				CodeElementFinder.search(violation.getTargetElement(), violation.getDescription(), project);
			}			
		}
	}	

	@Override
	public boolean isInterested(Class<?> resultClass) {
		return IViolationReport.class.equals(resultClass);
	}

	@Override
	public void cleanUp() {
		CodeElementMarker.deleteAllMarkers();
	}

}
