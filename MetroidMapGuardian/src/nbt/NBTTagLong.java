package nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A basic NBT element with a long value.
 * 
 * @author	Markus Persson
 *
 * @see
 * {@linkplain NBTElement},
 * {@linkplain NBTTagCompound}
 */
public class NBTTagLong extends NBTElement {

	// ======================= Members ========================
	
	/** The long value of the element. */
	public long longValue;

	// ===================== Constructors =====================

	/**
	 * The default constructor, constructs a long with a value of 0 and
	 * an empty key.
	 * 
	 * @return	Returns a long of the value 0 with an empty key.
	 */
	public NBTTagLong() {
		super("");
		this.longValue = 0;
	}
	/**
	 * Constructs a long of the value 0 with the given key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @return	Returns a long of the value 0 with the given key.
	 */
	public NBTTagLong(String key) {
		super(key);
		this.longValue = 0;
	}
	/**
	 * Constructs a long with the given value and key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	longValue - The value of the tag.
	 * @return	Returns a long with the given value and key.
	 */
	public NBTTagLong(String key, long longValue) {
		super(key);
		this.longValue = longValue;
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
		return super.toString(level) + String.valueOf(longValue) + "\n";
	}
	
	/**
	 * Writes the contents of the tag to the output stream.
	 * 
	 * @param	out - The output stream to write the contents to.
	 * @throws	IOException 
	 */
	void writeTagContents(DataOutput out) throws IOException {
		out.writeLong(longValue);
	}
	/**
	 * Reads the contents of the tag from the input stream.
	 * 
	 * @param	in - The input stream to read the contents from.
	 * @throws	IOException 
	 */
	void readTagContents(DataInput in) throws IOException {
		longValue = in.readLong();
	}
	/**
	 * Returns the type of the tag.
	 * 
	 * @return	Returns the type of the tag.
	 */
	public byte getType() {
		return NBTElement.TAG_LONG;
	}
}
