package nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A basic NBT element with a byte value.
 * 
 * @author	Markus Persson
 *
 * @see
 * {@linkplain NBTElement},
 * {@linkplain NBTTagCompound}
 */
public class NBTTagByte extends NBTElement {

	// ======================= Members ========================
	
	/** The byte value of the element. */
	public byte byteValue;

	// ===================== Constructors =====================

	/**
	 * The default constructor, constructs a byte with a value of 0 and
	 * an empty key.
	 * 
	 * @return	Returns a byte of the value 0 with an empty key.
	 */
	public NBTTagByte() {
		super("");
		this.byteValue = 0;
	}
	/**
	 * Constructs a byte of the value 0 with the given key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @return	Returns a byte of the value 0 with the given key.
	 */
	public NBTTagByte(String key) {
		super(key);
		this.byteValue = 0;
	}
	/**
	 * Constructs a byte with the given value and key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	byteValue - The value of the tag.
	 * @return	Returns a byte with the given value and key.
	 */
	public NBTTagByte(String key, byte byteValue) {
		super(key);
		this.byteValue = byteValue;
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
		return super.toString(level) + String.valueOf(byteValue) + "\n";
	}
	
	/**
	 * Writes the contents of the tag to the output stream.
	 * 
	 * @param	out - The output stream to write the contents to.
	 * @throws	IOException 
	 */
	void writeTagContents(DataOutput out) throws IOException {
		out.writeByte(byteValue);
	}
	/**
	 * Reads the contents of the tag from the input stream.
	 * 
	 * @param	in - The input stream to read the contents from.
	 * @throws	IOException 
	 */
	void readTagContents(DataInput in) throws IOException {
		byteValue = in.readByte();
	}
	/**
	 * Returns the type of the tag.
	 * 
	 * @return	Returns the type of the tag.
	 */
	public byte getType() {
		return NBTElement.TAG_BYTE;
	}
}

