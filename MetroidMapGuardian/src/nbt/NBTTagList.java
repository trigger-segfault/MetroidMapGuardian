package nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * A basic NBT element with a byte value.
 * 
 * @author	Markus Persson
 *
 * @see
 * {@linkplain NBTElement},
 * {@linkplain NBTTagCompound}
 */
public class NBTTagList extends NBTElement {

	// ======================= Members ========================
	
	/** The type of tags in the list. */
	private byte tagType;
	/** The list of elements. */
	private ArrayList<NBTElement> tagList;

	// ===================== Constructors =====================

	/**
	 * The default constructor, constructs an empty list with a byte type and
	 * an empty key.
	 * 
	 * @return	Returns an empty list with a byte type and with an empty key.
	 */
	public NBTTagList() {
		super("");
		this.tagType = 0;
		this.tagList = new ArrayList<NBTElement>();
	}
	/**
	 * Constructs an empty list with a byte type and the given key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @return	Returns an empty list with a byte type and the given key.
	 */
	public NBTTagList(String key) {
		super(key);
		this.tagType = 0;
		this.tagList = new ArrayList<NBTElement>();
	}
	/**
	 * Constructs an empty list of the given type and key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	tagType - The type of the tag in the list.
	 * @return	Returns an empty list with the given type  and key.
	 */
	public NBTTagList(String key, byte tagType) {
		super(key);
		this.tagType = tagType;
		this.tagList = new ArrayList<NBTElement>();
	}
	/**
	 * Constructs an empty list of the given type and key.
	 * 
	 * @param	key - The key identifier of the tag.
	 * @param	tagType - The type of the tag in the list.
	 * @return	Returns an empty list with the given type  and key.
	 */
	public NBTTagList(String key, Collection<NBTElement> tagList) {
		super(key);
		this.tagList = new ArrayList<NBTElement>();
		this.tagList.addAll(tagList);
		if (this.tagList.size() > 0)
			this.tagType = this.tagList.get(0).getType();
		else
			this.tagType = NBTElement.TAG_BYTE;
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
		for (NBTElement tag : tagList) {
			tagName += tag.toString(level);
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
		// Correct the tag type
		if (tagList.size() > 0)
			tagType = tagList.get(0).getType();
		
		// Write the header
		out.writeByte(tagType);
		out.writeInt(tagList.size());
		
		// Write the list
		for (NBTElement tag : tagList) {
			tag.writeTagContents(out);
		}
	}
	/**
	 * Reads the contents of the tag from the input stream.
	 * 
	 * @param	in - The input stream to read the contents from.
	 * @throws	IOException 
	 */
	void readTagContents(DataInput in) throws IOException {
		// Read the header
		tagType = in.readByte();
		int tagCount = in.readInt();
		
		// Read the list
		tagList.clear();
		for (int i = 0; i < tagCount; i++) {
			NBTElement tag = NBTElement.createTagOfType(tagType, "");
			tag.readTagContents(in);
			tagList.add(tag);
		}
	}
	/**
	 * Returns the type of the tag.
	 * 
	 * @return	Returns the type of the tag.
	 */
	public byte getType() {
		return NBTElement.TAG_LIST;
	}

	// ===================== List Methods =====================

	/**
	 * Returns the collection of tags contained in this list.
	 * 
	 * @return	Returns the collection of tags contained in this list.
	 */
	public Collection<NBTElement> getTags() {
		return tagList;
	}
	/**
	 * Returns the number of elements in the list.
	 * 
	 * @return	Returns the number of elements in the list.
	 */
	public int tagCount() {
		return tagList.size();
	}
	/**
	 * Clears all the tags in the list.
	 */
	public void clearTags() {
		tagList.clear();
	}
	
	// =================== Mutator Methods ====================

	/**
	 * Sets the type of tags this list contains, this is mainly used for when
	 * it is not certain if the list will have any elements.
	 * 
	 * @param	tagType - The new type of tag this list contains.
	 * @return	Returns this element after the list type has been set.
	 */
	public NBTTagList setTagType(byte tagType) {
		this.tagType = tagType;
		return this;
	}
	/**
	 * Sets the tag at the given index to the given value.
	 * 
	 * @param	index - The index of the tag.
	 * @param	tag - The tag to set the element to.
	 */
	public void setTag(int index, NBTElement tag) {
		// Set the tag type
		tagType = tag.getType();
		
		// Add the tag to the list at the given index
		tagList.set(index, tag.setKey(""));
	}
	/**
	 * Adds the tag at the end of the list.
	 * 
	 * @param	tag - The tag to set the element to.
	 */
	public void addTag(NBTElement tag) {
		// Set the tag type
		tagType = tag.getType();
		
		// Add the tag to the list
		tagList.add(tag.setKey(""));
	}
	/**
	 * Sets the tag at the given index to the given value.
	 * 
	 * @param	index - The index of the tag.
	 * @param	booleanValue - The value to set the tag to.
	 */
	public void setBoolean(int index, boolean booleanValue) {
		setTag(index, new NBTTagByte("", (byte)(booleanValue ? 1 : 0)));
	}
	/**
	 * Adds the tag at the end of the list with the given value.
	 * 
	 * @param	booleanValue - The value to set the tag to.
	 */
	public void addBoolean(boolean booleanValue) {
		addTag(new NBTTagByte("", (byte)(booleanValue ? 1 : 0)));
	}
	/**
	 * Sets the tag at the given index to the given value.
	 * 
	 * @param	index - The index of the tag.
	 * @param	byteValue - The value to set the tag to.
	 */
	public void setByte(int index, byte byteValue) {
		setTag(index, new NBTTagByte("", byteValue));
	}
	/**
	 * Adds the tag at the end of the list with the given value.
	 * 
	 * @param	booleanValue - The value to set the tag to.
	 */
	public void addByte(byte byteValue) {
		addTag(new NBTTagByte("", byteValue));
	}
	/**
	 * Sets the tag at the given index to the given value.
	 * 
	 * @param	index - The index of the tag.
	 * @param	shortValue - The value to set the tag to.
	 */
	public void setShort(int index, short shortValue) {
		setTag(index, new NBTTagShort("", shortValue));
	}
	/**
	 * Adds the tag at the end of the list with the given value.
	 * 
	 * @param	shortValue - The value to set the tag to.
	 */
	public void addShort(short shortValue) {
		addTag(new NBTTagShort("", shortValue));
	}
	/**
	 * Sets the tag at the given index to the given value.
	 * 
	 * @param	index - The index of the tag.
	 * @param	intValue - The value to set the tag to.
	 */
	public void setInteger(int index, int intValue) {
		setTag(index, new NBTTagInt("", intValue));
	}
	/**
	 * Adds the tag at the end of the list with the given value.
	 * 
	 * @param	intValue - The value to set the tag to.
	 */
	public void addInteger(int intValue) {
		addTag(new NBTTagInt("", intValue));
	}
	/**
	 * Sets the tag at the given index to the given value.
	 * 
	 * @param	index - The index of the tag.
	 * @param	longValue - The value to set the tag to.
	 */
	public void setLong(int index, long longValue) {
		setTag(index, new NBTTagLong("", longValue));
	}
	/**
	 * Adds the tag at the end of the list with the given value.
	 * 
	 * @param	longValue - The value to set the tag to.
	 */
	public void addLong(long longValue) {
		addTag(new NBTTagLong("", longValue));
	}
	/**
	 * Sets the tag at the given index to the given value.
	 * 
	 * @param	index - The index of the tag.
	 * @param	floatValue - The value to set the tag to.
	 */
	public void setFloat(int index, float floatValue) {
		setTag(index, new NBTTagFloat("", floatValue));
	}
	/**
	 * Adds the tag at the end of the list with the given value.
	 * 
	 * @param	floatValue - The value to set the tag to.
	 */
	public void addFloat(float floatValue) {
		addTag(new NBTTagFloat("", floatValue));
	}
	/**
	 * Sets the tag at the given index to the given value.
	 * 
	 * @param	index - The index of the tag.
	 * @param	doubleValue - The value to set the tag to.
	 */
	public void setDouble(int index, double doubleValue) {
		setTag(index, new NBTTagDouble("", doubleValue));
	}
	/**
	 * Adds the tag at the end of the list with the given value.
	 * 
	 * @param	doubleValue - The value to set the tag to.
	 */
	public void addDouble(double doubleValue) {
		addTag(new NBTTagDouble("", doubleValue));
	}
	/**
	 * Sets the tag at the given index to the given value.
	 * 
	 * @param	index - The index of the tag.
	 * @param	stringValue - The value to set the tag to.
	 */
	public void setString(int index, String stringValue) {
		setTag(index, new NBTTagString("", stringValue));
	}
	/**
	 * Adds the tag at the end of the list with the given value.
	 * 
	 * @param	stringValue - The value to set the tag to.
	 */
	public void addString(String stringValue) {
		addTag(new NBTTagString("", stringValue));
	}
	/**
	 * Sets the tag at the given index to the given value.
	 * 
	 * @param	index - The index of the tag.
	 * @param	byteArray - The value to set the tag to.
	 */
	public void setByteArray(int index, byte[] byteArray) {
		setTag(index, new NBTTagByteArray("", byteArray));
	}
	/**
	 * Adds the tag at the end of the list with the given value.
	 * 
	 * @param	byteArray - The value to set the tag to.
	 */
	public void addByteArray(byte[] byteArray) {
		addTag(new NBTTagByteArray("", byteArray));
	}
	/**
	 * Sets the tag at the given index to the given value.
	 * 
	 * @param	index - The index of the tag.
	 * @param	intArray - The value to set the tag to.
	 */
	public void setIntegerArray(int index, int[] intArray) {
		setTag(index, new NBTTagIntArray("", intArray));
	}
	/**
	 * Adds the tag at the end of the list with the given value.
	 * 
	 * @param	intArray - The value to set the tag to.
	 */
	public void addIntegerArray(int[] intArray) {
		addTag(new NBTTagIntArray("", intArray));
	}
	/**
	 * Sets the tag at the given index to the given value.
	 * 
	 * @param	index - The index of the tag.
	 * @param	tagList - The list of tags to set the element to.
	 */
	public void setList(int index, Collection<NBTElement> tagList) {
		setTag(index, new NBTTagList("", tagList));
	}
	/**
	 * Adds the tag at the end of the list with the given value.
	 * 
	 * @param	tagList - The list of tags to set the element to.
	 */
	public void addList(Collection<NBTElement> tagList) {
		addTag(new NBTTagList("", tagList));
	}
	/**
	 * Sets the tag at the given index to the given value.
	 * 
	 * @param	index - The index of the tag.
	 * @param	tag - The tag to set the element to.
	 */
	public void setList(int index, NBTTagList tag) {
		setTag(index, tag);
	}
	/**
	 * Adds the tag at the end of the list with the given value.
	 * 
	 * @param	tag - The tag to set the element to.
	 */
	public void addList(NBTTagList tag) {
		addTag(tag);
	}
	/**
	 * Sets the tag at the given index to the given value.
	 * 
	 * @param	index - The index of the tag.
	 * @param	tagMap - The map of tags to set the element to.
	 */
	public void setCompound(int index, Map<String, NBTElement> tagMap) {
		setTag(index, new NBTTagCompound("", tagMap));
	}
	/**
	 * Adds the tag at the end of the list with the given value.
	 * 
	 * @param	tagMap - The map of tags to set the element to.
	 */
	public void addCompound(Map<String, NBTElement> tagMap) {
		addTag(new NBTTagCompound("", tagMap));
	}
	/**
	 * Sets the tag at the given index to the given value.
	 * 
	 * @param	index - The index of the tag.
	 * @param	tag - The tag to set the element to.
	 */
	public void setCompound(int index, NBTTagCompound tag) {
		setTag(index, tag);
	}
	/**
	 * Adds the tag at the end of the list with the given value.
	 * 
	 * @param	tag - The tag to set the element to.
	 */
	public void addCompound(NBTTagCompound tag) {
		addTag(tag);
	}

	// =================== Accessor Methods ===================
	
	/**
	 * Returns the type of tags this list contains.
	 * 
	 * @return	Returns the type of tags this list contains.
	 */
	public byte getTagType() {
		if (tagList.size() > 0) {
			return tagList.get(0).getType();
		}
		else {
			return tagType;
		}
	}
	/**
	 * Finds and returns the tag at the given index. Returns the default
	 * tag if the key does not exist.
	 * 
	 * @param	index - The index of the tag.
	 * @param	defTag - The default tag to return if the index does not exist.
	 * @return	Returns the tag at the given index, or defTag if the tag doesn't
	 * exist.
	 */
	public NBTElement getTag(int index, NBTElement defTag) {

		// Make sure the index is valid
		if (index >= 0 && index < tagList.size())
			return tagList.get(index);

		// Return defTag if the tag does not exist
		return defTag;
	}
	/**
	 * Finds and returns the tag at the given index. Returns null if the key
	 * does not exist.
	 * 
	 * @param	index - The index of the tag.
	 * @return	Returns the tag at the given index, or null if the tag doesn't
	 * exist.
	 */
	public NBTElement getTag(int index) {
		return getTag(index, null);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(int, NBTElement)}
	 */
	public boolean getBoolean(int index, boolean defValue) {
		return ((NBTTagByte)getTag(index, new NBTTagByte("", (byte)(defValue ? 1 : 0)))).byteValue != 0;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(int)}
	 */
	public boolean getBoolean(int index) {
		return getBoolean(index, false);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(int, NBTElement)}
	 */
	public byte getByte(int index, byte defValue) {
		return ((NBTTagByte)getTag(index, new NBTTagByte("", defValue))).byteValue;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(int)}
	 */
	public byte getByte(int index) {
		return getByte(index, (byte)0);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(int, NBTElement)}
	 */
	public short getShort(int index, short defValue) {
		return ((NBTTagShort)getTag(index, new NBTTagShort("", defValue))).shortValue;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(int)}
	 */
	public short getShort(int index) {
		return getShort(index, (short)0);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(int, NBTElement)}
	 */
	public int getInteger(int index, int defValue) {
		return ((NBTTagInt)getTag(index, new NBTTagInt("", defValue))).intValue;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(int)}
	 */
	public int getInteger(int index) {
		return getInteger(index, 0);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(int, NBTElement)}
	 */
	public long getLong(int index, long defValue) {
		return ((NBTTagLong)getTag(index, new NBTTagLong("", defValue))).longValue;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(int)}
	 */
	public long getLong(int index) {
		return getLong(index, 0L);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(int, NBTElement)}
	 */
	public float getFloat(int index, float defValue) {
		return ((NBTTagFloat)getTag(index, new NBTTagFloat("", defValue))).floatValue;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(int)}
	 */
	public float getFloat(int index) {
		return getFloat(index, 0.0f);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(int, NBTElement)}
	 */
	public double getDouble(int index, double defValue) {
		return ((NBTTagDouble)getTag(index, new NBTTagDouble("", defValue))).doubleValue;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(int)}
	 */
	public double getDouble(int index) {
		return getDouble(index, 0.0);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(int, NBTElement)}
	 */
	public String getString(int index, String defValue) {
		return ((NBTTagString)getTag(index, new NBTTagString("", defValue))).stringValue;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(int)}
	 */
	public String getString(int index) {
		return getString(index, null);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(int, NBTElement)}
	 */
	public byte[] getByteArray(int index, byte[] defValue) {
		return ((NBTTagByteArray)getTag(index, new NBTTagByteArray("", defValue))).byteArray;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(int)}
	 */
	public byte[] getByteArray(int index) {
		return getByteArray(index, null);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(int, NBTElement)}
	 */
	public int[] getIntegerArray(int index, int[] defValue) {
		return ((NBTTagIntArray)getTag(index, new NBTTagIntArray("", defValue))).intArray;
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(int)}
	 */
	public int[] getIntegerArray(int index) {
		return getIntegerArray(index, null);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(int, NBTElement)}
	 */
	public NBTTagList getList(int index, NBTTagList defValue) {
		return ((NBTTagList)getTag(index, defValue));
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(int)}
	 */
	public NBTTagList getList(int index) {
		return getList(index, null);
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @param	defValue - The default value to return if the tag does not exist.
	 * @return	Returns the value of the key or the default value.
	 * 
	 * @see
	 * {@linkplain #getTag(int, NBTElement)}
	 */
	public NBTTagCompound getCompound(int index, NBTTagCompound defValue) {
		return ((NBTTagCompound)getTag(index, defValue));
	}
	/**
	 * Returns the value of the given key. See getTag for more details.
	 * 
	 * @param	index - The index of the tag.
	 * @return	Returns the value of the key.
	 * 
	 * @see
	 * {@linkplain #getTag(int)}
	 */
	public NBTTagCompound getCompound(int index) {
		return getCompound(index, null);
	}
}
