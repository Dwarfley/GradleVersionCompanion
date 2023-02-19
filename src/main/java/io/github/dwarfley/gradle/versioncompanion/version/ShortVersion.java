package io.github.dwarfley.gradle.versioncompanion.version;

public final class ShortVersion extends Version {
	
	private int mMajor;
	private int mMinor;
	
	public ShortVersion(int pMajor, int pMinor){
		mMajor = pMajor;
		mMinor = pMinor;
	}
	
	public int getMajor(){
		return mMajor;
	}
	
	public int getMinor(){
		return mMinor;
	}
	
	public void incMajor(){
		mMajor++;
	}
	
	public void incMinor(){
		mMinor++;
	}
	
	public FullVersion getFirstVersion(){
		return new FullVersion(mMajor, mMinor, 0);
	}
	
	@Override
	public boolean equals(Object pObject){
		if(pObject instanceof ShortVersion lVersion){
			return mMajor == lVersion.mMajor && mMinor == lVersion.mMinor && super.equals(pObject);
		}else{
			return false;
		}
	}
	
	@Override
	public int hashCode(){
		return Integer.hashCode(mMajor) * Integer.hashCode(mMinor) * super.hashCode();
	}
	
	@Override
	public String toString(){
		
		String lNumbers = String.join(".", String.valueOf(mMajor), String.valueOf(mMinor));
		
		return lNumbers + super.toString();
	}
	
}
