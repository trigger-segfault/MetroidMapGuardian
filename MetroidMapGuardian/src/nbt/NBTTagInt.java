package nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A basic NBT element with a integer value.
 * 
 * @author	Markus Persson
 *
 * @see
 * {@linkplain NBTElement},
 * {@linkplain NBTTagCompound}
 */
public class NBTTagInt extends NBTElement {

	// ======================= Members ========================
	
	/** The integer value of the element. */
	public int intValue;

	// ===================== Constructors =====================

	/**
	 * The default constructor, constructs an integer with a value of 0 and
	 * an empty key.
	 * 
	 * @return	Returns an integer of the value 0 with an empty key.
	 */
	public NBTTagInt() {
		super("");
		this.intValue = 0;
	}
	/**
	 * Constructs an integer of the value 0 with the given key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @return	Returns an integer of the value 0 with the given key.
	 */
	public NBTTagInt(String key) {
		super(key);
		this.intValue = 0;
	}
	/**
	 * Constructs an integer with the given value and key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	intValue - The value of the tag.
	 * @return	Returns an integer with the given value and key.
	 */
	public NBTTagInt(String key, int intValue) {
		super(key);
		this.intValue = intValue;
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
		return super.toString(level) + String.valueOf(intValue) + "\n";
	}
	
	/**
	 * Writes the contents of the tag to the output stream.
	 * 
	 * @param	out - The output stream to write the contents to.
	 * @throws	IOException 
	 */
	void writeTagContents(DataOutput out) throws IOException {
		out.writeInt(intValue);
	}
	/**
	 * Reads the contents of the tag from the input stream.
	 * 
	 * @param	in - The input stream to read the contents from.
	 * @throws	IOException 
	 */
	void readTagContents(DataInput in) throws IOException {
		intValue = in.readInt();
	}
	/**
	 * Returns the type of the tag.
	 * 
	 * @return	Returns the type of the tag.
	 */
	public byte getType() {
		return NBTElement.TAG_INT;
	}
}
