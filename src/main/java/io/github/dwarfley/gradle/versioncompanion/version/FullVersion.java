package io.github.dwarfley.gradle.versioncompanion.version;

public final class FullVersion extends Version {
	
	private int mMajor;
	private int mMinor;
	private int mPatch;
	
	public FullVersion(int pMajor, int pMinor, int pPatch){
		mMajor = pMajor;
		mMinor = pMinor;
		mPatch = pPatch;
	}
	
	public int getMajor(){
		return mMajor;
	}
	
	public int getMinor(){
		return mMinor;
	}
	
	public int getPatch(){
		return mPatch;
	}
	
	public void incMajor(){
		mMajor++;
	}
	
	public void incMinor(){
		mMinor++;
	}
	
	public void incPatch(){
		mPatch++;
	}
	
	@Override
	public boolean equals(Object pObject){
		if(pObject instanceof FullVersion lVersion){
			return mMajor == lVersion.mMajor && mMinor == lVersion.mMinor && mPatch == lVersion.mPatch && super.equals(pObject);
		}else{
			return false;
		}
	}
	
	@Override
	public int hashCode(){
		return Integer.hashCode(mMajor) * Integer.hashCode(mMinor) * Integer.hashCode(mPatch) * super.hashCode();
	}
	
	@Override
	public String toString(){
		
		String lNumbers = String.join(".", String.valueOf(mMajor), String.valueOf(mMinor), String.valueOf(mPatch));
		
		return lNumbers + super.toString();
	}
	
}
