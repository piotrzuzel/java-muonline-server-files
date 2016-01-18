This MuOnline server is written to work with a MuOnline v99b client. Other versions may use a slightly different protocol and include other features which can easily be implemented using the same core. The protocol information below is in regards to MuOnline version 99.xx.

---

# Packet types and encryption #

Packet types are differentiated by their header (of 1 byte). These are:
  1. **C1** & **C2** - basic packets used in game, encrypted with an XOR algorithm.
  1. **C3** & **C4** - packets used for sensitive information, like login information and trades. In addition to XOR-ing, the encryption algorithm also uses a byte shifting algorithm. Upon decryption, C3 packets become C1, and C4 packets become C2.

# Packet structure #

C1/C3 packets have the following structure:
|0xC1|Length|Type|Subtype|Body|
|:---|:-----|:---|:------|:---|
|1 byte|1 byte|1 byte|1 byte |n bytes|

C2/C4 packets have the following structure:
|0xC2|Length|Type|Subtype|Body|
|:---|:-----|:---|:------|:---|
|1 byte|2 bytes|1 byte|1 byte |n bytes|



Please refer to the [protocol specification](http://code.google.com/p/java-muonline-server-files/wiki/ProtocolHexSpec) for more details.

---

Please let us know if this article contains inaccurate information.