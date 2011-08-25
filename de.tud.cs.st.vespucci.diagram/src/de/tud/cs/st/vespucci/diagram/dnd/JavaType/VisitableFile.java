package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.core.resources.IFile;

public class VisitableFile implements IVisitable {
	private IFile project;

	public VisitableFile(Object project) {
		this.project = (IFile)project;
	}

	@Override
	public Object accept(IEclipseObjectVisitor visitor) {
		return visitor.visit(project);
	}		
}
