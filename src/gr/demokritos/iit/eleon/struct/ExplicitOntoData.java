package gr.demokritos.iit.eleon.struct;

import gr.demokritos.iit.eleon.interfaces.OntoExtension;

public class ExplicitOntoData implements OntoExtension
{
	
	private EleonStruc explicitUserData;

	public ExplicitOntoData( EleonStruc data )
	{
		explicitUserData = data;
	}

	@Override
	public void setPrevious( OntoExtension prev ) { }

	@Override
	public EleonStruc getExtension()
	{
		return explicitUserData;
	}

	@Override
	public boolean isRealTime() { return true; }

	@Override
	public void rebind() { }

}
