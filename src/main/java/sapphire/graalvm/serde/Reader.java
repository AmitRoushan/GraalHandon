package sapphire.graalvm.serde;

import org.graalvm.polyglot.*;
import java.io.*;
import java.util.*;


public class Reader {

	private DataInputStream in;
	private Context c;
	public Map<Integer, Value> seenCache = new HashMap<Integer, Value>();
		
	public Reader(InputStream in, Context c) {
		this.in = new DataInputStream(in);
		this.c = c;
	}

	public Value read() throws Exception {
		seenCache = new HashMap<Integer, Value>();
		return readHelper();
	}

	public Value readHelper() throws Exception {
		Value out = null;

		//check if seen before or known type

		int seenindex = in.readInt();
		short typeindex = in.readShort();
		if(typeindex < TypesDB.fromInt.length) {
			switch(TypesDB.fromInt[typeindex]) {
				case BOOLEAN:
					//System.out.println("found boolean");
					boolean b = in.readBoolean();
					out = c.asValue(b);
					System.out.println("boolean " + b);
					break;
				case NATIVE_POINTER:
					throw new IllegalArgumentException("don't handle native pointers yet");
				case NULL:
					//System.out.println("found null");
					out = c.asValue(null);
					System.out.println("null");
					break;
				case NUMBER:
					//System.out.println("found number");
					int i = in.readInt();
					out = c.asValue(i);
					System.out.println("int " + i);
					break;
				case STRING:
					//System.out.println("found string");
					String s = in.readUTF();
					out = c.asValue(s);
					System.out.println("String " + s);
					break;
				case SEEN:
					//System.out.println("found cached value");
					return seenCache.get(seenindex);
				default: 
					throw new IllegalArgumentException("we should never get here");
			}
		} else { //Complex
			out = TypesDB.newInstance(typeindex, c);
		}
		seenCache.put(seenindex, out);

		//check array data
		long arraylength = in.readLong();
		if(arraylength != 0) {
			//System.out.println("array of length " + arraylength);
		}
		for(int i = 0; i < arraylength; i++) {
			out.setArrayElement(i, readHelper());
			System.out.println("Array element " + i);
		}

		//if we have a type, check named members
		for(String s : TypesDB.getMembers(typeindex)) {
			//System.out.println("now reading in " + s);
			if(s.equals("__proto__")) { //TODO: for some reason can't serialize js inheritance chain
				continue;
			}
			System.out.println("Reading member, id is " + s);
			Value member = readHelper();
			if(member != null) {
				out.putMember(s, member);
			}
		}
		return out;
	}

	public void close() throws Exception {
		in.close();
		in = null;
	}
}
