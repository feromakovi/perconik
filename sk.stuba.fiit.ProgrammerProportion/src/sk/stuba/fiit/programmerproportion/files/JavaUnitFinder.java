package sk.stuba.fiit.programmerproportion.files;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;

public final class JavaUnitFinder {

	private final IJavaProject mJavaProject;
	private JavaUnitListener mListener;
	
	public JavaUnitFinder(final IJavaProject javaProject){
		if(javaProject == null)
			throw new NullPointerException("JavaProject instance cannot be null");
		
		this.mJavaProject = javaProject;
	}
	
	public void setFileFinderListener(final JavaUnitListener listener){
		this.mListener = listener;
	}
	
	public void find(){
		try {
			IPackageFragment[] fragments = this.mJavaProject.getPackageFragments();
			for(IPackageFragment fragment : fragments)
				if(IPackageFragmentRoot.K_SOURCE == fragment.getKind())
					for(ICompilationUnit iUnit : fragment.getCompilationUnits())
						if(this.mListener != null)
							this.mListener.onCompilationUnit(iUnit);
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}
	
	public static interface JavaUnitListener{
		public abstract void onCompilationUnit(ICompilationUnit iCompilationUnit);
	}
}