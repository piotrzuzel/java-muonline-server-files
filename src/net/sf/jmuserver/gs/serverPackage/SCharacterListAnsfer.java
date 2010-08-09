package net.sf.jmuserver.gs.serverPackage;

import net.sf.jmuserver.gs.muObjects.MuCharacterBase;
import net.sf.jmuserver.gs.muObjects.MuCharacterList;
import net.sf.jmuserver.gs.muObjects.MuObjectFactory;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;


/**
 * @author MikiOne
 * 
 */
public class SCharacterListAnsfer extends ServerBasePacket {

	@Override
	public IoBuffer getContent(int sesionID) {

		MuCharacterList _list = MuObjectFactory.getInstance()
				.getSession(sesionID).getChList();

		final int characterCount = _list.getCharsCount();
		int size;
		if (characterCount == 0)
			size = 0x07;
		else
			size = 7 + ((characterCount) * 28);

		final IoBuffer out = IoBuffer.allocate(size);

		mC1Header(out, 0xf3, 0x00, size); // c1 size f3 00
		out.put((byte) 0x04);
		out.put((byte) 0x00);
		out.put((byte) characterCount); // count
		if (characterCount != 0) {
			for (int s = 0; s < characterCount; s++) {
				MuCharacterBase _char = _list.getChar(s);
				out.put((byte) s);
				writeNick(out, _char.getName());
				out.putShort((short) _char.getLvl());
				out.put((byte) 0);
				out.put((byte) _char.getClas());
				out.put(_char.getWear().getBytes());
			}
		}
		return out;
	}

	

	
}
