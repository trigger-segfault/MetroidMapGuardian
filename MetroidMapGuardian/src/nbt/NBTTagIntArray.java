package nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A basic NBT element with a integer array.
 * 
 * @author	Markus Persson
 *
 * @see
 * {@linkplain NBTElement},
 * {@linkplain NBTTagCompound}
 */
public class NBTTagIntArray extends NBTElement {

	// ======================= Members ========================
	
	/** The integer array of the element. */
	public int[] intArray;

	// ===================== Constructors =====================

	/**
	 * The default constructor, constructs a integer array with 0 integers and an
	 * empty key.
	 * 
	 * @return	Returns a integer array with 0 integers and an empty key.
	 */
	public NBTTagIntArray() {
		super("");
		this.intArray = new int[0];
	}
	/**
	 * Constructs a integer array with 0 integers and the given key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @return	Returns a integer array with 0 integers and the given key.
	 */
	public NBTTagIntArray(String key) {
		super(key);
		this.intArray = new int[0];
	}
	/**
	 * Constructs a integer array with the given value and key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	intArray - The value of the tag.
	 * @return	Returns a integer array with the given value and key.
	 */
	public NBTTagIntArray(String key, int[] intArray) {
		super(key);
		this.intArray = intArray;
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
		for (int intValue : intArray) {
			tagName += new NBTTagInt("", intValue).toString(level);
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
		out.writeInt(intArray.length);
		for (int i = 0; i < intArray.length; i++) {
			out.writeInt(intArray[i]);
		}
	}
	/**
	 * Reads the contents of the tag from the input stream.
	 * 
	 * @param	in - The input stream to read the contents from.
	 * @throws	IOException 
	 */
	void readTagContents(DataInput in) throws IOException {
		int arraySize = in.readInt();
		intArray = new int[arraySize];
		for (int i = 0; i < arraySize; i++) {
			intArray[i] = in.readInt();
		}
	}
	/**
	 * Returns the type of the tag.
	 * 
	 * @return	Returns the type of the tag.
	 */
	public byte getType() {
		return NBTElement.TAG_INT_ARRAY;
	}

	// ==================== Array Methods =====================

	/**
	 * Returns the number of integers in the array.
	 * 
	 * @return	Returns the number of integers in the array.
	 */
	public int arraySize() {
		return intArray.length;
	}
}
