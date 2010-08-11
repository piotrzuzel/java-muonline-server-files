/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.openmu.cs.serverPackets;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

import com.google.code.openmu.mina.codecs.MuMessageDecoder;

/**
 *
 * @author mikiones
 */
public class MuCSDemuxingProtocolCodecFactory extends DemuxingProtocolCodecFactory {

    public MuCSDemuxingProtocolCodecFactory() {
        super.addMessageEncoder(HelloClientData.class, HelloClientEncoder.class);
        super.addMessageEncoder(ServerListData.class,ServerListEncoder.class);
        super.addMessageEncoder(ServerAdressData.class, ServerAdressEnoder.class);
        super.addMessageDecoder(MuMessageDecoder.class);
    }

}
