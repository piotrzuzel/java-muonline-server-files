/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mina.tests;

import mina.IoHandler.IoMuHandler;
import mina.codec.BaseServerPacketWraper;
import mina.codec.MuMessageDefragmentator;
import java.io.IOException;
import java.net.InetSocketAddress;
import mina.MuFilters.MuDecryptIoFilter;
import mina.codec.CS2CL.MuCSDemuxingProtocolCodecFactory;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;


/**
 *
 * @author mikiones
 */
public class minaTest {
    private static final int GSport = 55901;
    private static final int CSport = 44405;/**
     * @param args the command line arguments
     */
    public static void main(String[] args)  throws IOException {
        System.out.println("Server runed omn port:"+CSport);
        // Create the acceptor
        IoAcceptor acceptor = new NioSocketAcceptor();

        
        // Add two filters : a logger and a codec
        acceptor.getFilterChain().addLast( "Raw Logger", new LoggingFilter("RawLogger") );
        acceptor.getFilterChain().addLast("dummuxing  codecs", new ProtocolCodecFilter(new MuCSDemuxingProtocolCodecFactory()));
        //acceptor.getFilterChain().addLast( "logger1", new LoggingFilter("Def logger") );
        //acceptor.getFilterChain().addLast("defrag", new ProtocolCodecFilter(new BaseServerPacketWraper(),new MuMessageDefragmentator()));
        // acceptor.getFilterChain().addLast( "logger21", new LoggingFilter("Dec logger") );
        //acceptor.getFilterChain().addLast("Decrytor",new MuDecryptIoFilter());
        //acceptor.getFilterChain().addLast( "logger2", new LoggingFilter("Dec logger") );
        
        
        
        //acceptor.getFilterChain().addLast("test",  );
        acceptor.setHandler(new IoMuHandler((short)10));

        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );

        // And bind !
        acceptor.bind( new InetSocketAddress(CSport) );
        // Prepare the service configuration.

    }

}
