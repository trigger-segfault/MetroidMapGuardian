package nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A basic NBT element with a byte array. This can be used to store files.
 * 
 * @author	Markus Persson
 *
 * @see
 * {@linkplain NBTElement},
 * {@linkplain NBTTagCompound}
 */
public class NBTTagByteArray extends NBTElement {

	// ======================= Members ========================
	
	/** The byte array of the element. */
	public byte[] byteArray;

	// ===================== Constructors =====================

	/**
	 * The default constructor, constructs a byte array with 0 bytes and an
	 * empty key.
	 * 
	 * @return	Returns a byte array with 0 bytes and an empty key.
	 */
	public NBTTagByteArray() {
		super("");
		this.byteArray = new byte[0];
	}
	/**
	 * Constructs a byte array with 0 bytes and the given key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @return	Returns a byte array with 0 bytes and the given key.
	 */
	public NBTTagByteArray(String key) {
		super(key);
		this.byteArray = new byte[0];
	}
	/**
	 * Constructs a byte array with the given value and key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	byteArray - The value of the tag.
	 * @return	Returns a byte array with the given value and key.
	 */
	public NBTTagByteArray(String key, byte[] byteArray) {
		super(key);
		this.byteArray = byteArray;
	}

	// ================= Implemented Methods ==================

	/**
	 * Returns the string representation of the tag.
	 * 
	 * @return	Returns the string representation of the tag.
	 */
	public String toString() {
		return toString(0);
	}
	/**
	 * Returns the string representation of the tag with level spacing.
	 * 
	 * @param	level - The compound level of the tag.
	 * @return	Returns the string representation of the tag with level spacing.
	 */
	public String toString(int level) {
		String tagName = super.toString(level) + "\n";
		level += 1;
		for (byte byteValue : byteArray) {
			tagName += new NBTTagByte("", byteValue).toString(level);
		}
		
		return tagName;
	}
	
	/**
	 * Writes the contents of the tag to the output stream.
	 * 
	 * @param	out - The output stream to write the contents to.
	 * @throws	IOException 
	 */
	void writeTagContents(DataOutput out) throws IOException {
		out.writeInt(byteArray.length);
		out.write(byteArray);
	}
	/**
	 * Reads the contents of the tag from the input stream.
	 * 
	 * @param	in - The input stream to read the contents from.
	 * @throws	IOException 
	 */
	void readTagContents(DataInput in) throws IOException {
		int arraySize = in.readInt();
		byteArray = new byte[arraySize];
		in.readFully(byteArray);
	}
	/**
	 * Returns the type of the tag.
	 * 
	 * @return	Returns the type of the tag.
	 */
	public byte getType() {
		return NBTElement.TAG_BYTE_ARRAY;
	}

	// ==================== Array Methods =====================

	/**
	 * Returns the number of bytes in the array.
	 * 
	 * @return	Returns the number of bytes in the array.
	 */
	public int arraySize() {
		return byteArray.length;
	}
}
