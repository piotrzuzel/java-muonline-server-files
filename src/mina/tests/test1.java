/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mina.tests;

import java.io.IOException;
import java.net.InetSocketAddress;

import mina.MuFilters.MuDecryptIoFilter;
import mina.codec.BaseSererMessageEncoder;
import mina.codec.BaseServerPacketWraper;
import mina.codec.MuMessageDecoder;
import net.sf.jmuserver.cs.ServerList;
import net.sf.jmuserver.cs.IoHandler.CSIoHandler;
import net.sf.jmuserver.cs.serverPackage.MuCSDemuxingProtocolCodecFactory;
import net.sf.jmuserver.gs.GSIoHandler;
import net.sf.jmuserver.gs.serverPackage.SHello;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 *
 * @author mikiones
 */
public class test1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
    	 IoAcceptor acceptor = new NioSocketAcceptor();

         
         // Add two filters : a logger and a codec
         acceptor.getFilterChain().addLast( "Raw Logger", new LoggingFilter("RawLogger") );
         //acceptor.getFilterChain().addLast("dummuxing  codecs", new ProtocolCodecFilter(new MuCSDemuxingProtocolCodecFactory()));
         //acceptor.getFilterChain().addLast( "logger1", new LoggingFilter("Def logger") );
         acceptor.getFilterChain().addLast("defrag", new ProtocolCodecFilter(new BaseServerPacketWraper(),new BaseSererMessageEncoder()));
          //acceptor.getFilterChain().addLast( "logger21", new LoggingFilter("Dec logger") );
         acceptor.getFilterChain().addLast("Decrytor",new MuDecryptIoFilter());
         acceptor.getFilterChain().addLast( "logger2", new LoggingFilter("Dec logger") );
         
         
         
         //acceptor.getFilterChain().addLast("test",  );
         acceptor.setHandler(new GSIoHandler());

         acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );
         

         // And bind !
         acceptor.bind( new InetSocketAddress(55901) );
         // Prepare the service configuration.

    }

}
