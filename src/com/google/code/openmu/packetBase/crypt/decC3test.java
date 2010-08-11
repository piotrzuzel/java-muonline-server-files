package com.google.code.openmu.packetBase.crypt;

import java.io.ByteArrayOutputStream;

public class decC3test {

	private final long[] ClientDecryptKeys = { 73326, 109989, 98843, 171058,
			18035, 30340, 24701, 11141, 62004, 64409, 35374, 64599, 35 };
	private final long[] ClientEncryptKeys = { 128079, 164742, 70235, 106898,
			23489, 11911, 19816, 13647, 48413, 46165, 15171, 37433, 73326 };
	private final long[] ServerDecryptKeys = { 128079, 164742, 70235, 106898,
			31544, 2047, 57011, 10183, 48413, 46165, 15171, 37433, 128079 };
	private final long[] ServerEncryptKeys = { 73326, 109989, 98843, 171058,
			13169, 19036, 35482, 29587, 62004, 64409, 35374, 64599, 128079 };

	private final byte C2Keys[] = { (byte) 0xE7, 0x6D, 0x3A, (byte) 0x89,
			(byte) 0xBC, (byte) 0xB2, (byte) 0x9F, 0x73, 0x23, (byte) 0xA8,
			(byte) 0xFE, (byte) 0xB6, 0x49, 0x5D, 0x39, 0x5D, (byte) 0x8A,
			(byte) 0xCB, 0x63, (byte) 0x8D, (byte) 0xEA, 0x7D, 0x2B, 0x5F,
			(byte) 0xC3, (byte) 0xB1, (byte) 0xE9, (byte) 0x83, 0x29, 0x51,
			(byte) 0xE8, 0x56 };

	public String printData(byte[] data, int len, String string) {
		final StringBuffer result = new StringBuffer();

		int counter = 0;

		for (int i = 0; i < len; i++) {
			if (counter % 16 == 0) {
				result.append(string + " ");
				result.append(fillHex(i, 4) + ": ");
			}

			result.append(fillHex(data[i] & 0xff, 2) + " ");
			counter++;
			if (counter == 16) {
				result.append("   ");

				int charpoint = i - 15;
				for (int a = 0; a < 16; a++) {
					final int t1 = data[charpoint++];
					if (t1 > 0x1f && t1 < 0x80) {
						result.append((char) t1);
					} else {
						result.append('.');
					}
				}

				result.append("\n");
				counter = 0;
			}
		}

		final int rest = data.length % 16;
		if (rest > 0) {
			for (int i = 0; i < 17 - rest; i++) {
				result.append("   ");
			}

			int charpoint = data.length - rest;
			for (int a = 0; a < rest; a++) {
				final int t1 = data[charpoint++];
				if (t1 > 0x1f && t1 < 0x80) {
					result.append((char) t1);
				} else {
					result.append('.');
				}
			}

			result.append("\n");
		}

		return result.toString();
	}

	private String fillHex(int data, int digits) {
		String number = Integer.toHexString(data);

		for (int i = number.length(); i < digits; i++) {
			number = "0" + number;
		}

		return number;
	}

	private final byte LoginKeys[] = { (byte) 0xFC, (byte) 0xCF, (byte) 0xAB };

	private int HashBuffer(byte[] Dest, int arrd, int Param10, byte[] Src,
			int off, int Param18, int Param1c) {

		System.out.println(printData(Src, Src.length, ""));

		final int BuffLen = ((Param1c + Param18 - 1) >> 3) - (Param18 >> 3) + 2;
		final byte[] Temp = new byte[BuffLen];
		Temp[BuffLen - 1] = 0;
		// memcpy(Temp,Src+(Param18>>3),BuffLen-1);
		System.arraycopy(Src, Param18 >> 3 + off, Temp, 0, BuffLen - 1);
		final int EAX = (Param1c + Param18) & 7;
		if (EAX != 0) {
			Temp[BuffLen - 2] &= (0xff) << (8 - EAX);
		}
		final int ESI = Param18 & 7;
		final int EDI = Param10 & 7;
		ShiftBuffer(Temp, BuffLen - 1, -ESI);
		ShiftBuffer(Temp, BuffLen, EDI);

		// unsigned char*TempPtr =(Param10>>3)+Dest;
		final int LoopCount = BuffLen - 1 + check(EDI, ESI);
		if (LoopCount != 0) {
			for (int i = 0; i < LoopCount; i++) {
				Dest[i + Param10 >> 3 + arrd] = (byte) (Dest[i + Param10 >> 3 + arrd] | (Temp[i]));
			}
		}
		// delete[] Temp;
		System.out.println(printData(Dest, Dest.length, ""));
		return Param10 + Param1c;
	}

	private int check(int a, int b) {
		int t = 0;
		if (a > b) {
			t = 1;
		}
		return t;
	}

	private void ShiftBuffer(byte[] Buff, int Len, int ShiftLen) {
		System.out.println(printData(Buff, Buff.length, ""));
		if (ShiftLen != 0) {
			if (ShiftLen > 0) {
				if (Len - 1 > 0) {
					for (int i = Len - 1; i > 0; i--) {
						Buff[i] = (byte) ((Buff[i - 1] << (8 - ShiftLen)) | (Buff[i] >> (ShiftLen)));
					}
				}
				Buff[0] = (byte) (Buff[0] >> ShiftLen);
				return;
			}
			ShiftLen = -ShiftLen;
			if (Len - 1 > 0) {
				for (int i = 0; i < Len - 1; i++) {
					Buff[i] = (byte) ((Buff[i + 1] >> (8 - ShiftLen)) | (Buff[i] << ShiftLen));
				}
			}
			Buff[Len - 1] = (byte) (Buff[Len - 1] << ShiftLen);
		}
		System.out.println(printData(Buff, Buff.length, ""));
	}

	private void ZeroMemory(byte[] Dest, int arr, int i) {
		for (int a = 0; a <= i; a++) {
			Dest[a + arr] = 0;
		}
	}

	private void EncC3Bytes(byte[] Dest, int arrd, byte[] Src, int arrs,
			int Len, long[] Keys) {
		final long Temp[] = { 0 };
		final long[] TempEnc = new long[4];
		for (int i = 0; i < 4; i++) {
			TempEnc[i] = ((Keys[i + 8] ^ (Src)[i + arrs] ^ Temp[0]) * Keys[i + 4])
					% Keys[i];
			Temp[0] = TempEnc[i] & 0xFFFF;
		}
		for (int i = 0; i < 3; i++) {
			TempEnc[i] = TempEnc[i] ^ Keys[8 + i] ^ (TempEnc[i + 1] & 0xFFFF);
		}
		int j = 0;
		ZeroMemory(Dest, arrd, 11);
		for (int i = 0; i < 4; i++) {
			j = HashBuffer(Dest, arrd, j, longArrayToByteArray(TempEnc), 4 * i,
					0, 16);
			j = HashBuffer(Dest, arrd, j, longArrayToByteArray(TempEnc),
					(4 * i), 22, 2);
		}
		byte XorByte = (byte) 0xF8;
		for (int i = 0; i < 8; i++) {
			XorByte ^= Src[i + arrs];
		}
		final byte[] TempB = longArrayToByteArray(Temp);

		TempB[1] = XorByte;
		TempB[0] = (byte) (XorByte ^ Len ^ 0x3D);
		HashBuffer(Dest, arrd, j, TempB, 0, 0, 16);
	}

	private byte[] longArrayToByteArray(long arr[]) {
		final ByteArrayOutputStream _bao = new ByteArrayOutputStream();
		for (final long element : arr) {
			_bao.write((int) (element & 0xff));
			_bao.write((int) (arr[1] >> 8 & 0xff));
			_bao.write((int) (arr[1] >> 16 & 0xff));
			_bao.write((int) (arr[1] >> 24 & 0xff));
		}
		return _bao.toByteArray();

	}

	private int EncryptC3(byte[] Dest, byte[] Src, int Len, long[] Keys) {
		if (Dest == null) {
			return 0;
		}
		// unsigned char *TempDest=Dest,*TempSrc=Src;
		int TempSrc = 0;
		int TempDest = 0;
		int EncLen = Len;
		if (Len > 0) {
			do {
				EncC3Bytes(Dest, TempDest, Src, TempSrc, (EncLen > 7) ? 8
						: EncLen, Keys);
				EncLen -= 8;
				TempSrc += 8;
				TempDest += 11;
			} while (EncLen > 0);
		}
		return Len * 11 / 8;
	}

	void DecXor32(byte[] Buff, int SizeOfHeader, int Len) {
		for (int i = Len - 1; i >= 0; i--) {
			Buff[i] ^= (C2Keys[(i + SizeOfHeader) & 31] ^ Buff[i - 1]);
		}
	}

	void EncXor32(byte[] Buff, int SizeOfHeader, int Len) {
		for (int i = 0; i < Len; i++) {
			Buff[i] ^= (C2Keys[(i + SizeOfHeader) & 31] ^ Buff[i - 1]);
		}
	}

	void EncDecLogin(byte[] Buff, int Len) {
		for (int i = 0; i < Len; i++) {
			Buff[i] = (byte) (Buff[i] ^ LoginKeys[i % 3]);
		}
	}

	int DecryptC3asClient(byte[] Dest, byte[] Src, int Len) {
		return DecryptC3(Dest, Src, Len, ClientDecryptKeys);
	}

	int EncryptC3asClient(byte[] Dest, byte[] Src, int Len) {
		return EncryptC3(Dest, Src, Len, ClientEncryptKeys);
	}

	int DecryptC3asServer(byte[] Dest, byte[] Src, int Len) {
		return DecryptC3(Dest, Src, Len, ServerDecryptKeys);
	}

	int EncryptC3asServer(byte[] Dest, byte[] Src, int Len) {
		return EncryptC3(Dest, Src, Len, ServerEncryptKeys);
	}

	private int DecryptC3(byte[] Dest, byte[] Src, int Len, long[] Keys) {
		if (Dest == null) {
			return 0;
		}
		int TempDest = 0, TempSrc = 0;
		int DecLen = 0;
		if (Len > 0) {
			do {
				if (DecC3Bytes(Dest, TempDest, Src, TempSrc, Keys) < 0) {
					return 0;
				}
				DecLen += 11;
				TempSrc += 11;
				TempDest += 8;
			} while (DecLen < Len);
		}
		return Len * 8 / 11;
	}

	private int DecC3Bytes(byte[] Dest, int arrd, byte[] Src, int arrs,
			long[] Keys) {
		System.out.println(printData(Src, Src.length, ""));
		ZeroMemory(Dest, arrd, 8);

		byte[] TempDecB = new byte[5 * 4];
		int j = 0;
		for (int i = 0; i < 4; i++) {
			HashBuffer(TempDecB, 4 * i, 0, Src, arrs, j, 16);
			j += 16;
			HashBuffer(TempDecB, 4 * i, 22, Src, arrs, j, 2);
			j += 2;
		}
		final long TempDec[] = byteArrayToLongArray(TempDecB);
		for (int i = 2; i >= 0; i--) {
			TempDec[i] = TempDec[i] ^ Keys[8 + i] ^ (TempDec[i + 1] & 0xFFFF);
		}
		long Temp = 0, Temp1;
		for (int i = 0; i < 4; i++) {
			Temp1 = ((Keys[4 + i] * (TempDec[i])) % (Keys[i])) ^ Keys[i + 8]
					^ Temp;
			Temp = TempDec[i] & 0xFFFF;
			(Dest)[i] = (byte) (Temp1);
		}
		TempDec[0] = 0;
		TempDecB = longArrayToByteArray(TempDec);
		HashBuffer(TempDecB, 0, 0, Src, arrs, j, 16);
		TempDecB = longArrayToByteArray(TempDec);
		TempDecB[0] = (byte) (TempDecB[1] ^ TempDecB[0] ^ 0x3d);

		byte XorByte = (byte) 0xF8;
		for (int i = 0; i < 8; i++) {
			XorByte ^= Dest[i];
		}
		System.out.println(printData(Dest, Dest.length, ""));
		if (XorByte != TempDecB[1]) {
			return -1;
		} else {
			return TempDecB[0];
		}

	}

	private long[] byteArrayToLongArray(byte[] tempDecB) {
		int off = 0;
		final long[] ret = new long[tempDecB.length / 4];
		for (int i = 0; i < tempDecB.length / 4; i++) {
			long t = tempDecB[off++] & 0xff;
			t |= tempDecB[off++] << 8 & 0xff00;
			t |= tempDecB[off++] << 0x10 & 0xff0000;
			t |= tempDecB[off++] << 0x18 & 0xff000000;
			ret[i] = t;
		}
		return ret;

	}

}
