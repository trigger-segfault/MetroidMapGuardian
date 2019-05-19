package nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A basic NBT element with a float value.
 * 
 * @author	Markus Persson
 *
 * @see
 * {@linkplain NBTElement},
 * {@linkplain NBTTagCompound}
 */
public class NBTTagFloat extends NBTElement {

	// ======================= Members ========================
	
	/** The float value of the element. */
	public float floatValue;

	// ===================== Constructors =====================

	/**
	 * The default constructor, constructs a float with a value of 0 and
	 * an empty key.
	 * 
	 * @return	Returns a float of the value 0 with an empty key.
	 */
	public NBTTagFloat() {
		super("");
		this.floatValue = 0.0f;
	}
	/**
	 * Constructs a float of the value 0 with the given key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @return	Returns a float of the value 0 with the given key.
	 */
	public NBTTagFloat(String key) {
		super(key);
		this.floatValue = 0.0f;
	}
	/**
	 * Constructs a float with the given value and key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	floatValue - The value of the tag.
	 * @return	Returns a float with the given value and key.
	 */
	public NBTTagFloat(String key, float floatValue) {
		super(key);
		this.floatValue = floatValue;
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
		return super.toString(level) + String.valueOf(floatValue) + "\n";
	}
	
	/**
	 * Writes the contents of the tag to the output stream.
	 * 
	 * @param	out - The output stream to write the contents to.
	 * @throws	IOException 
	 */
	void writeTagContents(DataOutput out) throws IOException {
		out.writeFloat(floatValue);
	}
	/**
	 * Reads the contents of the tag from the input stream.
	 * 
	 * @param	in - The input stream to read the contents from.
	 * @throws	IOException 
	 */
	void readTagContents(DataInput in) throws IOException {
		floatValue = in.readFloat();
	}
	/**
	 * Returns the type of the tag.
	 * 
	 * @return	Returns the type of the tag.
	 */
	public byte getType() {
		return NBTElement.TAG_FLOAT;
	}
}
