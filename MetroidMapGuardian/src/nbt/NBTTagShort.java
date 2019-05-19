package nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A basic NBT element with a short value.
 * 
 * @author	Markus Persson
 *
 * @see
 * {@linkplain NBTElement},
 * {@linkplain NBTTagCompound}
 */
public class NBTTagShort extends NBTElement {

	// ======================= Members ========================
	
	/** The short value of the element. */
	public short shortValue;

	// ===================== Constructors =====================

	/**
	 * The default constructor, constructs a short with a value of 0 and
	 * an empty key.
	 * 
	 * @return	Returns a short of the value 0 with an empty key.
	 */
	public NBTTagShort() {
		super("");
		this.shortValue = 0;
	}
	/**
	 * Constructs a short of the value 0 with the given key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @return	Returns a short of the value 0 with the given key.
	 */
	public NBTTagShort(String key) {
		super(key);
		this.shortValue = 0;
	}
	/**
	 * Constructs a short with the given value and key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	shortValue - The value of the tag.
	 * @return	Returns a short with the given value and key.
	 */
	public NBTTagShort(String key, short shortValue) {
		super(key);
		this.shortValue = shortValue;
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
		return super.toString(level) + String.valueOf(shortValue) + "\n";
	}
	
	/**
	 * Writes the contents of the tag to the output stream.
	 * 
	 * @param	out - The output stream to write the contents to.
	 * @throws	IOException 
	 */
	void writeTagContents(DataOutput out) throws IOException {
		out.writeShort(shortValue);
	}
	/**
	 * Reads the contents of the tag from the input stream.
	 * 
	 * @param	in - The input stream to read the contents from.
	 * @throws	IOException 
	 */
	void readTagContents(DataInput in) throws IOException {
		shortValue = in.readShort();
	}
	/**
	 * Returns the type of the tag.
	 * 
	 * @return	Returns the type of the tag.
	 */
	public byte getType() {
		return NBTElement.TAG_SHORT;
	}
}
