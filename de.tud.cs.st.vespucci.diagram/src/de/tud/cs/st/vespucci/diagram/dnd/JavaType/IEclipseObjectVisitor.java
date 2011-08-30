package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.util.ISourceAttribute;

/**
 * This interface enforces to implement the polymorphic visit-method by each visitor.
 * 
 * @author Dominic Scheurer
 * @author Thomas Schulz
 *
 */
public interface IEclipseObjectVisitor {
	
	Object visit(IProject project);

	Object visit(IPackageFragment packageFragment);

	Object visit(IPackageFragmentRoot packageFragmentRoot);

	Object visit(ICompilationUnit compilationUnit);

	Object visit(IType type);

	Object visit(IField field);

	Object visit(IMethod method);

	Object visit(ISourceAttribute sourceAttribute);

	Object visit(IClassFile classFile);

	Object visit(IFile file);

	Object visit(IFolder folder);

	Object visit(ArrayList<IJavaElement> listOfJavaElements);
}
