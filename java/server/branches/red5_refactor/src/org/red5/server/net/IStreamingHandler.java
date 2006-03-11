package org.red5.server.net;

import org.red5.server.net.message.IStreamControl;
import org.red5.server.net.message.IStreamData;

public interface IStreamingHandler {
	
	public void handleControl(IStreamControl control) throws StreamControlException;
	
	public void handleData(IStreamData data) throws StreamDataException;

}
