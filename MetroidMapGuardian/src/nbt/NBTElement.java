package nbt;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The Named-Binary-Tag format is a file format created by Markus Persson.
 * It works by storing elements in the form of tags. Each tag starts with a
 * tag type which is a byte long, then the tag will contain a name. There
 * are currently 12 different types of tags, the most important being
 * {@linkplain NBTTagCompound}, as it is used to store all other tags.
 * 
 * <p></p>
 * 
 * This is the base element in the Named-Binary-Tag format.
 * All elements contain a value of the type specified by their tag.
 * 
 * <p></p>
 * 
 * To write and read a tag use {@linkplain saveNBTCompound} and
 * {@linkplain loadNBTCompound} respectively. The NBT standard requires the
 * contents to be saved with a compound within a root compound, however the
 * root compound is not required to function.
 * 
 * <p></p>
 * 
 * @author	Markus Persson
 * 
 * @see <a href="https://twitter.com/notch">Markus Persson's Twitter</a>,
 * 		<a href="http://www.minecraftwiki.net/wiki/NBT_format">Minepedia: NBT Format</a>
 * @see
 * {@linkplain NBTTagCompound}
 */
public abstract class NBTElement {
	
	// ====================== Constants =======================
	
	/** A tag used to end lists and compounds. */
	public static final byte TAG_END			= 0;
	/** A tag with a byte value, it can also be used as a boolean. */
	public static final byte TAG_BYTE			= 1;
	/** A tag with a short value. */
	public static final byte TAG_SHORT			= 2;
	/** A tag with a integer value. */
	public static final byte TAG_INT			= 3;
	/** A tag with a long value. */
	public static final byte TAG_LONG			= 4;
	/** A tag with a float value. */
	public static final byte TAG_FLOAT			= 5;
	/** A tag with a double value. */
	public static final byte TAG_DOUBLE			= 6;
	/** A tag with a byte array value. */
	public static final byte TAG_BYTE_ARRAY		= 7;
	/** A tag with a string value. */
	public static final byte TAG_STRING			= 8;
	/** A tag with a list of tag value. */
	public static final byte TAG_LIST			= 9;
	/** A tag that contains more elements of tags. */
	public static final byte TAG_COMPOUND		= 10;
	/** A tag with a integer array value. */
	public static final byte TAG_INT_ARRAY		= 11;
	
	// ======================= Members ========================
	
	/** The key identifier of the element. */
	protected String key;

	// ===================== Constructors =====================
	
	/**
	 * Creates the base of an element with the given key.
	 * 
	 * @param	key - The key identifier of the element.
	 * @return	Returns the base of the element with the given key.
	 */
	protected NBTElement(String key) {
		this.key = ((key == null) ? "" : key);
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
		// Format:
		// TAG_Compound - MyCompound:
		//     TAG_String - MyString: "Hello World!"
		
		String spacing = "";
		for (int i = 0; i < level; i++)
			spacing += "    ";
	
		String tagName = NBTElement.getTagName(getType());
		
		if (getType() != NBTElement.TAG_END) {
			if (!key.isEmpty())
				tagName += " - " + key;
			
			tagName += ": ";
		}
		
		return spacing + tagName;
	}
		
	// =================== Abstract Methods ===================
	
	/**
	 * Writes the contents of the tag to the output stream.
	 * 
	 * @param	out - The output stream to write the contents to.
	 * @throws	IOException 
	 */
	abstract void writeTagContents(DataOutput out) throws IOException;
	/**
	 * Reads the contents of the tag from the input stream.
	 * 
	 * @param	in - The input stream to read the contents from.
	 * @throws	IOException
	 */
	abstract void readTagContents(DataInput in) throws IOException;
	/**
	 * Returns the type of the tag.
	 * 
	 * @return	Returns the type of the tag.
	 */
	public abstract byte getType();

	// =================== Key Information ====================
	
	/**
	 * Sets the key identifier of the tag to the specified string.
	 * 
	 * @param	key - The new key identifier.
	 * @return	Returns the element after the key has been set.
	 */
	public NBTElement setKey(String key) {
		// If the string is null set key to an empty string.
		this.key = ((key == null) ? "" : key);
		return this;
	}
	/**
	 * Returns the key identifier of the tag.
	 * 
	 * @return	Returns the key identifier.
	 */
	public String getKey() {
		return key;
	}

	// ======================= File IO ========================

	/**
	 * Writes the given tag to the output stream.
	 * 
	 * @param	tag - The tag to write.
	 * @param	out - The output stream to write to.
	 * @throws	IOException
	 */
	public static void writeTag(NBTElement tag, DataOutput out) throws IOException {
		// Write the tag type
		out.writeByte(tag.getType());
		// Write the contents and key if the tag is not TAG_End
		if (tag.getType() == NBTElement.TAG_END) {
			return;
		}
		else {
			// Write the key identifier
			out.writeUTF(tag.getKey());
			// Write the tag contents
			tag.writeTagContents(out);
			return;
		}
	}
	/**
	 * Reads and returns the next tag in the input stream.
	 * 
	 * @param	in - The input stream to read from
	 * @return	Returns the new tag after it has been read.
	 * @throws	IOException
	 */
	public static NBTElement readTag(DataInput in) throws IOException {
		// Read the tag type
		byte type = in.readByte();
		// Read the contents and key if the tag is not TAG_End
		if (type == NBTElement.TAG_END) {
			return new NBTTagEnd();
		}
		else {
			// Read the key identifier
			String key = in.readUTF();
			// Create and read the tag contents
			NBTElement tag = NBTElement.createTagOfType(type, key);
			tag.readTagContents(in);
			return tag;
		}
	}
	
	// =================== Static Functions ===================
	
	/**
	 * Returns a tag of the given type with the given key.
	 * 
	 * @param	type - The type of tag to create.
	 * @param	key - The key identifier to assign to the tag
	 * @return	Returns a tag of the given tag type with the given key.
	 */
	public static NBTElement createTagOfType(byte type, String key) {
		switch (type) {
		case NBTElement.TAG_END:			return new NBTTagEnd();
		case NBTElement.TAG_BYTE:			return new NBTTagByte(key);
		case NBTElement.TAG_SHORT:			return new NBTTagShort(key);
		case NBTElement.TAG_INT:			return new NBTTagInt(key);
		case NBTElement.TAG_LONG:			return new NBTTagLong(key);
		case NBTElement.TAG_FLOAT:			return new NBTTagFloat(key);
		case NBTElement.TAG_DOUBLE:			return new NBTTagDouble(key);
		case NBTElement.TAG_BYTE_ARRAY:		return new NBTTagByteArray(key);
		case NBTElement.TAG_STRING:			return new NBTTagString(key);
		case NBTElement.TAG_LIST:			return new NBTTagList(key);
		case NBTElement.TAG_COMPOUND:		return new NBTTagCompound(key);
		case NBTElement.TAG_INT_ARRAY:		return new NBTTagIntArray(key);
		}
		return null;
	}
	/**
	 * Returns the name of the specified tag type.
	 * 
	 * @param	type - The type of tag to get the name of.
	 * @return	Returns the name of the given tag type.
	 */
	public static String getTagName(byte type) {
		switch (type) {
		case NBTElement.TAG_END:			return "TAG_End";
		case NBTElement.TAG_BYTE:			return "TAG_Byte";
		case NBTElement.TAG_SHORT:			return "TAG_Short";
		case NBTElement.TAG_INT:			return "TAG_Int";
		case NBTElement.TAG_LONG:			return "TAG_Long";
		case NBTElement.TAG_FLOAT:			return "TAG_Float";
		case NBTElement.TAG_DOUBLE:			return "TAG_Double";
		case NBTElement.TAG_BYTE_ARRAY:		return "TAG_Byte_Array";
		case NBTElement.TAG_STRING:			return "TAG_String";
		case NBTElement.TAG_LIST:			return "TAG_List";
		case NBTElement.TAG_COMPOUND:		return "TAG_Compound";
		case NBTElement.TAG_INT_ARRAY:		return "TAG_Int_Array";
		}
		return "UNKNOWN";
	}
	/**
	 * Loads and returns an NBT compound that contains the main tag in the file.
	 * 
	 * @param	path - The path of the file to load from.
	 * @param	isResource - True if the file should be loaded as a resource.
	 * @return	Returns the NBT compound that contains the main tag in the file.
	 */
	public static NBTTagCompound loadNBTCompound(String path, boolean isResource) {
		try {
			InputStream stream = null;
			
			if (isResource)
				stream = NBTElement.class.getResourceAsStream(path);
			else
				stream = new FileInputStream(path);
			
			DataInputStream in = new DataInputStream(stream);
			
			// Read the tag which needs to be a TAG_Compound.
			NBTElement tag = NBTElement.readTag(in);
			
			return (NBTTagCompound)tag;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * Saves the given NBT tag to the file.
	 * 
	 * @param	path - The path of the file to save as.
	 * @param	nbt - The NBT tag to save to file.
	 */
	public static void saveNBTCompound(String path, NBTTagCompound nbt) {
		try {
			OutputStream stream = new FileOutputStream(path);
			DataOutputStream out = new DataOutputStream(stream);
			
			// Write the tag which needs to be a TAG_Compound.
			NBTElement.writeTag(nbt, out);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
