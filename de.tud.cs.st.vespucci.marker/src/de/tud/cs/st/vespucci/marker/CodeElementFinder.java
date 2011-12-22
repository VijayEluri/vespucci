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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.corext.util.JavaModelUtil;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;
import de.tud.cs.st.vespucci.interfaces.IStatement;

public class CodeElementFinder {

	private static String PLUGIN_ID = "de.tud.cs.st.vespucci.marker";
	
	protected static void search(ICodeElement sourceElement, String violationMessage, IProject project){
		// Debug Info
		System.out.println("-----Start Seraching");
		foundMatch(null, sourceElement, violationMessage, project);
	}
		
	private static void search(SearchPattern searchPattern, IJavaSearchScope javaSearchScope, final ICodeElement sourceElement, final String string, final IProject project) {
		
		SearchRequestor requestor = new SearchRequestor() {
			private boolean sucess = false;
			
			@Override
			public void acceptSearchMatch(SearchMatch match) throws CoreException {
				sucess = foundMatch(match, sourceElement, string, project);
			}		
			
			@Override
			public void endReporting(){
				if (!sucess){
					notfoundMatch(sourceElement, string, project);
				}
			}
			
		};
		
	    // Search
	    SearchEngine searchEngine = new SearchEngine();
	    try {
			searchEngine.search(searchPattern, new SearchParticipant[] {SearchEngine.getDefaultSearchParticipant()}, javaSearchScope, requestor, null);
		} catch (CoreException e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
	}

	protected static void notfoundMatch(ICodeElement sourceElement, String string, IProject project) {
		// Debug Info
		System.out.println("Didn't find anything");
		// TODO: this case will be use when we are searching for inner classes or anonym classes
	}

	protected static boolean foundMatch(SearchMatch match, ICodeElement sourceElement, String violationMessage, IProject project) {
		// Initial case: nothing found yet. We start so search
		if (match == null){
			
			// Debug Info
			System.out.println("null");
			
			IJavaSearchScope javaSearchScope = SearchEngine.createWorkspaceScope();
			SearchPattern searchPattern = SearchPattern.createPattern(sourceElement.getPackageIdentifier(), IJavaSearchConstants.PACKAGE, IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
						
			search(searchPattern, javaSearchScope, sourceElement, violationMessage, project);
			
			return true;
		}
		// Find a packageFragment, now looking for the SimpleClassName
		if (match.getElement() instanceof IPackageFragment){
			IPackageFragment packageFragment = (IPackageFragment) match.getElement();
			
			// Debug Info
			System.out.println("packageFragment");
			
			//TODO: At the moment no inner classes or anonym classes are supported
			SearchPattern searchPattern = SearchPattern.createPattern(sourceElement.getSimpleClassName(), IJavaSearchConstants.CLASS, IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);		
			
			IJavaElement[] je = new IJavaElement[1];
			je[0] = packageFragment;
			IJavaSearchScope javaSearchScope = SearchEngine.createJavaSearchScope(je);
			
			search(searchPattern, javaSearchScope, sourceElement, violationMessage, project);
			
			return true;
		}
		// Find a class declaration and we were looking for it
		if ((match.getElement() instanceof IType) && (sourceElement instanceof IClassDeclaration)){
			IType type = (IType) match.getElement();
			IClassDeclaration classDeclaration = (IClassDeclaration) sourceElement;
	
			// Debug Info
			System.out.println("IType");
			
			// check if the match was what we were searching for
			if (type.getKey().equals(classDeclaration.getTypeQualifier())){
				CodeElementMarker.markIMember((IMember) match.getElement(), violationMessage, project);
				return true;
			}
						
			// return false, if the match was not what we were searching for
			return false;
		}	
		// Find a class declaration and we were looking for a IMethodDeclaration
		if ((match.getElement() instanceof IType) && (sourceElement instanceof IMethodDeclaration)){
			IType type = (IType) match.getElement();
			IMethodDeclaration methodeDeclaration = (IMethodDeclaration) sourceElement;
				
			// Debug Info
			System.out.println("IType");
			
			SearchPattern searchPattern = SearchPattern.createPattern(methodeDeclaration.getMethodName(), IJavaSearchConstants.METHOD, IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
			
			IJavaElement[] je = new IJavaElement[1];
			je[0] = type;
			IJavaSearchScope javaSearchScope = SearchEngine.createJavaSearchScope(je);
			
			search(searchPattern, javaSearchScope, sourceElement, violationMessage, project);
			
			return true;
		}
		// Find a class declaration and we were looking for a IFieldDeclaration
		if ((match.getElement() instanceof IType) && (sourceElement instanceof IFieldDeclaration)){
			IType type = (IType) match.getElement();
			IFieldDeclaration fieldDeclaration = (IFieldDeclaration) sourceElement;
			
			// Debug Info
			System.out.println("IType");	
			
			SearchPattern searchPattern = SearchPattern.createPattern(fieldDeclaration.getFieldName(), IJavaSearchConstants.FIELD, IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
			
			IJavaElement[] je = new IJavaElement[1];
			je[0] = type;
			IJavaSearchScope javaSearchScope = SearchEngine.createJavaSearchScope(je);
			
			search(searchPattern, javaSearchScope, sourceElement, violationMessage, project);
			
			return true;
		}
		// Find a class declaration and we were looking for a IStatement
		if ((match.getElement() instanceof IType) && (sourceElement instanceof IStatement)){
			IStatement sourceCodeElement = (IStatement) sourceElement;
			
			// Debug Info
			System.out.println("IType");
			
			CodeElementMarker.markIStatement((IMember) match.getElement(), violationMessage, sourceCodeElement.getLineNumber(), project);
			return true;
		}
		// Find a method and we were looking for a IMethodDeclaration
		if ((match.getElement() instanceof IMethod) && (sourceElement instanceof IMethodDeclaration)){
			IMethodDeclaration methodDeclaration = (IMethodDeclaration) sourceElement;
			IMethod method = (IMethod) match.getElement();
			
			// Debug Info		
			System.out.println("IMehtod");
					
			//TODO: At this point the typeQulifier of the ReturnTyp and the parameters has to be checked  
			
			try {
				IType declaringType = method.getDeclaringType();
				
				// check if the returnType is the expected
				if (methodDeclaration.getReturnTypeQualifier().equals(createTypQualifier(method.getReturnType(), declaringType))){
					// check if all parameterTypes are equal the expected
					String[] parameterTypes = method.getParameterTypes();
					String[] expectedParameterTypes = methodDeclaration.getParameterTypeQualifiers();
					boolean equal = true;
					for (int i = 0; i < parameterTypes.length; i++){
						if (!expectedParameterTypes[i].equals(createTypQualifier(parameterTypes[i], declaringType))){
							equal = false;
							break;
						}
					}
					if (equal){
						CodeElementMarker.markIMember((IMember) match.getElement(), violationMessage, project);
						return true;
					}
				}
			} catch (JavaModelException e) {
				final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
			
			
			// return false, if the match was not the right hit	
			return false;
			
//			// Old Code --has to be replaced--
//			try {
//				if (method.getReturnType().equals(methodDeclaration.getReturnTypeQualifier())){
//					String[] mParaTypes = method.getParameterTypes();
//					String[] meParaTypes = methodDeclaration.getParameterTypeQualifiers();
//					
//					if (meParaTypes.length == mParaTypes.length){
//						boolean equalParameterTypes = true;
//						for (int i = 0; i < mParaTypes.length; i++){
//							if (!meParaTypes[i].equals(mParaTypes[i])){
//								equalParameterTypes = false;
//							}
//						}
//						if (equalParameterTypes){
//							CodeElementMarker.markIMember((IMember) match.getElement(), violationMessage, project);
//							return true;
//						}
//					}
//				}
//			} catch (JavaModelException e) {
//				final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
//				StatusManager.getManager().handle(is, StatusManager.LOG);
//			}
//			// --- Old Code
//		
//			return false;
		}
		// find a field declaration and we were looking for a IFieldDeclaration
		if ((match.getElement() instanceof IField) && (sourceElement instanceof IFieldDeclaration)){
			IField field = (IField) match.getElement();
			IFieldDeclaration fieldDeclaration = (IFieldDeclaration) sourceElement;
			
			// Debug Info		
			System.out.println("IField");
			
			try {
				if (fieldDeclaration.getTypeQualifier().equals(createTypQualifier(field.getTypeSignature(), field.getDeclaringType()))){
					CodeElementMarker.markIMember((IMember) match.getElement(), violationMessage, project);
					return true;
				}
			} catch (JavaModelException e) {
				final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
			
			// return false, if the match was not what we were searching for
			return false;
		}
		return false;
	}
	
	private static Map<String,String> primitiveTypeTable;
	
	private static String createTypQualifier(String signatur, IType declaringType) {
		if (primitiveTypeTable == null){
			primitiveTypeTable = new HashMap<String, String>();
			primitiveTypeTable.put("byte", "B");
			primitiveTypeTable.put("char", "C");
			primitiveTypeTable.put("double", "D");
			primitiveTypeTable.put("float", "F");
			primitiveTypeTable.put("int", "I");
			primitiveTypeTable.put("long", "J");
			primitiveTypeTable.put("short", "S");
			primitiveTypeTable.put("boolean", "Z");
		}
		
		String typeQualifier = "";
		
		// filter '[' we need them later
		int dimensionOfArray = 0;
		for (int i = 0; i < signatur.length(); i++){
			if (signatur.charAt(i) == '[' ){
				dimensionOfArray++;
			}else{
				break;
			}
		}
		
		String arraySymbols = signatur.substring(0, dimensionOfArray);
		String innerTypQualifier = signatur.substring(dimensionOfArray);
				
		try {
			innerTypQualifier = JavaModelUtil.getResolvedTypeName(innerTypQualifier, declaringType);
			
			if (innerTypQualifier.contains(".")){
				// . --> /
				innerTypQualifier = innerTypQualifier.replace('.', '/');
				// add L at the beginning
				innerTypQualifier = "L" + innerTypQualifier.replace('.', '/');
			}else{
				innerTypQualifier = primitiveTypeTable.get(innerTypQualifier);
			}			
			
			// add the arraySymbols at the beginning and ';' at the end
			typeQualifier = arraySymbols + innerTypQualifier + ";";
			
		} catch (Exception e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
		return typeQualifier;
	}
	
}
