/**
 * 
 */
package net.sf.jmuserver.gs;

import net.sf.jmuserver.gs.serverPackage.SHello;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * @author mikiones
 * 
 */
public class GSIoHandler implements IoHandler {

	private int playerId = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandler#exceptionCaught(org.apache.mina
	 * .core.session.IoSession, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandler#messageReceived(org.apache.mina
	 * .core.session.IoSession, java.lang.Object)
	 */
	@Override
	public void messageReceived(IoSession arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandler#messageSent(org.apache.mina.core
	 * .session.IoSession, java.lang.Object)
	 */
	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandler#sessionClosed(org.apache.mina.
	 * core.session.IoSession)
	 */
	@Override
	public void sessionClosed(IoSession arg0) throws Exception {
		IdFactory.getInstance().deleteId(playerId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandler#sessionCreated(org.apache.mina
	 * .core.session.IoSession)
	 */
	@Override
	public void sessionCreated(IoSession sesion) throws Exception {
		playerId = IdFactory.getInstance().newId();
		sesion.setAttribute("sesionID", playerId);
		sesion.write(new SHello(playerId,"09928"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandler#sessionIdle(org.apache.mina.core
	 * .session.IoSession, org.apache.mina.core.session.IdleStatus)
	 */
	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandler#sessionOpened(org.apache.mina.
	 * core.session.IoSession)
	 */
	public void sessionOpened(IoSession arg0) throws Exception {
		

	}

}
