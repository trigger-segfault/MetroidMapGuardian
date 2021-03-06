package nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A basic NBT element with a double value.
 * 
 * @author	Markus Persson
 *
 * @see
 * {@linkplain NBTElement},
 * {@linkplain NBTTagCompound}
 */
public class NBTTagDouble extends NBTElement {

	// ======================= Members ========================
	
	/** The double value of the element. */
	public double doubleValue;

	// ===================== Constructors =====================

	/**
	 * The default constructor, constructs a double with a value of 0 and
	 * an empty key.
	 * 
	 * @return	Returns a double of the value 0 with an empty key.
	 */
	public NBTTagDouble() {
		super("");
		this.doubleValue = 0.0;
	}
	/**
	 * Constructs a double of the value 0 with the given key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @return	Returns a double of the value 0 with the given key.
	 */
	public NBTTagDouble(String key) {
		super(key);
		this.doubleValue = 0.0;
	}
	/**
	 * Constructs a double with the given value and key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	doubleValue - The value of the tag.
	 * @return	Returns a double with the given value and key.
	 */
	public NBTTagDouble(String key, double doubleValue) {
		super(key);
		this.doubleValue = doubleValue;
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
		return super.toString(level) + String.valueOf(doubleValue) + "\n";
	}
	
	/**
	 * Writes the contents of the tag to the output stream.
	 * 
	 * @param	out - The output stream to write the contents to.
	 * @throws	IOException 
	 */
	void writeTagContents(DataOutput out) throws IOException {
		out.writeDouble(doubleValue);
	}
	/**
	 * Reads the contents of the tag from the input stream.
	 * 
	 * @param	in - The input stream to read the contents from.
	 * @throws	IOException 
	 */
	void readTagContents(DataInput in) throws IOException {
		doubleValue = in.readDouble();
	}
	/**
	 * Returns the type of the tag.
	 * 
	 * @return	Returns the type of the tag.
	 */
	public byte getType() {
		return NBTElement.TAG_DOUBLE;
	}
}
