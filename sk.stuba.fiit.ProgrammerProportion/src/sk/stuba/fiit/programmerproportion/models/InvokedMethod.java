package sk.stuba.fiit.programmerproportion.models;

import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;

import sk.stuba.fiit.programmerproportion.utils.Strings;

public final class InvokedMethod extends AbstractReferCode{
	
	private final String mPackage;
	private final String mClass;
	private final String mName;
	
	public InvokedMethod(final String mPackage, final String mClass, final String mName){
		this.mPackage = mPackage;
		this.mClass = mClass;
		this.mName = mName;
	}
	
	public static InvokedMethod from(final MethodInvocation methodInvocation){
		ITypeBinding mclass;
		InvokedMethod im = null;
		if(methodInvocation.resolveMethodBinding() != null){
			mclass = methodInvocation.resolveMethodBinding().getDeclaringClass();//class name
			im = new InvokedMethod(mclass.getPackage().getName().toString(), mclass.getName().toString(), methodInvocation.getName().toString());
		}
		return im;
	}
	
	public String getPath(){
		return Strings.representationOf(mPackage, mClass);
	}

	@Override
	public String getStringRepresentation() {
		return Strings.representationOf(mPackage, mClass, mName);
	}
}
