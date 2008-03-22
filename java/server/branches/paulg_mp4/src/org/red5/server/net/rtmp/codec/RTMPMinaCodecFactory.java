package org.red5.server.net.rtmp.codec;

/*
 * RED5 Open Source Flash Server - http://www.osflash.org/red5
 * 
 * Copyright (c) 2006-2008 by respective authors (see below). All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or modify it under the 
 * terms of the GNU Lesser General Public License as published by the Free Software 
 * Foundation; either version 2.1 of the License, or (at your option) any later 
 * version. 
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along 
 * with this library; if not, write to the Free Software Foundation, Inc., 
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
 */

import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.red5.server.net.protocol.SimpleProtocolCodecFactory;
import org.red5.server.net.protocol.SimpleProtocolDecoder;
import org.red5.server.net.protocol.SimpleProtocolEncoder;

/**
 * RTMP codec factory.
 */
public class RTMPMinaCodecFactory implements ProtocolCodecFactory,
		SimpleProtocolCodecFactory {
	
    /**
     * RTMP Mina protocol decoder.
     */
	protected RTMPMinaProtocolDecoder decoder;
    /**
     * RTMP Mina protocol encoder.
     */
	protected RTMPMinaProtocolEncoder encoder;

    /**
     * Initialization. 
     * Create and setup of encoder/decoder and serializer/deserializer is handled by Spring.
     */
    public void init() {
	}

	/**
     * Setter for encoder.
     *
     * @param encoder  Encoder
     */
    public void setMinaEncoder(RTMPMinaProtocolEncoder encoder) {
		this.encoder = encoder;
    }

	/**
     * Setter for decoder
     *
     * @param decoder  Decoder
     */
    public void setMinaDecoder(RTMPMinaProtocolDecoder decoder) {
		this.decoder = decoder;
    }

	/** {@inheritDoc} */
    public ProtocolDecoder getDecoder() {
		return decoder;
	}

	/** {@inheritDoc} */
    public ProtocolEncoder getEncoder() {
		return encoder;
	}

	/** {@inheritDoc} */
    public SimpleProtocolDecoder getSimpleDecoder() {
		return decoder;
	}

	/** {@inheritDoc} */
    public SimpleProtocolEncoder getSimpleEncoder() {
		return encoder;
	}
	
	/** {@inheritDoc} */
    public RTMPMinaProtocolDecoder getMinaDecoder() {
		return decoder;
	}

	/** {@inheritDoc} */
    public RTMPMinaProtocolEncoder getMinaEncoder() {
		return encoder;
	}	

}
