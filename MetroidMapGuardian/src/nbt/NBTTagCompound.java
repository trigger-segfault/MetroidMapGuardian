package nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An NBT element that contains a collection of NBT elements. This is the
 * main class to read NBT files from.
 * 
 * @author	Markus Persson
 *
 * @see
 * {@linkplain NBTElement}
 */
public class NBTTagCompound extends NBTElement {

	// ======================= Members ========================
	
	/**
	 * The map that contains all the elements in this compound. A linked hash map
	 * is used for this so the order of the elements is kept, thus outputting to the
	 * console will give an organized list.
	 */
	private LinkedHashMap<String, NBTElement> tagMap;

	// ===================== Constructors =====================

	/**
	 * The default constructor, constructs an empty compound with an empty key.
	 * 
	 * @return	Returns an empty compound with an empty key.
	 */
	public NBTTagCompound() {
		super("");
		this.tagMap = new LinkedHashMap<String, NBTElement>();
	}
	/**
	 * Constructs an empty compound with the given key.
	 * 
	 * @param	key - The key identifier.
	 * @return	Returns an empty compound with the given key.
	 */
	public NBTTagCompound(String key) {
		super(key);
		this.tagMap = new LinkedHashMap<String, NBTElement>();
	}
	/**
	 * Constructs a compound with the given tags and key.
	 * 
	 * @param	key - The key identifier.
	 * @param	tagMap - The map of tags to add to the compound.
	 * @return	Returns a compound with the given tags and key.
	 */
	public NBTTagCompound(String key, Map<String, NBTElement> tagMap) {
		super(key);
		this.tagMap = new LinkedHashMap<String, NBTElement>();
		this.tagMap.putAll(tagMap);
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
		for (NBTElement tag : tagMap.values()) {
			tagName += tag.toString(level);
		}
		tagName += new NBTTagEnd().toString(level);
		
		return tagName;
	}
	
	/**
	 * Writes the contents of the tag to the output stream.
	 * 
	 * @param	out - The output stream to write the contents to.
	 * @throws	IOException 
	 */
	void writeTagContents(DataOutput out) throws IOException {

		// Write the tags
		for (NBTElement tag : tagMap.values()) {
			NBTElement.writeTag(tag, out);
		}
		
		// End the list with TAG_End
		out.writeByte(0);
	}
	/**
	 * Reads the contents of the tag from the input stream.
	 * 
	 * @param	in - The input stream to read the contents from.
	 * @throws	IOException 
	 */
	void readTagContents(DataInput in) throws IOException {
		
		// Read the tags until TAG_End is reached
		NBTElement tag = null;
		tagMap.clear();
		do {
			// Read and add the tag to the map
			tag = NBTElement.readTag(in);
			if (tag.getType() != NBTElement.TAG_END)
				tagMap.put(tag.getKey(), tag);
		}
		while(tag.getType() != NBTElement.TAG_END);
	}
	/**
	 * Returns the type of the tag.
	 * 
	 * @return	Returns the type of the tag.
	 */
	public byte getType() {
		return NBTElement.TAG_COMPOUND;
	}
	
	// =================== Compound Methods ===================
	
	/**
	 * Returns the collection of tags contained in this compound.
	 * 
	 * @return	Returns the collection of tags contained in this compound.
	 */
	public Collection<NBTElement> getTags() {
		return tagMap.values();
	}
	/**
	 * Returns the map of tags contained in this compound.
	 * 
	 * @return	Returns the map of tags contained in this compound.
	 */
	public Map<String, NBTElement> getTagMap() {
		return tagMap;
	}
	/**
	 * Returns the number of elements in the list.
	 * 
	 * @return	Returns the number of elements in the list.
	 */
	public int tagCount() {
		return tagMap.size();
	}
	/**
	 * Clears all the tags in the compound.
	 */
	public void clearTags() {
		tagMap.clear();
	}
	/**
	 * Tests whether an element with the given key exists.
	 * 
	 * @param	key - The key identifier to test for.
	 * @return	Returns true if there is an element with the given key.
	 */
	public boolean hasKey(String key) {
		return (getTag(key, null) != null);
	}
	/**
	 * Tests whether an element with the given key and type exists.
	 * 
	 * @param	key - The key identifier to test for.
	 * @param	type - The type that the element must match
	 * @return	Returns true if there is an element with the given key and
	 * the specified type.
	 */
	public boolean hasKey(String key, byte type) {
		NBTElement tag = getTag(key, null);
		if (tag != null)
			return (tag.getType() == type);
		
		return false;
	}

	// =================== Mutator Methods ====================
	
	/**
	 * Sets the tag of the given key identifier to the given value. This will overwrite
	 * the current tag with this key in the compound. To access compound elements
	 * within this compound use a '/' to separate values.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	tag - The tag to set the element to.
	 */
	public void setTag(String key, NBTElement tag) {
		// Check if the key is accessing another compound
		if (key.indexOf("/") == -1) {
			// Add the tag to the map
			tagMap.put(key, tag.setKey(key));
		}
		else {
			String prefix	= key.substring(0, key.indexOf("/"));
			String postfix	= key.substring(key.indexOf("/") + 1);
			
			// Create a compound tag if one does not exist
			if (!hasKey(prefix, NBTElement.TAG_COMPOUND))
				tagMap.put(prefix, new NBTTagCompound(prefix));
			
			// Recurse until the tag is set
			((NBTTagCompound)tagMap.get(prefix)).setTag(postfix, tag);
		}
	}
	/**
	 * Sets the tag with the given key to the given value. See setTag for more details.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	booleanValue - The value to set the tag to.
	 * 
	 * @see
	 * {@linkplain #setTag(String, NBTElement)}
	 */
	public void setBoolean(String key, boolean booleanValue) {
		setTag(key, new NBTTagByte(key, (byte)(booleanValue ? 1 : 0)));
	}
	/**
	 * Sets the tag with the given key to the given value. See setTag for more details.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	byteValue - The value to set the tag to.
	 * 
	 * @see
	 * {@linkplain #setTag(String, NBTElement)}
	 */
	public void setByte(String key, byte byteValue) {
		setTag(key, new NBTTagByte(key, byteValue));
	}
	/**
	 * Sets the tag with the given key to the given value. See setTag for more details.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	shortValue - The value to set the tag to.
	 * 
	 * @see
	 * {@linkplain #setTag(String, NBTElement)}
	 */
	public void setShort(String key, short shortValue) {
		setTag(key, new NBTTagShort(key, shortValue));
	}
	/**
	 * Sets the tag with the given key to the given value. See setTag for more details.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	intValue - The value to set the tag to.
	 * 
	 * @see
	 * {@linkplain #setTag(String, NBTElement)}
	 */
	public void setInteger(String key, int intValue) {
		setTag(key, new NBTTagInt(key, intValue));
	}
	/**
	 * Sets the tag with the given key to the given value. See setTag for more details.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	longValue - The value to set the tag to.
	 * 
	 * @see
	 * {@linkplain #setTag(String, NBTElement)}
	 */
	public void setLong(String key, long longValue) {
		setTag(key, new NBTTagLong(key, longValue));
	}
	/**
	 * Sets the tag with the given key to the given value. See setTag for more details.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	floatValue - The value to set the tag to.
	 * 
	 * @see
	 * {@linkplain #setTag(String, NBTElement)}
	 */
	public void setFloat(String key, float floatValue) {
		setTag(key, new NBTTagFloat(key, floatValue));
	}
	/**
	 * Sets the tag with the given key to the given value. See setTag for more details.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	doubleValue - The value to set the tag to.
	 * 
	 * @see
	 * {@linkplain #setTag(String, NBTElement)}
	 */
	public void setDouble(String key, double doubleValue) {
		setTag(key, new NBTTagDouble(key, doubleValue));
	}
	/**
	 * Sets the tag with the given key to the given value. See setTag for more details.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	stringValue - The value to set the tag to.
	 * 
	 * @see
	 * {@linkplain #setTag(String, NBTElement)}
	 */
	public void setString(String key, String stringValue) {
		setTag(key, new NBTTagString(key, stringValue));
	}
	/**
	 * Sets the tag with the given key to the given value. See setTag for more details.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	byteArray - The value to set the tag to.
	 * 
	 * @see
	 * {@linkplain #setTag(String, NBTElement)}
	 */
	public void setByteArray(String key, byte[] byteArray) {
		setTag(key, new NBTTagByteArray(key, byteArray));
	}
	/**
	 * Sets the tag with the given key to the given value. See setTag for more details.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	intArray - The value to set the tag to.
	 * 
	 * @see
	 * {@linkplain #setTag(String, NBTElement)}
	 */
	public void setIntegerArray(String key, int[] intArray) {
		setTag(key, new NBTTagIntArray(key, intArray));
	}
	/**
	 * Sets the tag with the given key to the given value. See setTag for more details.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	tagList - The list of tags to set the element to.
	 * 
	 * @see
	 * {@linkplain #setTag(String, NBTElement)}
	 */
	public void setList(String key, Collection<NBTElement> tagList) {
		setTag(key, new NBTTagList(key, tagList));
	}
	/**
	 * Sets the tag with the given key to the given value. See setTag for more details.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	tag - The tag to set the element to.
	 * 
	 * @see
	 * {@linkplain #setTag(String, NBTElement)}
	 */
	public void setList(String key, NBTTagList tag) {
		setTag(key, tag.setKey(key));
	}
	/**
	 * Sets the tag with the given key to the given value. See setTag for more details.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	tagMap - The map of tags to set the element to.
	 * 
	 * @see
	 * {@linkplain #setTag(String, NBTElement)}
	 */
	public void setCompound(String key, Map<String, NBTElement> tagMap) {
		setTag(key, new NBTTagCompound(key, tagMap));
	}
	/**
	 * Sets the tag with the given key to the given value. See setTag for more details.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	tag - The tag to set the element to.
	 * 
	 * @see
	 * {@linkplain #setTag(String, NBTElement)}
	 */
	public void setCompound(String key, NBTTagCompound tag) {
		setTag(key, tag.setKey(key));
	}
	
	// =================== Accessor Methods ===================
	
	/**
	 * Finds and returns the tag with the given key identifier. To access
	 * compound elements within this compound use a '/' to separate values.
	 * Returns the default tag if the key does not exist.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @param	defTag - The default tag to return if the key does not exist.
	 * This can be set to null.
	 * @return	Returns the tag with the given key identifier, or defTag if
	 * the tag doesn't exist.
	 */
	public NBTElement getTag(String key, NBTElement defTag) {
		// Check if the key is accessing another compound
		if (key.indexOf("/") == -1) {
			// Get the tag
			NBTElement tag = tagMap.get(key);
			// Return defTag if the tag does not exist
			return ((tag != null) ? tag : defTag);
		}
		else {
			String prefix	= key.substring(0, key.indexOf("/"));
			String postfix	= key.substring(key.indexOf("/") + 1);
			
			// Recurse until the tag is retreived
			return ((NBTTagCompound)tagMap.get(prefix)).getTag(postfix, defTag);
		}
	}
	/**
	 * Finds and returns the tag with the given key identifier. To access
	 * compound elements within this compound use a '/' to separate values.
	 * Returns the null if the key does not exist.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @return	Returns the tag with the given key identifier, or null if
	 * the tag doesn't exist.
	 */
	public NBTElement getTag(String key) {
		return getTag(key, null);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(String, NBTElement)}
	 */
	public boolean getBoolean(String key, boolean defValue) {
		return ((NBTTagByte)getTag(key, new NBTTagByte(key, (byte)(defValue ? 1 : 0)))).byteValue != 0;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(String)}
	 */
	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(String, NBTElement)}
	 */
	public byte getByte(String key, byte defValue) {
		return ((NBTTagByte)getTag(key, new NBTTagByte(key, defValue))).byteValue;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(String)}
	 */
	public byte getByte(String key) {
		return getByte(key, (byte)0);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(String, NBTElement)}
	 */
	public short getShort(String key, short defValue) {
		return ((NBTTagShort)getTag(key, new NBTTagShort(key, defValue))).shortValue;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(String)}
	 */
	public short getShort(String key) {
		return getShort(key, (short)0);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(String, NBTElement)}
	 */
	public int getInteger(String key, int defValue) {
		return ((NBTTagInt)getTag(key, new NBTTagInt(key, defValue))).intValue;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(String)}
	 */
	public int getInteger(String key) {
		return getInteger(key, 0);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(String, NBTElement)}
	 */
	public long getLong(String key, long defValue) {
		return ((NBTTagLong)getTag(key, new NBTTagLong(key, defValue))).longValue;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(String)}
	 */
	public long getLong(String key) {
		return getLong(key, 0L);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(String, NBTElement)}
	 */
	public float getFloat(String key, float defValue) {
		return ((NBTTagFloat)getTag(key, new NBTTagFloat(key, defValue))).floatValue;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(String)}
	 */
	public float getFloat(String key) {
		return getFloat(key, 0.0f);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(String, NBTElement)}
	 */
	public double getDouble(String key, double defValue) {
		return ((NBTTagDouble)getTag(key, new NBTTagDouble(key, defValue))).doubleValue;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(String)}
	 */
	public double getDouble(String key) {
		return getDouble(key, 0.0);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(String, NBTElement)}
	 */
	public String getString(String key, String defValue) {
		return ((NBTTagString)getTag(key, new NBTTagString(key, defValue))).stringValue;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(String)}
	 */
	public String getString(String key) {
		return getString(key, null);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(String, NBTElement)}
	 */
	public byte[] getByteArray(String key, byte[] defValue) {
		return ((NBTTagByteArray)getTag(key, new NBTTagByteArray(key, defValue))).byteArray;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(String)}
	 */
	public byte[] getByteArray(String key) {
		return getByteArray(key, null);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(String, NBTElement)}
	 */
	public int[] getIntegerArray(String key, int[] defValue) {
		return ((NBTTagIntArray)getTag(key, new NBTTagIntArray(key, defValue))).intArray;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(String)}
	 */
	public int[] getIntegerArray(String key) {
		return getIntegerArray(key, null);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(String, NBTElement)}
	 */
	public NBTTagList getList(String key, NBTTagList defValue) {
		return ((NBTTagList)getTag(key, defValue));
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(String)}
	 */
	public NBTTagList getList(String key) {
		return getList(key, null);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(String, NBTElement)}
	 */
	public NBTTagCompound getCompound(String key, NBTTagCompound defValue) {
		return ((NBTTagCompound)getTag(key, defValue));
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	key - The key of the tag to search for.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(String)}
	 */
	public NBTTagCompound getCompound(String key) {
		return getCompound(key, null);
	}
}
