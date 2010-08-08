/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.cs.serverPackage;

import mina.codec.MuMessageDecoder;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

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
