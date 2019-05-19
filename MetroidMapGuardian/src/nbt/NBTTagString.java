package nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A basic NBT element with a string value.
 * 
 * @author	Markus Persson
 *
 * @see
 * {@linkplain NBTElement},
 * {@linkplain NBTTagCompound}
 */
public class NBTTagString extends NBTElement {

	// ======================= Members ========================
	
	/** The string value of the element. */
	public String stringValue;

	// ===================== Constructors =====================

	/**
	 * The default constructor, constructs an empty string and an empty key.
	 * 
	 * @return	Returns an empty string with an empty key.
	 */
	public NBTTagString() {
		super("");
		this.stringValue = "";
	}
	/**
	 * Constructs an empty string with the given key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @return	Returns an empty string with the given key.
	 */
	public NBTTagString(String key) {
		super(key);
		this.stringValue = "";
	}
	/**
	 * Constructs a string with the given value and key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	stringValue - The value of the tag.
	 * @return	Returns a string with the given value and key.
	 */
	public NBTTagString(String key, String stringValue) {
		super(key);
		this.stringValue = stringValue;
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
		return super.toString(level) + "\"" + stringValue + "\"" + "\n";
	}
	
	/**
	 * Writes the contents of the tag to the output stream.
	 * 
	 * @param	out - The output stream to write the contents to.
	 * @throws	IOException 
	 */
	void writeTagContents(DataOutput out) throws IOException {
		out.writeUTF(stringValue);
	}
	/**
	 * Reads the contents of the tag from the input stream.
	 * 
	 * @param	in - The input stream to read the contents from.
	 * @throws	IOException 
	 */
	void readTagContents(DataInput in) throws IOException {
		stringValue = in.readUTF();
	}
	/**
	 * Returns the type of the tag.
	 * 
	 * @return	Returns the type of the tag.
	 */
	public byte getType() {
		return NBTElement.TAG_STRING;
	}
}
