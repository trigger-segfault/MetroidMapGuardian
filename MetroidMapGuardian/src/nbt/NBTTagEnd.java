package nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * The end tag used to close NBT compounds.
 * 
 * @author	Markus Persson
 *
 * @see
 * {@linkplain NBTElement},
 * {@linkplain NBTTagCompound}
 */
public class NBTTagEnd extends NBTElement {

	// ===================== Constructors =====================

	/**
	 * The default constructor, constructs an end tag
	 * 
	 * @return	Returns an end tag.
	 */
	public NBTTagEnd() {
		super("");
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
		return super.toString(level) + "\n";
	}
	
	/**
	 * Writes the contents of the tag to the output stream.
	 * 
	 * @param	out - The output stream to write the contents to.
	 * @throws	IOException 
	 */
	void writeTagContents(DataOutput out) throws IOException {
		
	}
	/**
	 * Reads the contents of the tag from the input stream.
	 * 
	 * @param	in - The input stream to read the contents from.
	 * @throws	IOException 
	 */
	void readTagContents(DataInput in) throws IOException {
		
	}
	/**
	 * Returns the type of the tag.
	 * 
	 * @return	Returns the type of the tag.
	 */
	public byte getType() {
		return NBTElement.TAG_END;
	}
}
